package com.cursospringboot.libraryapi.Controller;
import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.DTO.ErroResposta;
import com.cursospringboot.libraryapi.Exception.AutorNaoEncontrado;
import com.cursospringboot.libraryapi.Exception.OperacaoNaoPermitidaException;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Service.AutorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid AutorDTO autorDTO) {
        try {
            System.out.println(autorDTO);
            Autor autor = autorService.mapearParaAutor(autorDTO);
            Autor autorSalvo = autorService.adicionar(autor);
            URI linkAutor = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(autorSalvo.getId())
                                .toUri();
    
            return ResponseEntity.created(linkAutor).body(autorSalvo);
        
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar autor: " + erro.getMessage() +"\n");
        }
    } 
    //remover
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable UUID id) {
        try {
            autorService.remover(id);
            return ResponseEntity.status(CREATED).body("Autor "+ id + " removido com sucesso!");
        } catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }
    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody @Valid AutorDTO autorDTO) {
    try {
        Autor autorAtualizado = autorService.atualizar(autorService.mapearParaAutor(autorDTO));
        return ResponseEntity.ok(new AutorDTO(autorAtualizado.getId(), autorAtualizado.getNome(), autorAtualizado.getDataNascimento(), autorAtualizado.getNacionalidade(), autorAtualizado.getId_usuario()));

    } catch (AutorNaoEncontrado ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());

    } catch (Exception ex) {
        return ResponseEntity.status(BAD_REQUEST).body("Erro ao atualizar autor: " + ex.getMessage());
    }
}

    
    //buscar um
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPeloId(@PathVariable UUID id) {
        try {
            Optional<Autor> autorEncontrado = autorService.buscarPeloId(id);
            AutorDTO autorDTO = new AutorDTO(autorEncontrado.get().getId() ,autorEncontrado.get().getNome(), autorEncontrado.get().getDataNascimento(), autorEncontrado.get().getNacionalidade(), autorEncontrado.get().getId_usuario());
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
                                                .map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade(), autor.getId_usuario()))
                                                .toList();
            return ResponseEntity.status(CREATED).body(autoresDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }
    //buscar pelo nome e nacionalidade
    @GetMapping("/buscarNomeNacionalidade")
    public ResponseEntity<List<AutorDTO>> buscarNomeNacionalidade(
        @RequestParam(value="nome", required = false) String nome, 
        @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                                .map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade(), autor.getId_usuario()))
                                .toList();
        return ResponseEntity.ok(lista);
    }
}

