package com.cursospringboot.libraryapi.Controller;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.libraryapi.Controller.Mappers.AutorMapper;
import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.DTO.ErroResposta;
import com.cursospringboot.libraryapi.Exception.AutorNaoEncontrado;
import com.cursospringboot.libraryapi.Exception.OperacaoNaoPermitidaException;
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
    private AutorService autorService;
    @Autowired
    private AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid AutorDTO autorDTO) {
        try {
            Autor autor = autorService.adicionar(autorMapper.toEntity(autorDTO));
            URI linkAutor = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.status(OK).body(autor);

        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar autor: " + erro.getMessage() + "\n");
        }
    }

    //remover
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable UUID id) {
        try {
            autorService.remover(id);
            return ResponseEntity.status(NO_CONTENT).build();

        } catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody @Valid AutorDTO autorDTO) {
        try {
            Optional<Autor> autorEncontrado = autorService.buscarPeloId(autorDTO.id());
            if ( !autorEncontrado.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var autor = autorEncontrado.get();
            autor.setId(autorDTO.id());
            autor.setNome(autorDTO.nome());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autor.setDataNascimento(autorDTO.dataNascimento());

            Autor autorAtualizado = autorService.atualizar(autor);
            return ResponseEntity.ok(autorAtualizado);
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
            return autorService.buscarPeloId(id)
                    .map(autor -> {
                        AutorDTO dto = autorMapper.toDTO(autor);
                        return ResponseEntity.ok(dto);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());

        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro.getMessage());
        }
    }

    //buscar todos
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Autor> autores = autorService.buscarTodos();
            List<AutorDTO> autoresDTO = autores.stream()
                    .map(autor -> autorMapper.toDTO(autor))
                    .toList();
            return ResponseEntity.status(CREATED).body(autoresDTO);
        } catch (Exception erro) {
            return ResponseEntity.status(BAD_REQUEST).body(erro.getMessage());
        }
    }

    //buscar pelo nome e nacionalidade
    @GetMapping("/pesquisar")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }    
}
