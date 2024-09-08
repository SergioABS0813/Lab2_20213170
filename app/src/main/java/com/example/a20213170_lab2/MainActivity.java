package com.example.a20213170_lab2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView teleGameTextView;
    private EditText nameInput;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        teleGameTextView = findViewById(R.id.teleGame); //R = recursos
        registerForContextMenu(teleGameTextView); //Registramos el textview como context menu
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setEnabled(false); //Para que el botón esté deshabilitado por default

        nameInput = findViewById(R.id.nameInput);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    button.setEnabled(false);
                }
                else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //No se coloca nada cuando este seteando solo nos importa el antes del cambio del texto y el después
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")){
                    button.setEnabled(false);
                }else {
                    button.setEnabled(true);
                }

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecciona un color:");
        menu.add(0,v.getId(),0,"Verde");
        menu.add(0,v.getId(),0,"Rojo");
        menu.add(0,v.getId(),0,"Morado");
    }
    
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        if(item.getTitle().equals("Rojo")){
            teleGameTextView.setTextColor(Color.parseColor("#FF0000"));
            Toast.makeText(this, "Escogiste Rojo", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle().equals("Morado")) {
            teleGameTextView.setTextColor(Color.parseColor("#800080"));
            Toast.makeText(this, "Escogiste Morado", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle().equals("Verde")) {
            teleGameTextView.setTextColor(Color.parseColor("#008000"));
            Toast.makeText(this, "Escogiste Verde", Toast.LENGTH_SHORT).show();
        }
        return true;
        //Se empleo herramienta de chatgpt para la funcionalidad del indicador TOAST.makeText
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button){ //Cuando le damos clic a "JUGAR"

            String[] listaPalabras = {"PROPA", "TELECO", "FIBRA", "REDES", "ANTENA", "CLOUD"};
            String palabraEscogida = palabraAleatoria(listaPalabras);
            String nombreUsuario = nameInput.getText().toString();
            Intent intent = new Intent(this, TeleGame.class); //Enviamos al usuario a la vista de TeleGame
            intent.putExtra("palabra", palabraEscogida);
            intent.putExtra("nombreUsuario",nombreUsuario);
            startActivity(intent);
        }

    }


    public String palabraAleatoria(String[] listaPalabras) {
        Random random = new Random();
        int numeroRandom = random.nextInt(listaPalabras.length); // Generamos un numero aleatorio de [0;longitud-1]
        return listaPalabras[numeroRandom];  // Devuelve la palabra en esa posición (número Random)
    }


}