package com.cursospringboot.libraryapi.Validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.Exception.CampoInvalido;
import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository; 

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }
        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalido("preco", "Para livros com ano de publicação a partir de 2020, o campo preço é obrigatório.");
        }
    }

    public boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());
        if (livro.getId() == null) {
            return livroEncontrado.isPresent();
        }
        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
    
    public boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= 2020;
    }
}
