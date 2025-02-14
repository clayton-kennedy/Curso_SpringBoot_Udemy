package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;

public record AutorDTO (
    UUID id, 
    
    @NotBlank(message = "Campo nome é obrigatório")
    @Size(max = 100, message = "Campo fora do tamanho padrão.")
    String nome, 

    @NotNull(message = "Campo data nascimento é obrigatório")
    @Past(message = "Data de nascimento não pode ser futura à data atual.")
    LocalDate dataNascimento, 

    @NotBlank(message = "Campo nacionalidade é obrigatório")
    @Size(max = 20, message = "Campo fora do tamanho padrão.")
    String nacionalidade) {

    }

