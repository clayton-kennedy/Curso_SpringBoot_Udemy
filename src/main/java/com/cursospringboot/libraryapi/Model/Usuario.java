package com.cursospringboot.libraryapi.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;
    
    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String senha;

    @NotNull
    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition= "varchar[]")
    private List<String> roles;

    @NotNull
    @Email
    @Column (name = "email")
    private String email;

    public Usuario() {
    }

    public Usuario(String id, String login, String senha, List<String> roles, String email) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.roles = roles;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   

    
}
