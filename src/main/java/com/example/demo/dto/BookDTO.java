package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class BookDTO {
    
    private Long id;
    
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    
    @NotNull(message = "Author ID cannot be null")
    private Long authorId;
    
    private String authorName;
    
    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be exactly 13 digits")
    private String isbn;
    
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private Double price;
    
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public BookDTO() {
    }
    
    public BookDTO(String title, Long authorId, String authorName, String isbn, Double price, String description) {
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.isbn = isbn;
        this.price = price;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Long getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}