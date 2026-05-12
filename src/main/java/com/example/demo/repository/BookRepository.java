package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    /**
     * Find book by ISBN
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Find books by author (case-insensitive)
     */
    List<Book> findByAuthorIgnoreCase(String author);

    /**
     * Find books by title containing (search)
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find books within price range
     */
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Custom JPQL query
     */
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.price <= :maxPrice")
    List<Book> findByAuthorAndMaxPrice(@Param("author") String author, @Param("maxPrice") Double maxPrice);

    /**
     * Check if book exists by ISBN
     */
    boolean existsByIsbn(String isbn);
}
