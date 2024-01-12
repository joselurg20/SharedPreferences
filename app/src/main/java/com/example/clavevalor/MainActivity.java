package com.example.clavevalor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText met_dato;

    private EditText met_valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        met_dato= (EditText)  findViewById(R.id.cet_data);
        met_valor=(EditText) findViewById(R.id.cet_valor);


    }


    public void guardar(View view){
        String dato = met_dato.getText().toString();
        String valor = met_valor.getText().toString();

        SharedPreferences fichero = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor grabar = fichero.edit();


        grabar.putString(dato,valor);
        grabar.commit();
    }

    public void leer (View view){

        SharedPreferences fichero = getSharedPreferences("Almacen", Context.MODE_PRIVATE);

        String dato = met_dato.getText().toString();

        String valor = fichero.getString(dato, "");

        met_valor.setText(valor);
    }



}