package com.cursospringboot.libraryapi.Exception;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException (String message){
        super(message);
    }
}
