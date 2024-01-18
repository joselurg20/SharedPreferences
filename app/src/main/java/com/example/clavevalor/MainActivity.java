package com.example.clavevalor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText cetDato;
    private EditText cetValor;
    private EditText cetName;

    private Button btnGuardar;
    private Button btnLeer;
    private Button btnBorrar;
    private Button btnModificar;
    private Button btnMostrarTodo;
    private TextView etResultado;
    private ListView listView;

    private static final String SHARED_PREF_KEY = "Datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cetDato = findViewById(R.id.cet_data);
        cetValor = findViewById(R.id.cet_valor);
        cetName = findViewById(R.id.cet_name);
        btnGuardar = findViewById(R.id.save);
        btnLeer = findViewById(R.id.show);
        btnBorrar = findViewById(R.id.delete);
        btnModificar = findViewById(R.id.modify);
        btnMostrarTodo = findViewById(R.id.showAll);
        etResultado = findViewById(R.id.etResultado);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leer();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

        btnMostrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTodo();
            }
        });

        Button btnLimpiar = findViewById(R.id.clear);

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });
    }

    private void guardar() {
        String dato = cetDato.getText().toString();
        String valor = cetValor.getText().toString();
        String name = cetName.getText().toString();

        SharedPreferences fichero = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fichero.edit();

        String clave = dato + ":" + name;

        if (fichero.contains(clave)) {
            Toast.makeText(this, "El trío ya existe. ¿Desea actualizarlo?", Toast.LENGTH_SHORT).show();
        } else {
            editor.putString(clave, valor);
            editor.apply();
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        }
    }

    private void leer() {
        String dato = cetDato.getText().toString();
        String name = cetName.getText().toString();
        String valor = cetValor.getText().toString();

        SharedPreferences fichero = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);

        String clave = dato + ":" + name;

        if (fichero.contains(clave)) {
            String valorGuardado = fichero.getString(clave, "");
            cetValor.setText(valorGuardado);
        } else {
            Toast.makeText(this, "El trío no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void borrar() {
        String dato = cetDato.getText().toString();
        String name = cetName.getText().toString();
        String valor = cetValor.getText().toString();
        SharedPreferences fichero = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fichero.edit();

        String clave = dato + ":" + name;

        if (fichero.contains(clave)) {
            editor.remove(clave);
            editor.apply();
            Toast.makeText(this, "Dato eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El trío no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificar() {
        String dato = cetDato.getText().toString();
        String valorNuevo = cetValor.getText().toString();
        String nameNuevo = cetName.getText().toString();

        SharedPreferences fichero = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fichero.edit();

        String claveAntigua = dato ;
        String claveNueva = valorNuevo + ":" + nameNuevo;

        if (fichero.contains(claveAntigua)) {
            // Obtener el valor antiguo para comparar si ha cambiado
            String valorAntiguo = fichero.getString(claveAntigua, "");

            if (!claveAntigua.equals(claveNueva)) {
                // Eliminar la clave antigua y agregar la nueva
                editor.remove(claveAntigua);
                editor.putString(claveNueva, valorAntiguo);
                editor.apply();
                Toast.makeText(this, "Dato(s) actualizado(s)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Los nuevos valores son iguales a los anteriores", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El trío no existe", Toast.LENGTH_SHORT).show();
        }
    }



    private void mostrarTodo() {
        Log.d("MostrarTodo", "mostrarTodo() function called");
        SharedPreferences fichero = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        Map<String, ?> map = fichero.getAll();

        StringBuilder dataText = new StringBuilder();

        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            dataText.append(key).append(": ").append(value).append("\n");
        }

        etResultado.setText(dataText.toString());
        Log.d("MostrarTodo", "Mostrando resultados: " + dataText.toString());
    }

    private void limpiarCampos() {
        cetDato.setText("");
        cetValor.setText("");
        cetName.setText("");
    }
}