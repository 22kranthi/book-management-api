package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class UpdateBookDTO {
    
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    
    private Long authorId;
    
    @Positive(message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private Double price;
    
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;
    
    // Constructors
    public UpdateBookDTO() {
    }
    
    public UpdateBookDTO(String title, Long authorId, Double price, String description) {
        this.title = title;
        this.authorId = authorId;
        this.price = price;
        this.description = description;
    }
    
    // Getters and Setters
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
}