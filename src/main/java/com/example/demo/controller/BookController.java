package com.example.demo.controller;


import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "*",maxAge = 3600)
public class BookController {

    // @Autowired
    private BookService bookService;

    // Constructor Injection Recommended
    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    // GET all books
    // http://localhost:8080/api/v1/books
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // GET book by ID
    // http://localhost:8080/api/v1/books/1
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * SEARCH books by title
     * http://localhost:8080/api/v1/books/search?title=Clean
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> searchByTitle(@RequestParam String title) {
        List<BookDTO> books =  bookService.searchByTitle(title);
        return ResponseEntity.ok(books);
    }

    /**
     * SEARCH books by author
     * http://localhost:8080/api/v1/books/search/author?author=Martin
     */
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> findByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.findByAuthor(author);
        return ResponseEntity.ok(books);
    }

    /**
     * SEARCH books by price range
     * http://localhost:8080/api/v1/books/search/price?minPrice=10&maxPrice=50
     */
    @GetMapping("/search/price")
    public ResponseEntity<List<BookDTO>> findByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice
    ) {
        List<BookDTO> books = bookService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    /**
     * CREATE new book
     * POST http://localhost:8080/api/v1/books
     * Body: { "title": "...", "author": "...", "isbn": "...", "price": 45.99 }
     */
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * UPDATE book by ID
     * PUT http://localhost:8080/api/v1/books/1
     * Body: { "title": "...", "price": 50.99 }
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookDTO updateBookDTO  // Changed to UpdateBookDTO
    ) {
        BookDTO updatedBook = bookService.updateBook(id, updateBookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * DELETE book by ID
     * DELETE http://localhost:8080/api/v1/books/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    /**
     * DELETE all books
     * DELETE http://localhost:8080/api/v1/books
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

    /**
     * GET total count of books
     * http://localhost:8080/api/v1/books/count
     */
    @GetMapping("/info/count")
    public ResponseEntity<Long> countBooks() {
        long count = bookService.countBooks();
        return ResponseEntity.ok(count);
    }

    /**
     * CHECK if book exists
     * http://localhost:8080/api/v1/books/exists/1
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable Long id) {
        boolean exists = bookService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
