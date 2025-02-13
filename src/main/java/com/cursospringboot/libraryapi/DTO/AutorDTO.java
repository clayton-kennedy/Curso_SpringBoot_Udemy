package com.cursospringboot.libraryapi.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO (UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {}

