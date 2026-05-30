package com.ecoread.model;

public class Proprietario {
    private long id;
    private String nome;
    private String cpf;
    private String contato;

    public Proprietario() {
    }

    public Proprietario(long id, String nome, String cpf, String contato) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
    }

    public Proprietario(String nome, String cpf, String contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return nome; // Useful for Spinners
    }
}
