package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;

import java.util.List;


public interface BookService {

    // Get all Books
    List<BookDTO> getAllBooks();

    // Get book by ID
    BookDTO getBookById(Long id);

    // Search books by title
    List<BookDTO> searchByTitle(String title);

    // Find books by author
    List<BookDTO> findByAuthor(String author);

    // Find books within price range
    List<BookDTO> findByPriceRange(Double minPrice,Double maxPrice);

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

}
