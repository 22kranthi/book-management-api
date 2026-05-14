package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    // @Autowired
    private BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // Constructor Injection Recommended
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return convertToDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksPageable(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchByTitlePageable(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findByPriceRangePageable(Double minPrice, Double maxPrice, Pageable pageable) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice, pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findByAuthorPageable(Long authorId, Pageable pageable) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + authorId));
        return bookRepository.findByAuthor(author, pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> findExpensiveBooksByAuthor(Long authorId, Double minPrice, Pageable pageable) {
        return bookRepository.findExpensiveBooksByAuthor(authorId, minPrice, pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchByKeyword(String keyword, Pageable pageable) {
        return bookRepository.searchByKeyword(keyword, pageable)
                .map(this::convertToDTO);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // Check if book already exists
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDTO.getIsbn() + " already exists");
        }

        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, UpdateBookDTO updateBookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        // Use helper method to convert and update
        Book updatedBook = convertUpdateDTOToEntity(updateBookDTO, book);
        Book savedBook = bookRepository.save(updatedBook);

        return convertToDTO(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    // ==================== Helper Methods ====================

    /**
     * Convert Entity to DTO
     * Used for all response conversions
     */
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }

    /**
     * Convert BookDTO to Entity
     * Used for CREATE (POST) operations
     * All fields are required (validated by @Valid in controller)
     */
    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());

        // Fetch author from database
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + dto.getAuthorId()));
        book.setAuthor(author);

        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        return book;
    }

    /**
     * Convert UpdateBookDTO to Entity
     * Used for UPDATE (PUT) operations
     * Only updates fields that are provided (not null)
     *
     * @param updateDTO    The update data (partial fields)
     * @param existingBook The existing book entity to update
     * @return Updated book entity
     */
    private Book convertUpdateDTOToEntity(UpdateBookDTO updateDTO, Book existingBook) {
        if (updateDTO.getTitle() != null && !updateDTO.getTitle().isEmpty()) {
            existingBook.setTitle(updateDTO.getTitle());
        }

        if (updateDTO.getAuthorId() != null) {
            Author author = authorRepository.findById(updateDTO.getAuthorId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Author not found with ID: " + updateDTO.getAuthorId()));
            existingBook.setAuthor(author);
        }

        if (updateDTO.getPrice() != null) {
            existingBook.setPrice(updateDTO.getPrice());
        }

        if (updateDTO.getDescription() != null && !updateDTO.getDescription().isEmpty()) {
            existingBook.setDescription(updateDTO.getDescription());
        }

        return existingBook;
    }

}