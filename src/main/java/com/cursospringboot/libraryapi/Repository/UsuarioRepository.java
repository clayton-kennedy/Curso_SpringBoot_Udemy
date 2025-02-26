package com.cursospringboot.libraryapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursospringboot.libraryapi.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    Usuario findByLogin(String login);
    Usuario findByEmail(String email);
}
