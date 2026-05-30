package com.ecoread.model;

public class Apartamento {
    private long id;
    private String numero;
    private String bloco;
    private long proprietarioId;

    public Apartamento() {
    }

    public Apartamento(long id, String numero, String bloco, long proprietarioId) {
        this.id = id;
        this.numero = numero;
        this.bloco = bloco;
        this.proprietarioId = proprietarioId;
    }

    public Apartamento(String numero, String bloco, long proprietarioId) {
        this.numero = numero;
        this.bloco = bloco;
        this.proprietarioId = proprietarioId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public long getProprietarioId() {
        return proprietarioId;
    }

    public void setProprietarioId(long proprietarioId) {
        this.proprietarioId = proprietarioId;
    }

    @Override
    public String toString() {
        return "Apto: " + numero + " - Bloco: " + bloco;
    }
}
