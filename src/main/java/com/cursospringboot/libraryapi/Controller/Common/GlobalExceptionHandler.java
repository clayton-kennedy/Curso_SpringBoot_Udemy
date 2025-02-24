package com.cursospringboot.libraryapi.Controller.Common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cursospringboot.libraryapi.DTO.ErroCampo;
import com.cursospringboot.libraryapi.DTO.ErroResposta;
import com.cursospringboot.libraryapi.Exception.CampoInvalido;
import com.cursospringboot.libraryapi.Exception.OperacaoNaoPermitidaException;
import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta erroArgumentoInvalido(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream()
        .map(errors -> new ErroCampo(errors.getField(), errors.getDefaultMessage()))
        .collect(Collectors.toList());

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Erro de validação do bean validation.",
        listaErros);
    }
    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }
    
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta hanldeOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
        "Erro inesperado. Contate a administração do sistema.", 
        List.of());
    }

    @ExceptionHandler(CampoInvalido.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalido e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), 
                                            "Erro de validação.", 
                                            List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAcessoNegado(AccessDeniedException e) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), 
        "Acesso negado! Você não possui autorização.", 
        List.of());
    }
}
