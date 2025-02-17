package com.cursospringboot.libraryapi.Controller.Mappers;

import org.mapstruct.Mapper;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO (Autor autor);
}
