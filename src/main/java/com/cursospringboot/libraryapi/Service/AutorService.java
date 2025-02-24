package com.cursospringboot.libraryapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.cursospringboot.libraryapi.Exception.OperacaoNaoPermitidaException;
import com.cursospringboot.libraryapi.Exception.RegistroDuplicadoException;
import com.cursospringboot.libraryapi.Model.Autor;
import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Repository.AutorRepository;
import com.cursospringboot.libraryapi.Repository.LivroRepository;
import com.cursospringboot.libraryapi.Security.SecurityService;
import com.cursospringboot.libraryapi.Validator.AutorValidator;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorValidator autorValidator;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private SecurityService securityService;

    //adicionar
    public Autor adicionar(Autor autor) {
            if(autorValidator.existeAutor(autor) == false) {
                Usuario usuario = securityService.obteUsuario();
                autor.setUsuario(usuario);
                return autorRepository.save(autor);
            } else {
                throw new RegistroDuplicadoException("Autor já cadastrado!");
            }
    }
    //remover
    public void remover(String id) {
        try {
            Autor autor = buscarPeloId(id).get();
            if (!possuiLivro(autor)) {
                if (autorValidator.existeAutor(id)) {
                    autorRepository.deleteById(id);
                }
            } else {
                throw new OperacaoNaoPermitidaException("Não foi possível remover pois o autor possui livro cadastrado.");
            }
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }
    //atualizar
    public Autor atualizar(Autor autor) {
        try {
                autorValidator.validarParaAtualizarAutor(autor);
                return autorRepository.save(autor);
        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            throw new OperacaoNaoPermitidaException("Falha ao atualizar autor");
        }
    }
    //buscar um
    public Optional<Autor> buscarPeloId(String id) {
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
    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnorePaths("id","dataNascimento", "dataCadastro")
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
}

