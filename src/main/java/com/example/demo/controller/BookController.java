package com.example.demo.controller;


import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UpdateBookDTO;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET book by ID
    // http://localhost:8080/api/v1/books/1
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
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

    // ========== NEW: PAGINATION ENDPOINTS ==========

    /**
     * GET all books with pagination and sorting
     * Examples:
     * GET /api/v1/books/paginated?page=0&size=10
     * GET /api/v1/books/paginated?page=0&size=10&sort=price,desc
     * GET /api/v1/books/paginated?page=0&size=10&sort=title,asc
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<BookDTO>> getAllBooksPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<BookDTO> books = bookService.getAllBooksPageable(pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Search by title with pagination
     * GET /api/v1/books/paginated/search/title?title=Clean&page=0&size=10
     */
    @GetMapping("/paginated/search/title")
    public ResponseEntity<Page<BookDTO>> searchByTitlePageable(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<BookDTO> books = bookService.searchByTitlePageable(title, pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Search by author with pagination
     * GET /api/v1/books/paginated/search/author?authorId=1&page=0&size=10
     */
    @GetMapping("/paginated/search/author")
    public ResponseEntity<Page<BookDTO>> findByAuthorPageable(
            @RequestParam Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.findByAuthorPageable(authorId, pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Search by price range with pagination
     * GET /api/v1/books/paginated/search/price?minPrice=10&maxPrice=50&page=0&size=10
     */
    @GetMapping("/paginated/search/price")
    public ResponseEntity<Page<BookDTO>> findByPriceRangePageable(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        Page<BookDTO> books = bookService.findByPriceRangePageable(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Advanced: Find expensive books by specific author
     * GET /api/v1/books/paginated/expensive?authorId=1&minPrice=40&page=0&size=10
     */
    @GetMapping("/paginated/expensive")
    public ResponseEntity<Page<BookDTO>> findExpensiveBooksByAuthor(
            @RequestParam Long authorId,
            @RequestParam Double minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.findExpensiveBooksByAuthor(authorId, minPrice, pageable);
        return ResponseEntity.ok(books);
    }

    /**
     * Advanced: Global search (title + author + description)
     * GET /api/v1/books/paginated/search?keyword=code&page=0&size=10
     */
    @GetMapping("/paginated/search")
    public ResponseEntity<Page<BookDTO>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<BookDTO> books = bookService.searchByKeyword(keyword, pageable);
        return ResponseEntity.ok(books);
    }
}
