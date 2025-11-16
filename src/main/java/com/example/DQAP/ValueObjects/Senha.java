package com.example.DQAP.ValueObjects;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Senha {

    private String hash;

    protected Senha() {}

    public Senha(String senhaPura) {
        if (senhaPura == null || senhaPura.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }
        this.hash = gerarHash(senhaPura);
    }

    private String gerarHash(String senhaPura) {
        // Apenas para exemplo — use BCrypt ou Argon2 na prática
        return Integer.toHexString(senhaPura.hashCode());
    }

    public boolean verificar(String senhaPura) {
        return this.hash.equals(gerarHash(senhaPura));
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Senha)) return false;
        Senha senha = (Senha) o;
        return Objects.equals(hash, senha.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}

