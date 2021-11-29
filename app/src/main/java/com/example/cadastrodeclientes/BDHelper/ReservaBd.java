package com.example.cadastrodeclientes.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cadastrodeclientes.model.Reserva;

import java.util.ArrayList;

public class ReservaBd extends SQLiteOpenHelper {

    private static final String DATABASE = "bdReserva";
    private static final int VERSION = 1;

    public ReservaBd (Context context){
        super(context, DATABASE, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String reserva = "CREATE TABLE reserva(id, INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomecidade TEXT NOT NULL, nomehotel TEXT NOT NULL, data INTEGER);";
        db.execSQL(reserva);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String reserva = "DROP TABLE IF EXISTS reservas " ;
        db.execSQL(reserva);
    }

    public void salvarReserva(Reserva reserva){
        ContentValues values = new ContentValues();

        values.put("nomecidade", reserva.getNomeCidade());
        values.put("nomehotel", reserva.getNomeHotel());
        values.put("data", reserva.getData());

        getWritableDatabase().insert("reserva", null, values);

    }
    // método alterar
    public void alterarReserva(Reserva reserva){
        ContentValues values = new ContentValues();

        values.put("nomecidade", reserva.getNomeCidade());
        values.put("nomehotel", reserva.getNomeHotel());
        values.put("data", reserva.getData());


        String [] args = {reserva.getId().toString()};
        getWritableDatabase().update("reserva", values, "id=?", args);
    }

    public void deletarReserva(Reserva reserva){
        String [] args = {reserva.getId().toString()};
        getWritableDatabase().delete("reserva","id=?",args);
    }
    // lista - mostrar

    public ArrayList<Reserva> getLista(){
        String []columns = {"id", "nomecidade","nomehotel","data"};
        Cursor cursor = getWritableDatabase().query("reserva", columns, null,null,null,null,null,null);
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        while (cursor.moveToNext()){
            Reserva reserva = new Reserva();
            reserva.setId(cursor.getLong(0));
            reserva.setNomeCidade(cursor.getString(1));
            reserva.setNomeHotel(cursor.getString(2));
            reserva.setData(cursor.getInt(3));

            reservas.add(reserva);
        }

        return reservas;
    }
}
