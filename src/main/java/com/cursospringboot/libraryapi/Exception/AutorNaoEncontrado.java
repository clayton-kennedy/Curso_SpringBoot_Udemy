package com.cursospringboot.libraryapi.Exception;

public class AutorNaoEncontrado extends RuntimeException {
    public AutorNaoEncontrado(String message) {
        super(message);
    }
}