package com.example.demo.error;

public class ResourceAlreadyExistsException extends Exception{
    public ResourceAlreadyExistsException(String message) {
        super(message);
            }
    public ResourceAlreadyExistsException(){
            }
}
