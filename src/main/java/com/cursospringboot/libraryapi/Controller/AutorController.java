package com.cursospringboot.libraryapi.Controller;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Service.AutorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/autores")
public class AutorController {
    @Autowired
    AutorService autorService;
    
    //adicionar
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid Autor autor) {
        try {
            Autor autorSalvo = autorService.adicionar(autor);
            AutorDTO autorDTO = new AutorDTO(autorSalvo.getNome(), autorSalvo.getDataNascimento(), autorSalvo.getNacionalidade());

            URI linkAutor = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                .buildAndExpand(autorSalvo.getId()).toUri();
            return ResponseEntity.created(linkAutor).body(autorDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //remover
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable @Valid UUID id) {
        try {
            autorService.remover(id);
            return ResponseEntity.status(CREATED).body("Autor "+ id + "removido com sucesso!");
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody @Valid Autor autor) {
        try {
            Autor autorAtualizado = autorService.atualizar(autor);
            AutorDTO autorDTO = new AutorDTO(autorAtualizado.getNome(), autorAtualizado.getDataNascimento(), autorAtualizado.getNacionalidade());
            return ResponseEntity.status(CREATED).body(autorDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //buscar um
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPeloId(UUID id) {
        try {
            Optional<Autor> autorEncontrado = autorService.buscarPeloId(id);
            AutorDTO autorDTO = new AutorDTO(autorEncontrado.get().getNome(), autorEncontrado.get().getDataNascimento(), autorEncontrado.get().getNacionalidade());
            return ResponseEntity.status(CREATED).body(autorDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //buscar todos
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Autor> autores = autorService.buscarTodos();
            List<AutorDTO> autoresDTO = autores.stream()
                                                .map(autor -> new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()))
                                                .toList();
            return ResponseEntity.status(CREATED).body(autoresDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //buscar pelo nome e nacionalidade
    @GetMapping
    public ResponseEntity<List<AutorDTO>> buscarNomeNacionalidade(
        @RequestParam(value="nome", required = false) String nome, 
        @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.buscarNomeNacionalidade(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                                .map(autor -> new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()))
                                .toList();
        return ResponseEntity.ok(lista);
    }
    
}

