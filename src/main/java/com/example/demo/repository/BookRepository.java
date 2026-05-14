package com.example.demo.repository;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
        /**
         * Find book by ISBN
         */
        Optional<Book> findByIsbn(String isbn);

        /**
         * Check if book exists by ISBN
         */
        boolean existsByIsbn(String isbn);

        // ========== NEW: PAGINATION & SORTING METHODS ==========

        /**
         * Get all books with pagination
         * Usage: page=0, size=10 (first 10 books)
         */
        Page<Book> findAll(Pageable pageable);

        /**
         * Search by title with pagination
         */
        Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

        /**
         * Search by author name with pagination
         */
        Page<Book> findByAuthor(Author author, Pageable pageable);

        /**
         * Find books by author and price range with pagination
         */
        Page<Book> findByAuthorAndPriceBetween(Author author, Double minPrice, Double maxPrice, Pageable pageable);

        /**
         * Advanced: Find expensive books by author ID with pagination
         */
        @Query("SELECT b FROM Book b WHERE b.author.id = :authorId AND b.price >= :minPrice ORDER BY b.price DESC")
        Page<Book> findExpensiveBooksByAuthorId(@Param("authorId") Long authorId, @Param("minPrice") Double minPrice,
                        Pageable pageable);

        /**
         * Find books in price range with pagination
         */
        Page<Book> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

        @Query("SELECT b FROM Book b WHERE b.author.id = :authorId AND b.price >= :minPrice ORDER BY b.price DESC")
        Page<Book> findExpensiveBooksByAuthor(
                        @Param("authorId") Long authorId,
                        @Param("minPrice") Double minPrice,
                        Pageable pageable);

        @Query("SELECT b FROM Book b WHERE " +
                        "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<Book> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
