package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import java.util.List;

public interface AuthorService {
    
    AuthorDTO getAuthorById(Long id);
    
    AuthorDTO getAuthorByName(String name);
    
    List<AuthorDTO> getAllAuthors();
    
    AuthorDTO getAuthorByEmail(String email);

    AuthorDTO createAuthor(AuthorDTO authorDTO);
    
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);
    
    void deleteAuthor(Long id);
}