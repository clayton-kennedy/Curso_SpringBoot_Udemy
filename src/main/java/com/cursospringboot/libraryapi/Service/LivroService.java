package com.cursospringboot.libraryapi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.Model.GeneroLivro;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.LivroRepository;
import com.cursospringboot.libraryapi.Repository.Specs.LivroSpecs;
import com.cursospringboot.libraryapi.Validator.LivroValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    //listar
    public Livro adicionar(Livro livro) {
        if (livro.getId() != null) {
            throw new IllegalArgumentException("Livro já cadastrado!");
        }
        return livroRepository.save(livro);
    }

    //remover
    public void remover(Livro livro) {
        livroRepository.deleteById(livro.getId());
    }

    //atualizar
    public Livro atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja salvo na base.");
        }
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    //buscar um
    public Optional<Livro> buscarPeloId(String id) {
        return livroRepository.findById(id);
    }

    //buscar todos
    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    //Busca por filtro
    public Page<Livro> pesquisa(String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanho_pagina) {

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());
        if (isbn != null) {
            specs = specs.and(LivroSpecs.isbnPesquisada(isbn));
        }
        if (titulo != null) {
            specs = specs.and(LivroSpecs.tituloPesquisado(titulo));
        }
        if (genero != null) {
            specs = specs.and(LivroSpecs.generoPesquisado(genero));
        }
        if (nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorPesquisado(nomeAutor));
        }
        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoPesquisado(anoPublicacao));
        }
        Pageable pageRequest = PageRequest.of(pagina, tamanho_pagina);

        return livroRepository.findAll(specs, pageRequest);
    }
}
