package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.cursospringboot.libraryapi.Model.GeneroLivro;

import jakarta.validation.constraints.*;

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

    }

