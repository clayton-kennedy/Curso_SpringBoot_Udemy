package com.cursospringboot.libraryapi.Controller.Mappers;

import org.mapstruct.Mapper;

import com.cursospringboot.libraryapi.DTO.UsuarioDTO;
import com.cursospringboot.libraryapi.Model.Usuario;

@Mapper (componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO (Usuario usuario);
}
