package com.cursospringboot.libraryapi.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obterPorLogin(login);

        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return User.builder()
        .username(usuario.getLogin())
        .password(usuario.getSenha())
        .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
        .build();
    }
}
