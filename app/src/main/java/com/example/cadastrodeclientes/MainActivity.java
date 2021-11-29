package com.example.cadastrodeclientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastrodeclientes.BDHelper.ReservaBd;
import com.example.cadastrodeclientes.model.Reserva;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    ReservaBd reservaBd;
    ArrayList<Reserva> listview_Reserva;
    Reserva reserva;
    ArrayAdapter adapterr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        lista = (ListView) findViewById(R.id.listview_Reserva);
        registerForContextMenu(lista);


        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reserva reservaEscolhida = (Reserva) adapterr.getItemAtPosition(position);
                int position = 0;
                Reserva reservaEscolhida = (Reserva) adapterr.getItem(position);
                Intent i = new Intent(MainActivity.this, FormularioReserva.class);
                i.putExtra("reserva-escolhida", reservaEscolhida);
                MainActivity.this.startActivity(i);
            }
        });



        //chamando outro formulário
        Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btnCadastrar.setOnClickListener(new  android.view.View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, FormularioReserva.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar esta reserva");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                reservaBd = new ReservaBd(MainActivity.this);
                reservaBd.deletarReserva(reserva);
                reservaBd.close();

                carregarReserva();
                return true;
            }
        });
    }

    protected void onResume(){
        super.onResume();
        carregarReserva();
        }

        // método de carregar a reserva ja feita
        public void carregarReserva() {
            reservaBd = new ReservaBd(MainActivity.this);
            listview_Reserva = reservaBd.getLista();
            reservaBd.close();

            if (listview_Reserva != null){
                adapterr = new ArrayAdapter<Reserva>(MainActivity.this, android.R.layout.simple_list_item_1, listview_Reserva);
                lista.setAdapter(adapterr);
            }
            finish();
        }

}
