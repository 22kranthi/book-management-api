package com.example.demo.service;


import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;
import com.example.demo.entity.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
    public List<BookDTO> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findByPriceRange(Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
        dto.setAuthor(book.getAuthor());
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
        book.setAuthor(dto.getAuthor());
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
     * @param updateDTO The update data (partial fields)
     * @param existingBook The existing book entity to update
     * @return Updated book entity
     */
    private Book convertUpdateDTOToEntity(UpdateBookDTO updateDTO, Book existingBook) {
        // Only update title if provided
        if (updateDTO.getTitle() != null && !updateDTO.getTitle().isEmpty()) {
            existingBook.setTitle(updateDTO.getTitle());
        }

        // Only update author if provided
        if (updateDTO.getAuthor() != null && !updateDTO.getAuthor().isEmpty()) {
            existingBook.setAuthor(updateDTO.getAuthor());
        }

        // Only update price if provided
        if (updateDTO.getPrice() != null) {
            existingBook.setPrice(updateDTO.getPrice());
        }

        // Only update description if provided
        if (updateDTO.getDescription() != null && !updateDTO.getDescription().isEmpty()) {
            existingBook.setDescription(updateDTO.getDescription());
        }

        // NOTE: ISBN is NOT updated (it's unique and shouldn't change)

        return existingBook;
    }
}