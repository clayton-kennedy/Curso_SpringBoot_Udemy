package com.cursospringboot.libraryapi.Controller.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.cursospringboot.libraryapi.DTO.CadastroLivroDTO;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorRepository.class)
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.IdAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract CadastroLivroDTO toDto(Livro livro);
}
