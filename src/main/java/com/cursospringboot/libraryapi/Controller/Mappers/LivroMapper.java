package com.cursospringboot.libraryapi.Controller.Mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cursospringboot.libraryapi.DTO.CadastroLivroDTO;
import com.cursospringboot.libraryapi.DTO.ResultadoPesquisaLivroDTO;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public interface LivroMapper {

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizado", ignore = true)
    Livro toEntity(CadastroLivroDTO dto, @Context AutorRepository autorRepository);

    public abstract ResultadoPesquisaLivroDTO toDto(Livro livro);
}
