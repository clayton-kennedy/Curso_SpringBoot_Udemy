package com.cursospringboot.libraryapi.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, String>, JpaSpecificationExecutor<Livro> {
    Boolean existsByAutor (Autor autor);
    List<Livro> findByAutor(Autor autor);
    Optional<Livro> findByIsbn(String isbn);
    Page<Livro> findByAutor(Autor autor, Pageable pageable);
}
