package com.cursospringboot.libraryapi.Validator;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Exception.AutorNaoEncontrado;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Service.AutorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutorValidator {
    final AutorRepository autorRepository;
    final AutorService autorService;


    public Boolean ExisteAutor(Autor autor) {
        return !autorRepository.findByNomeAndNacionalidade(autor.getNome(), autor.getNacionalidade()).isEmpty();
    }
    public Boolean ExisteAutor(UUID id) {
        return autorRepository.existsById(id);
    }
    public Boolean ExisteAutor(AutorDTO autorDTO) {
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
