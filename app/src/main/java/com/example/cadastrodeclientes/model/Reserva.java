package com.example.cadastrodeclientes.model;

import java.io.Serializable;

public class Reserva implements Serializable {


    //declarando os atributos utilizados
    private Long id;
    private String nomeCidade;
    private String nomeHotel;
    private int data;


    //declarando os modificadores de acesso
    @Override

    public String toString(){
        return nomeCidade.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeHotel() {
        return nomeHotel;
    }

    public void setNomeHotel(String nomeHotel) {
        this.nomeHotel = nomeHotel;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
