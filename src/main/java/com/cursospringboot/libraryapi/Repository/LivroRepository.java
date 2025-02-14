package com.cursospringboot.libraryapi.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    Boolean existsByAutor (Autor autor);
    
}
