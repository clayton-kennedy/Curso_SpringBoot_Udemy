package com.cursospringboot.libraryapi.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospringboot.libraryapi.Controller.Mappers.LivroMapper;
import com.cursospringboot.libraryapi.DTO.CadastroLivroDTO;
import com.cursospringboot.libraryapi.DTO.ResultadoPesquisaLivroDTO;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.GeneroLivro;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Service.AutorService;
import com.cursospringboot.libraryapi.Service.LivroService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    LivroService livroService;
    LivroMapper livroMapper;
    AutorRepository autorRepository;

    //listar
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto, autorRepository);
        Livro livroSalvo = livroService.adicionar(livro);
        URI linkLivro = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroSalvo.getId())
                .toUri();
        return ResponseEntity.created(linkLivro).build();
    }

    //remover
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable String id) {
        return livroService.buscarPeloId(id).map(livro -> {
            livroService.remover(livro);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto) {
        return livroService.buscarPeloId(id).map(livro -> {
            Livro entidadeAux = livroMapper.toEntity(dto, autorRepository);
    
            livro.setDataPublicacao(entidadeAux.getDataPublicacao());
            livro.setIsbn(entidadeAux.getIsbn());
            livro.setPreco(entidadeAux.getPreco());
            livro.setTitulo(entidadeAux.getTitulo());
            livro.setGenero(entidadeAux.getGenero());
            livro.setAutor(entidadeAux.getAutor());
    
            return ResponseEntity.ok(livroService.atualizar(livro));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }    

    //buscar um
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarPeloId(@PathVariable String id) {
        return livroService.buscarPeloId(id)
                .map(livro -> {
                    var dto = livroMapper.toDto(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //buscar todos
    @GetMapping
    public List<Livro> buscarTodos() {
        return livroService.buscarTodos();
    }

    //buscar livro e autor
    @GetMapping("/pesquisa")
    public ResponseEntity<?> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao
    ) {
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado.stream().map(livroMapper::toDto).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

}
