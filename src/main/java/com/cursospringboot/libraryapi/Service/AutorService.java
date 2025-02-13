package com.cursospringboot.libraryapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cursospringboot.libraryapi.Controller.AutorDTO;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Repository.AutorRepository;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    //listar
    public Autor adicionar(Autor autor) {
        try {
            return autorRepository.save(autor);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return autor;
        }
    }
    //remover
    public String remover(UUID id) {
        try {
            if (autorRepository.existsById(id)) {
                autorRepository.deleteById(id);
                return "Autor removido com sucesso!";
            }
            return "Autor não encontrado.";
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return "Não foi possível remover o autor.";
        }
    }
    //atualizar
    public Autor atualizar(Autor autor) {
        try {
            return autorRepository.save(autor);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return autor;
        }
    }
    //buscar um
    public Optional<Autor> buscarPeloId(UUID id) {
        try {
            return autorRepository.findById(id);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return Optional.empty();
        }
    }
    //buscar todos
    public List<Autor> buscarTodos() {
        try {
            return autorRepository.findAll();
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            List<Autor> autores = new ArrayList<>();
            return autores;
        }
    }
    //buscar pelo nome e nacionalidade
    public List<Autor> buscarNomeNacionalidade (String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.buscarNomeNacionalidade(nome, nacionalidade);
        } else if (nome != null && nacionalidade == null) {
            return autorRepository.buscarNome(nome);
        } else if (nome == null && nacionalidade != null) {
            return autorRepository.buscarNacionalidade(nacionalidade);
        } else {
            System.out.println("Campos para pesquisa vazios.");
            return List.of();
        }
    }

}

