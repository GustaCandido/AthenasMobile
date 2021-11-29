package com.example.cadastrodeclientes;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cadastrodeclientes.database.DadosOpenHelper;
import com.example.cadastrodeclientes.dominio.entidades.Cliente;
import com.example.cadastrodeclientes.dominio.repositorio.ClienteRepositorio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastrodeclientes.databinding.ActMainBinding;

import java.util.List;

public class ActMain extends AppCompatActivity {
    private RecyclerView lstDados;
    private FloatingActionButton fab;
    private AppBarConfiguration appBarConfiguration;
    private ActMainBinding binding;
    private ConstraintLayout layoutContentMain;

    //objeto resposnavel por armazenar a conexão com o banco de dados
    private SQLiteDatabase conexao;
    //criando a instancia dessa classe
    private DadosOpenHelper dadosOpenHelper;
    private ClienteRepositorio clienteRepositorio;
    private ClienteAdapter clienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        lstDados = (RecyclerView)findViewById(R.id.lstDados);

        layoutContentMain = (ConstraintLayout)findViewById(R.id.layoutContentMain);
        criarConexao();

        lstDados.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);


        clienteRepositorio = new ClienteRepositorio(conexao);

       List<Cliente> dados = clienteRepositorio.buscarTodos();

        clienteAdapter = new ClienteAdapter(dados);

        lstDados.setAdapter(clienteAdapter);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {*/

            /*@Override
            public void onClick(View view) {

               /*Intent it = new Intent(ActMain.this, ActCadCliente.class);
                startActivity(it);*/

          //  }
       /* });*/

    }

    private void criarConexao(){

        try{

            dadosOpenHelper = new DadosOpenHelper(this);

            // método para alterar o banco de dados
            conexao = dadosOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentMain, R.string.message_conexao_criada_com_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();

            //verificar o sqlException para importar a conexeão certa
            //usar a do android ao inves do java
        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cadastrar(View view){
        Intent it = new Intent(ActMain.this, ActCadCliente.class);
        startActivity(it);
        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        List<Cliente> dados = clienteRepositorio.buscarTodos();
        clienteAdapter = new ClienteAdapter(dados);
        lstDados.setAdapter(clienteAdapter);

    }
}