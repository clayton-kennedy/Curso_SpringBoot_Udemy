package com.cursospringboot.libraryapi.Controller;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursospringboot.libraryapi.Controller.Mappers.LivroMapper;
import com.cursospringboot.libraryapi.DTO.CadastroLivroDTO;
import com.cursospringboot.libraryapi.DTO.ErroResposta;
import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.Livro;
import com.cursospringboot.libraryapi.Service.AutorService;
import com.cursospringboot.libraryapi.Service.LivroService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    LivroService livroService;
    AutorService autorService;
    LivroMapper livroMapper;

    //listar
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = livroMapper.toEntity(dto);
            Livro livroSalvo = livroService.adicionar(livro);
                URI linkLivro = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(livroSalvo.getId())
                    .toUri();
            CadastroLivroDTO dadosLivroCadastrado = livroMapper.toDto(livroSalvo);
            return ResponseEntity.created(linkLivro).body(dadosLivroCadastrado);
        } catch (RegistroDuplicadoException e) {
            var erro = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    //remover
    @DeleteMapping
    public String remover(UUID id) {
        try {
            livroService.remover(id);
            return "Livro removido com sucesso!";
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return "Não foi possível remover o livro.";
        }
    }

    //atualizar
    @PutMapping
    public Livro atualizar(@RequestBody @Valid Livro livro) {
        try {
            return livroService.atualizar(livro);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return livro;
        }
    }

    //buscar um
    @GetMapping("/{id}")
    public Optional<Livro> buscarPeloId(UUID id) {
        try {
            return livroService.buscarPeloId(id);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return Optional.empty();
        }
    }

    //buscar todos
    @GetMapping
    public List<Livro> buscarTodos() {
        try {
            return livroService.buscarTodos();
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            List<Livro> livros = new ArrayList<>();
            return livros;
        }
    }

    //buscar livro e autor
    @GetMapping("/buscarLivroEAutor")
    public List<?> buscarLivroAutor(UUID idLivro, UUID idAutor) {
        Optional<Livro> livro = livroService.buscarPeloId(idLivro);
        Optional<Autor> autor = autorService.buscarPeloId(idAutor);
        List op = new ArrayList<>();

        if (livro.isPresent() && autor.isPresent()) {
            op.add(livro.get());
            op.add(autor.get());
            return op;
        } else {
            return op;
        }
    }
}

