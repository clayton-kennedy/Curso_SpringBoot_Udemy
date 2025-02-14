package com.cursospringboot.libraryapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    LivroRepository livroRepository;
    AutorRepository autorRepository;

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
    public String remover(UUID id) {
        try {
            livroRepository.deleteById(id);
            return "Livro removido com sucesso!";
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return "Não foi possível remover o livro.";
        }
    }

    //atualizar
    public Livro atualizar(Livro livro) {
        try {
            return livroRepository.save(livro);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return livro;
        }
    }

    //buscar um
    public Optional<Livro> buscarPeloId(UUID id) {
        try {
            return livroRepository.findById(id);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return Optional.empty();
        }
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

    //buscar livro e autor
    public List<?> buscarLivroAutor(UUID idLivro, UUID idAutor) {
        Optional<Livro> livro = livroRepository.findById(idLivro);
        Optional<Autor> autor = autorRepository.findById(idAutor);
        List op = new ArrayList<>();

        if (livro.isPresent() && autor.isPresent()) {
            op.add(livro.get());
            op.add(autor.get());
            return op;
        } else {
            return op;
        }
    }
}
