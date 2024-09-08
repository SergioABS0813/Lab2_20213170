package com.example.a20213170_lab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private List<String> historialTiempos;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        nombreUsuario = intent.getStringExtra("nombreUsuario");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            historialTiempos = bundle.getStringArrayList("historial");

            LinearLayout linearLayoutHistorial = findViewById(R.id.linearLayoutHistorial);
            LinearLayout nombreJugador = findViewById(R.id.linearLayoutHistorial);
            System.out.println(nombreUsuario);

            if(nombreUsuario != null){
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                textView.setText(nombreUsuario);
                textView.setPadding(16, 16, 16, 16);
                nombreJugador.addView(textView);

            }



            if (historialTiempos != null) {
                int i = 1; //para indicar el numero del juego
                for (String tiempo : historialTiempos) {
                    TextView textView = new TextView(this);
                    if(tiempo.equals("Canceló")){
                        textView.setText("Juego " + i + ": Canceló el juego");
                    }else{
                        textView.setText("Juego " + i + ": terminó en " + tiempo + " segundos");
                    }
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(16, 16, 16, 16);
                    linearLayoutHistorial.addView(textView);
                    i++;
                }
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}