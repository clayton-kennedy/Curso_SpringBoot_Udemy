package com.cursospringboot.libraryapi.Validator;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Exception.AutorNaoEncontrado;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Repository.AutorRepository;

@Component
public class AutorValidator {
    private AutorRepository autorRepository;
    public AutorValidator (AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Boolean JaExisteAutor(Autor autor) {
        if(autorRepository.existsById(autor.getId())){
            return true;
        }
        return false;
    }
    public Boolean JaExisteAutor(UUID id) {
        if(autorRepository.existsById(id)){
            return true;
        }
        return false;
    }
    public Boolean JaExisteAutor(AutorDTO autorDTO) {
        if(autorRepository.existsById(autorDTO.id())){
            return true;
        }
        return false;
    }
    public Autor validarParaAtualizarAutor(AutorDTO dto) {
        return autorRepository.findById(dto.id()).map(autor -> {
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());
            return autor;
        }).orElseThrow(() -> new AutorNaoEncontrado("Não foi possível encontrar o Autor para atualizar."));
    }
}
