package com.cursospringboot.libraryapi.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

   public Usuario obteUsuario() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof CustomAuthentication customAuth) {
        return customAuth.getUsuario();
    }
    return null;
   } 
}
