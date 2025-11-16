package com.example.DQAP.dto;

public class ProgressoDTO {
	
	private String mensagem;
    private long cursosConcluidos;
    private double media;
    private long faltamParaBonus;

    public ProgressoDTO(String mensagem, long cursosConcluidos, double media, long faltamParaBonus) {
        this.mensagem = mensagem;
        this.cursosConcluidos = cursosConcluidos;
        this.media = media;
        this.faltamParaBonus = faltamParaBonus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public long getCursosConcluidos() {
        return cursosConcluidos;
    }

    public double getMedia() {
        return media;
    }

    public long getFaltamParaBonus() {
        return faltamParaBonus;
    }

}
