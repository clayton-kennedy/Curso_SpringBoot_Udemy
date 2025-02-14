package com.cursospringboot.libraryapi.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Table (name = "livro")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Livro {
    @Id
    @GeneratedValue ( strategy = GenerationType.UUID)
    private UUID id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    @NotBlank
    private GeneroLivro genero;

    @Column(name = "isbn", nullable = false)
    @NotBlank
    @ISBN
    private String isbn;

    @Column(name = "titulo", nullable = false)
    @NotBlank
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalTime dataPublicacao;

    @Column(name = "preco", nullable = true)
    @NotNull
    private Double preco;

    @ManyToOne
    @JoinColumn (name = "id_autor")
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizado")
    private LocalDate dataAtualizado;

    public Livro() {}
}
