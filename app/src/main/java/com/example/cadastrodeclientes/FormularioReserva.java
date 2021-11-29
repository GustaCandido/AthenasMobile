package com.example.cadastrodeclientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cadastrodeclientes.BDHelper.ReservaBd;
import com.example.cadastrodeclientes.model.Reserva;

public class FormularioReserva extends AppCompatActivity {


    //Declarando as caixas de texto e variaveis
    EditText edt_Cidade, edt_Hotel, edt_Data;
    Button btn_Reservar;
    Reserva editarReserva, reserva;
    ReservaBd reservaBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_reserva);



        //Iniciando o formulário
        reserva = new Reserva();

        reservaBd = new ReservaBd(FormularioReserva.this);
        Intent intent = getIntent();
        editarReserva = (Reserva) intent.getSerializableExtra("reserva-escolhida");


        edt_Cidade = (EditText) findViewById(R.id.edt_Cidade);
        edt_Hotel = (EditText) findViewById(R.id.edt_Hotel);
        edt_Data = (EditText) findViewById(R.id.edt_Data);

        btn_Reservar = (Button) findViewById(R.id.btn_Reservar);

        if (editarReserva != null){
            btn_Reservar.setText("Alterar");

            edt_Cidade.setText(editarReserva.getNomeCidade());
            edt_Hotel.setText(editarReserva.getNomeHotel());
            edt_Data.setText(editarReserva.getData()+"");

            reserva.setId(editarReserva.getId());
        }
        else {
            btn_Reservar.setText("Reservar");
        }

        //Botão pra salvar a reserva
        btn_Reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserva.setNomeCidade(edt_Cidade.getText().toString());
                reserva.setNomeHotel(edt_Hotel.getText().toString());
                reserva.setData(Integer.parseInt(edt_Data.getText().toString()));

                if (btn_Reservar.getText().toString().equals("Reservar")){
                    reservaBd.salvarReserva(reserva);
                    reservaBd.close();

                }else{
                    reservaBd.alterarReserva(reserva);
                    reservaBd.close();
                }

            }
        });
    }
}