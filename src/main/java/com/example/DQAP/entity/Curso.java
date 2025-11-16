package com.example.DQAP.entity;

import com.example.DQAP.ValueObjects.IdentificacaoCurso;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Curso {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private IdentificacaoCurso identificacao;

    private String titulo;
    private double notaFinal;

    public Curso() {}

    public Curso(IdentificacaoCurso identificacao, String titulo, double notaFinal) {
        this.identificacao = identificacao;
        this.titulo = titulo;
        this.notaFinal = notaFinal;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public IdentificacaoCurso getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(IdentificacaoCurso identificacao) {
        this.identificacao = identificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
