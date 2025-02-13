package com.cursospringboot.libraryapi.Repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospringboot.libraryapi.Model.Autor;
import java.util.List;


@Repository
public interface  AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> buscarNome(String nome);
    List<Autor> buscarNacionalidade(String nacionalidade);
    List<Autor> buscarNomeNacionalidade(String nome, String nacionalidade);
}

