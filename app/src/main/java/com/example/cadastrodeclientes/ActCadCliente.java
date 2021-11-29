package com.example.cadastrodeclientes;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cadastrodeclientes.database.DadosOpenHelper;
import com.example.cadastrodeclientes.dominio.entidades.Cliente;
import com.example.cadastrodeclientes.dominio.repositorio.ClienteRepositorio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AlertDialogLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cadastrodeclientes.databinding.ActCadClienteBinding;

public class ActCadCliente extends AppCompatActivity {
   /* private FloatingActionButton fab;*/
    private AppBarConfiguration appBarConfiguration;
    private ActCadClienteBinding binding;
    private EditText edtNome;
    private EditText edtIdade;
    private EditText edtEmail;
    private EditText edtEndereco;
    private EditText edtTelefone;

    private ConstraintLayout layoutContentActCadCliente;

    private ClienteRepositorio clienteRepositorio;
    //objeto resposnavel por armazenar a conexão com o banco de dados
    private SQLiteDatabase conexao;
    //criando a instancia dessa classe
    private DadosOpenHelper dadosOpenHelper;
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActCadClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_cad_cliente);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

       /* binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);

        layoutContentActCadCliente = (ConstraintLayout)findViewById(R.id.layoutContentActCadCliente);
    criarConexao();
    verificaParametro();
    }

    private void verificaParametro(){
        Bundle bundle = getIntent().getExtras();

        cliente = new Cliente();

        if((bundle != null) && (bundle.containsKey("CLIENTE"))){

            cliente = (Cliente) bundle.getSerializable("CLIENTE");

            edtNome.setText(cliente.nome);
            edtIdade.setText(cliente.idade);
            edtEmail.setText(cliente.email);
            edtEndereco.setText(cliente.endereco);
            edtTelefone.setText(cliente.telefone);
        }
    }
    private void criarConexao(){

        try{

            dadosOpenHelper = new DadosOpenHelper(this);

            // método para alterar o banco de dados
            conexao = dadosOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentActCadCliente, R.string.message_conexao_criada_com_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();


            clienteRepositorio = new ClienteRepositorio(conexao);

            //verificar o sqlException para importar a conexeão certa
            //usar a do android ao inves do java
        }catch (Exception ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();

        }
    }

    private void confirmar(){


            if (validaCampos() == false){


                try {

                    if (cliente.codigo == 0){
                        clienteRepositorio.inserir(cliente);
                    }
                    else {
                        clienteRepositorio.alterar(cliente);
                    }

                    finish();

                }catch (SQLException ex){

                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle(R.string.title_erro);
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.action_ok, null);
                    dlg.show();

                }
            }
    }

    private boolean validaCampos(){

        boolean res = false;

        String nome = edtNome.getText().toString();
        String idade = edtIdade.getText().toString();
        String email = edtEmail.getText().toString();
        String endereco = edtEndereco.getText().toString();
        String telefone = edtTelefone.getText().toString();

        cliente.nome = nome;
        cliente.idade = endereco;
        cliente.email = email;
        cliente.endereco = endereco;
        cliente.telefone = telefone;

        if (res = isCampoVazio(nome)){
            edtNome.requestFocus();
        }
        else if (res = isCampoVazio(idade)) {
                edtIdade.requestFocus();

        }
        else if (res = isCampoVazio(email)){
            edtEmail.requestFocus();
        }
        else if (res = isCampoVazio(endereco)){
            edtEndereco.requestFocus();
        }
        else if(res = isCampoVazio(telefone)){
            edtTelefone.requestFocus();
        }

        if (res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidos_brancos);
            dlg.setNeutralButton(R.string.lbl_ok,null);
            dlg.show();
        }
        return res;
    }

    private boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isEmailValido(String email){

        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;

            case R.id.action_ok:
                confirmar();
                break;

            case R.id.action_excluir:

                clienteRepositorio.excluir(cliente.codigo);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_cad_cliente);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}