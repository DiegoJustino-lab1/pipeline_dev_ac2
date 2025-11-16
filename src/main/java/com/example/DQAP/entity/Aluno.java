package com.example.DQAP.entity;

import java.util.List;

import com.example.DQAP.ValueObjects.Email;
import com.example.DQAP.ValueObjects.Senha;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double mediaGeral;

    @Embedded
    private Email email;

    @Embedded
    private Senha senha;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conclusao> conclusoes;

    public Aluno() {}

    public Aluno(Long id, String nome, double mediaGeral, Email email, Senha senha, List<Conclusao> conclusoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.mediaGeral = mediaGeral;
		this.email = email;
		this.senha = senha;
		this.conclusoes = conclusoes;
	}
    
    public void adicionarConclusao(Conclusao conclusao) {
        conclusoes.add(conclusao);
        conclusao.setAluno(this);
    }

    // Método para calcular média atualizada
    public void recalcularMedia() {
        if (conclusoes.isEmpty()) {
            this.mediaGeral = 0.0;
            return;
        }
        
        double soma = conclusoes.stream()
            .filter(Conclusao::isConcluido)
            .mapToDouble(c -> c.getCurso().getNotaFinal())
            .sum();
        
        long total = conclusoes.stream()
            .filter(Conclusao::isConcluido)
            .count();
        
        this.mediaGeral = total > 0 ? soma / total : 0.0;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getMediaGeral() {
        return mediaGeral;
    }

    public void setMediaGeral(double mediaGeral) {
        this.mediaGeral = mediaGeral;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public List<Conclusao> getConclusoes() {
        return conclusoes;
    }

    public void setConclusoes(List<Conclusao> conclusoes) {
        this.conclusoes = conclusoes;
    }

}
