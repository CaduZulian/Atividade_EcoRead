package com.ecoread.model;

public class Leitura {
    private long id;
    private long apartamentoId;
    private String data;
    private double valorLuz;
    private double valorGas;

    public Leitura() {
    }

    public Leitura(long id, long apartamentoId, String data, double valorLuz, double valorGas) {
        this.id = id;
        this.apartamentoId = apartamentoId;
        this.data = data;
        this.valorLuz = valorLuz;
        this.valorGas = valorGas;
    }

    public Leitura(long apartamentoId, String data, double valorLuz, double valorGas) {
        this.apartamentoId = apartamentoId;
        this.data = data;
        this.valorLuz = valorLuz;
        this.valorGas = valorGas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApartamentoId() {
        return apartamentoId;
    }

    public void setApartamentoId(long apartamentoId) {
        this.apartamentoId = apartamentoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValorLuz() {
        return valorLuz;
    }

    public void setValorLuz(double valorLuz) {
        this.valorLuz = valorLuz;
    }

    public double getValorGas() {
        return valorGas;
    }

    public void setValorGas(double valorGas) {
        this.valorGas = valorGas;
    }
}
