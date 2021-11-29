package com.example.cadastrodeclientes.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cadastrodeclientes.dominio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio {

    private SQLiteDatabase conexao;
    public ClienteRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", cliente.nome);
        contentValues.put("IDADE", cliente.idade);
        contentValues.put("EMAIL", cliente.email);
        contentValues.put("ENDERECO", cliente.endereco);
        contentValues.put("TELEFONE", cliente.telefone);

        conexao.insert("tb_clientes", null, contentValues);
    }

    public void excluir(int codigo){
        String[] parametros = new String [1];
        parametros [0] = String.valueOf(codigo);
        conexao.delete("tb_clientes", "CODIGO = ? ", parametros);
    }


    public void alterar (Cliente cliente){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", cliente.nome);
        contentValues.put("IDADE", cliente.idade);
        contentValues.put("EMAIL", cliente.email);
        contentValues.put("ENDERECO", cliente.endereco);
        contentValues.put("TELEFONE", cliente.telefone);

        String[] parametros = new String [1];
        parametros [0] = String.valueOf(cliente.codigo);
        conexao.update("tb_clientes", contentValues, "CODIGO = ? ", parametros);
    }

    public List<Cliente> buscarTodos(){

        List<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, NOME, IDADE, EMAIL, ENDERECO, TELEFONE ");
        sql.append(" FROM tb_clientes");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Cliente cli = new Cliente();

                cli.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.nome = resultado.getString (resultado.getColumnIndexOrThrow("NOME"));
                cli.idade = resultado.getString (resultado.getColumnIndexOrThrow("IDADE"));
                cli.email = resultado.getString (resultado.getColumnIndexOrThrow("EMAIL"));
                cli.endereco = resultado.getString (resultado.getColumnIndexOrThrow("ENDERECO"));
                cli.telefone = resultado.getString (resultado.getColumnIndexOrThrow("TELEFONE"));

                clientes.add(cli);

            }while(resultado.moveToNext());
        }

        return clientes;
    }

    public Cliente buscarCliente(int codigo){

        Cliente cliente = new Cliente();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, NOME, IDADE, EMAIL, ENDERECO, TELEFONE");
        sql.append(" FROM tb_clientes");
        sql.append("WHERE CODIGO = ?");

        String[] parametros = new String [1];
        parametros [0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0){

            resultado.moveToFirst();

                cliente.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cliente.nome = resultado.getString (resultado.getColumnIndexOrThrow("NOME"));
                cliente.idade = resultado.getString (resultado.getColumnIndexOrThrow("IDADE"));
                cliente.email = resultado.getString (resultado.getColumnIndexOrThrow("EMAIL"));
                cliente.endereco = resultado.getString (resultado.getColumnIndexOrThrow("ENDERECO"));
                cliente.telefone = resultado.getString (resultado.getColumnIndexOrThrow("TELEFONE"));


                return cliente;
        }

        return null;
    }
}
