package com.example.demo.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message){
        super(message);
    }

    public BookNotFoundException(String message,Throwable cause){
        super(message,cause);
    }

    public BookNotFoundException(Long id){
        super("Book not found with ID: "+id);
    }
}
