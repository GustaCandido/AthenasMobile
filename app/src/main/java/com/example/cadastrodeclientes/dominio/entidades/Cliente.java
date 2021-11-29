package com.example.cadastrodeclientes.dominio.entidades;

import java.io.Serializable;

public class Cliente implements Serializable {

    public int codigo;
    public String nome;
    public String idade;
    public String email;
    public String endereco;
    public String telefone;

    public Cliente(){
        codigo = 0;
    }
}
