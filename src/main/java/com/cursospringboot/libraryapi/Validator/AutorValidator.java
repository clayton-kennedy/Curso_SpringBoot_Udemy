package com.cursospringboot.libraryapi.Validator;

import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Exception.AutorNaoEncontrado;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutorValidator {
    final AutorRepository autorRepository;

    public Boolean existeAutor(Autor autor) {
        return autorRepository.existsByNomeAndNacionalidade(autor.getNome(), autor.getNacionalidade());
    }    
    public Boolean existeAutor(String id) {
        return autorRepository.existsById(id);
    }
    public Boolean existeAutor(AutorDTO autorDTO) {
        return autorRepository.existsById(autorDTO.id());
    }
    public Autor validarParaAtualizarAutor(Autor dto) {
        return autorRepository.findById(dto.getId()).map(autor -> {
            autor.setNome(dto.getNome());
            autor.setNacionalidade(dto.getNacionalidade());
            autor.setDataNascimento(dto.getDataNascimento());
            return autor;
        }).orElseThrow(() -> new AutorNaoEncontrado("Não foi possível encontrar o Autor para atualizar."));
    }
}
