package com.example.demo.error;


public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(){
    }
}
