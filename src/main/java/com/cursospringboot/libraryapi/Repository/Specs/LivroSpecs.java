package com.cursospringboot.libraryapi.Repository.Specs;

import org.springframework.data.jpa.domain.Specification;

import com.cursospringboot.libraryapi.Model.GeneroLivro;
import com.cursospringboot.libraryapi.Model.Livro;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class LivroSpecs {
    public static Specification<Livro> isbnPesquisada (String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloPesquisado(String titulo) {
        return (root, query, cb) -> cb.like(
            cb.upper(cb.upper(root.get("titulo"))), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoPesquisado(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }
    public static Specification<Livro> nomeAutorPesquisado(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get(nome)), "%" + nome.toUpperCase() + "%");
        };
    }
    public static Specification<Livro> anoPublicacaoPesquisado(Integer anoPublicacao) {
        return (root, query, cb) -> cb.equal(
            cb.function("to_char", String.class, root.get(
                "dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }
    
}
