package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class AuthorDTO {
    
    private Long id;
    
    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;
    
    @Email(message = "Email should be valid")
    private String email;
    
    // Constructors
    public AuthorDTO() {
    }
    
    public AuthorDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}