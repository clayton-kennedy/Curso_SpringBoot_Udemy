package com.cursospringboot.libraryapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.DTO.AutorDTO;
import com.cursospringboot.libraryapi.Exception.OperacaoNaoPermitidaException;
import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Repository.LivroRepository;
import com.cursospringboot.libraryapi.Validator.AutorValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {
    AutorRepository autorRepository;
    AutorValidator autorValidator;
    LivroRepository livroRepository;

    //listar
    public Autor adicionar(Autor autor) {
        try {
            if(!autorValidator.ExisteAutor(autor)) {
            return autorRepository.save(autor);
            }
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }
    //remover
    public String remover(UUID id) {
        try {
            Autor autor = buscarPeloId(id).get();
            if (!possuiLivro(autor)) {
                if (autorValidator.ExisteAutor(id)) {
                    autorRepository.deleteById(id);
                    return "Autor removido com sucesso!";
                }
                return "Autor não encontrado.";
        } 
        throw new OperacaoNaoPermitidaException("Não foi possível remover pois o autor possui livro cadastrado.");
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return "Não foi possível remover o autor.";
        }
    }
    //atualizar
    public Autor atualizar(Autor autor) {
        Autor autorfalho = new Autor();
        try {
                autorValidator.validarParaAtualizarAutor(autor);
                return autorRepository.save(autor);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return autorfalho;
        }
    }
    //buscar um
    public Optional<Autor> buscarPeloId(UUID id) {
        try {
            return autorRepository.findById(id);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            return Optional.empty();
        }
    }
    //buscar todos
    public List<Autor> buscarTodos() {
        try {
            return autorRepository.findAll();
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            List<Autor> autores = new ArrayList<>();
            return autores;
        }
    }
    //buscar pelo nome e nacionalidade
    public List<Autor> pesquisa (String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null && nacionalidade == null) {
            return autorRepository.findByNome(nome);
        } else if (nome == null && nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        } else {
            System.out.println("Campos para pesquisa vazios.");
            return List.of();
        }
    }
    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnorePaths("id","dataNascimento", "dataCAdastro")
        .withIgnoreNullValues()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }
    //verificar se o autor possui livro
    public Boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
    //transformar dto em autor
    public Autor mapearParaAutor(AutorDTO dto) {
        Autor autor = new Autor(dto.id(),dto.nome(),dto.nacionalidade(),dto.dataNascimento());
        return autor;
    }
}

