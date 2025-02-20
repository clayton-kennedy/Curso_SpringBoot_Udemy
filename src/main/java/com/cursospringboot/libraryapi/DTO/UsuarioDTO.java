package com.cursospringboot.libraryapi.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO (
    String id, 
    
    @NotBlank(message = "Campo nome é obrigatório")
    @Size(max = 100, message = "Campo fora do tamanho padrão.")
    String login, 

    @NotNull(message = "Campo data nascimento é obrigatório")
    String senha,
    
    List<String> roles
){
    
}
