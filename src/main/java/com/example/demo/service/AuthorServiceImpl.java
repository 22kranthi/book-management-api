package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    // @Autowired
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));
        return convertToDTO(author);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with name: " + name));
        return convertToDTO(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));

        if (authorDTO.getName() != null && !authorDTO.getName().isEmpty()) {
            author.setName(authorDTO.getName());
        }
        if (authorDTO.getEmail() != null && !authorDTO.getEmail().isEmpty()) {
            author.setEmail(authorDTO.getEmail());
        }

        Author updatedAuthor = authorRepository.save(author);
        return convertToDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new IllegalArgumentException("Author not found with ID: " + id);
        }
        authorRepository.deleteById(id);
    }

    // Helper methods
    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setEmail(author.getEmail());
        return dto;
    }

    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        return author;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorByEmail(String email) {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with email: " + email));
        return convertToDTO(author);
    }
}