package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService {

    // Get all Books with pagination
    // New paginated methods only
    /**
     * Get all books with pagination
     * @param pageable Contains page number, size, and sort info
     * @return Page<BookDTO> with pagination info
     */
    Page<BookDTO> getAllBooksPageable(Pageable pageable);

    // Get book by ID
    BookDTO getBookById(Long id);

    // Search books by title with pagination
    Page<BookDTO> searchByTitlePageable(String title, Pageable pageable);

    // Find books by author with pagination
    Page<BookDTO> findByAuthorPageable(Long authorId, Pageable pageable);

    // Find books within price range with pagination
    Page<BookDTO> findByPriceRangePageable(Double minPrice, Double maxPrice, Pageable pageable);

    // Create new book
    BookDTO createBook(BookDTO bookDTO);

    /**
     * Update existing book
     */
    BookDTO updateBook(Long id, UpdateBookDTO bookDTO);  // Changed parameter type

    // Delete book by ID
    void deleteBook(Long id);

    // Delete all books
    void deleteAllBooks();

    // Check if book exists
    boolean existsById(Long id);

    // Get total number of books
    long countBooks();

    //newly paginated methods
    //  Advanced: Find expensive books by author
    Page<BookDTO> findExpensiveBooksByAuthor(Long authorId, Double minPrice, Pageable pageable);

    // Advanced: Global search (title + author + description)
    Page<BookDTO> searchByKeyword(String keyword, Pageable pageable);
}
