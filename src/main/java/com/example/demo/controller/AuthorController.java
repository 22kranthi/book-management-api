package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.service.AuthorService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthorController {
    
    // @Autowired
    private AuthorService authorService;
    
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    /**
     * GET all authors
     * GET /api/v1/authors
     */
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
    
    /**
     * GET author by ID
     * GET /api/v1/authors/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }
    
    /**
     * GET author by name
     * GET /api/v1/authors/search?name=Robert%20Martin
     */
    @GetMapping("/search")
    public ResponseEntity<AuthorDTO> getAuthorByName(@RequestParam String name) {
        AuthorDTO author = authorService.getAuthorByName(name);
        return ResponseEntity.ok(author);
    }
    
    /**
     * GET author by email
     * GET /api/v1/authors/search/email?email=robert@example.com
     */
    @GetMapping("/search/email")
    public ResponseEntity<AuthorDTO> getAuthorByEmail(@RequestParam String email) {
        AuthorDTO author = authorService.getAuthorByEmail(email);
        return ResponseEntity.ok(author);
    }
    
    /**
     * CREATE new author
     * POST /api/v1/authors
     * Body: { "name": "Stephen King", "email": "stephen@example.com" }
     */
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
    
    /**
     * UPDATE author
     * PUT /api/v1/authors/1
     * Body: { "name": "Stephen King Updated", "email": "stephen.new@example.com" }
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO authorDTO
    ) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(updatedAuthor);
    }
    
    /**
     * DELETE author
     * DELETE /api/v1/authors/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}