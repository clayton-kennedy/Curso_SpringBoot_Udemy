package com.cursospringboot.libraryapi.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.libraryapi.Controller.Mappers.UsuarioMapper;
import com.cursospringboot.libraryapi.DTO.UsuarioDTO;
import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Service.UsuarioService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar (UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuarioService.adicionar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    
}
