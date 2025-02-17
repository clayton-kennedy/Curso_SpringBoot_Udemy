package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.cursospringboot.libraryapi.Model.GeneroLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record ResultadoPesquisaLivroDTO (
    UUID id, 
    
    @NotBlank(message = "Campo nacionalidade é obrigatório")
    @Size(max = 50, message = "Campo fora do tamanho padrão.")
    @ISBN
    String isbn,

    @NotBlank(message = "Campo nome é obrigatório")
    @Size(max = 120, message = "Campo fora do tamanho padrão.")
    String titulo, 

    @NotNull(message = "Campo data nascimento é obrigatório")
    @PastOrPresent(message = "Data de publicação não pode ser futura à data atual.")
    LocalDate dataPublicacao,

    GeneroLivro genero,

    Double preco,

    @NotNull(message = "Campo de Id do autor é obrigatório.")
    AutorDTO Autor

) {
    
}
