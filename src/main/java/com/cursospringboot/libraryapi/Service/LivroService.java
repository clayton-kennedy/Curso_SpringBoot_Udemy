package com.cursospringboot.libraryapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.GeneroLivro;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Repository.LivroRepository;
import com.cursospringboot.libraryapi.Repository.Specs.LivroSpecs;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    //listar
    public Livro adicionar(Livro livro) {
        try {
            return livroRepository.save(livro);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return livro;
        }
    }

    //remover
    public void remover(Livro livro) {
        livroRepository.deleteById(livro.getId());
    }

    //atualizar
    public Livro atualizar(Livro livro) {
        return livroRepository.save(livro);
    }

    //buscar um
    public Optional<Livro> buscarPeloId(String id) {
        return livroRepository.findById(id);
    }

    //buscar todos
    public List<Livro> buscarTodos() {
        try {
            return livroRepository.findAll();
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            List<Livro> livros = new ArrayList<>();
            return livros;
        }
    }

    //Busca por filtro
    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {

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
        return livroRepository.findAll(specs);
    }
}
