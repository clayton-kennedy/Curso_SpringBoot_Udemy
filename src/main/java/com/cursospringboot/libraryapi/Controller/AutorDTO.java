package com.cursospringboot.libraryapi.Controller;

import java.time.LocalDate;

public record AutorDTO (String nome, LocalDate dataNascimento, String nacionalidade) {}

