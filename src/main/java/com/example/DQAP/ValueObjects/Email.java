package com.example.DQAP.ValueObjects;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

    private String endereco;

    protected Email() {} // Construtor padrão exigido pelo JPA

    public Email(String endereco) {
        if (endereco == null || !endereco.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            throw new IllegalArgumentException("Email inválido: " + endereco);
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return Objects.equals(endereco, email.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endereco);
    }

    @Override
    public String toString() {
        return endereco;
    }
}

