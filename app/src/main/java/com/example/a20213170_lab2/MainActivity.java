package com.example.a20213170_lab2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView teleGameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        teleGameTextView = findViewById(R.id.teleGame); //R = recursos
        registerForContextMenu(teleGameTextView); //Registramos el textview como context menu

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








}