package com.cursospringboot.libraryapi.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospringboot.libraryapi.Controller.Mappers.AutorMapper;
import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Service.AutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;
    @Autowired
    private AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> adicionar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = autorService.adicionar(autorMapper.toEntity(autorDTO));
        autorService.adicionar(autor);

        URI linkAutor = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();
        return ResponseEntity.created(linkAutor).body(autor);
    }

    //remover
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> remover(@PathVariable String id) {
        autorService.remover(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    //atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> atualizar(@RequestBody @Valid AutorDTO autorDTO) {
        Optional<Autor> autorEncontrado = autorService.buscarPeloId(autorDTO.id());
        if (!autorEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorEncontrado.get();
        autor.setId(autorDTO.id());
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        Autor autorAtualizado = autorService.atualizar(autor);
        return ResponseEntity.ok(autorAtualizado);
    }

    //buscar um
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<?> buscarPeloId(@PathVariable String id) {
        return autorService.buscarPeloId(id)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //buscar todos
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<?> buscarTodos() {
        List<Autor> autores = autorService.buscarTodos();
        List<AutorDTO> autoresDTO = autores.stream()
                .map(autor -> autorMapper.toDTO(autor))
                .toList();
        return ResponseEntity.status(CREATED).body(autoresDTO);
    }

    //buscar pelo nome e nacionalidade
    @GetMapping("/pesquisar")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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
