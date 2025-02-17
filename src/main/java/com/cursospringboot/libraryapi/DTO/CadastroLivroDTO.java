package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.cursospringboot.libraryapi.Model.GeneroLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record CadastroLivroDTO (
    @NotBlank(message = "Campo título é obrigatório")
    @Size(max = 120, message = "Campo fora do tamanho padrão.")
    String titulo, 

    @NotBlank(message = "Campo isbn é obrigatório")
    @Size(max = 50, message = "Campo fora do tamanho padrão.")
    @ISBN
    String isbn,

    @NotNull(message = "Campo data de publicação é obrigatório")
    @PastOrPresent(message = "Data de publicação não pode ser futura à data atual.")
    LocalDate dataPublicacao,

    GeneroLivro genero,

    Double preco,

    @NotNull(message = "Campo de Id do autor é obrigatório.")
    UUID idAutor

    ){

    public String titulo() {
        return titulo;
    }

    public String isbn() {
        return isbn;
    }

    public LocalDate dataPublicacao() {
        return dataPublicacao;
    }

    public GeneroLivro genero() {
        return genero;
    }

    public Double preco() {
        return preco;
    }

    public UUID idAutor() {
        return idAutor;
    }

    }

