package com.cursospringboot.libraryapi.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursospringboot.libraryapi.Model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, String> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
    boolean existsByNomeAndNacionalidade(String nome, String nacionalidade);


}

