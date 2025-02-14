package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AutorDTO (
    UUID id, 
    @NotBlank(message = "Campo nome é obrigatório")
    @Size(max = 100)
    String nome, 
    @NotNull(message = "Campo data nascimento é obrigatório")
    LocalDate dataNascimento, 
    @NotBlank(message = "Campo nacionalidade é obrigatório")
    @Size(max = 20)
    String nacionalidade) {}

