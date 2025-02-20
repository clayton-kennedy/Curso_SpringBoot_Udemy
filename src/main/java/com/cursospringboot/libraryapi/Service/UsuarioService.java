package com.cursospringboot.libraryapi.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void adicionar (Usuario usuario) {
        var senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));

        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin (String login) {
        return usuarioRepository.findByLogin(login);
    }

    
}
