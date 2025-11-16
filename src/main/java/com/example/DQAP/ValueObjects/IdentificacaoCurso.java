package com.example.DQAP.ValueObjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class IdentificacaoCurso {

    private String codigo;

    protected IdentificacaoCurso() {
        // Construtor protegido para uso do JPA
    }

    public IdentificacaoCurso(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do curso não pode ser nulo ou vazio.");
        }
        this.codigo = codigo.toUpperCase().trim();
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentificacaoCurso)) return false;
        IdentificacaoCurso that = (IdentificacaoCurso) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}

