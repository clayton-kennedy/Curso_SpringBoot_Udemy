package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

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
    String nacionalidade,
    
    @Nullable
    UUID id_usuario
    ) {

    public UUID id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public LocalDate dataNascimento() {
        return dataNascimento;
    }

    public String nacionalidade() {
        return nacionalidade;
    }

    public UUID id_usuario() {
        return id_usuario;
    }

    }

