package com.cursospringboot.libraryapi.Validator;

import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository; 

    public void validar(Livro livro) {
        if(existeLivroPeloIsbn(livro)) {
            if ((livroRepository.findById(livro.getId())).isPresent()) {
                //atualizar
            } 
            Throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }
    }
    
    public boolean existeLivroPeloIsbn(Livro livro) {
        return livroRepository.existsByIsbn(livro.getIsbn());
    }
}
