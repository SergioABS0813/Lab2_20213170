package com.example.a20213170_lab2;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TeleGame extends AppCompatActivity {

    private TextView[] caracterViews;
    private LinearLayout botonesLetras;
    private Adaptador adaptador;
    private GridView gridView;
    private int numCorr;
    private int numChar;
    private ImageView[] partesHumano;
    private int cantidadpartes = 6;
    private int actualParte;
    private Button newGame;
    private long tiempoInicio;
    private long tiempoFin;
    //Lista para guardar los tiempos
    private List<String> tiemposString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tele_game);
        botonesLetras = findViewById(R.id.botonesLetras);
        gridView = findViewById(R.id.letters);
        newGame = findViewById(R.id.newGame);
        tiempoInicio = System.currentTimeMillis();
        tiemposString = new ArrayList<>();
        jugarGame();
        partesHumano = new ImageView[cantidadpartes];
        partesHumano[0] = findViewById(R.id.head);
        partesHumano[1] = findViewById(R.id.body);
        partesHumano[2] = findViewById(R.id.brazoDere);
        partesHumano[3] = findViewById(R.id.brazoIzq);
        partesHumano[4] = findViewById(R.id.piernaIzq);
        partesHumano[5] = findViewById(R.id.piernaDere);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void jugarGame(){
        Intent intent = getIntent();
        String palabra = intent.getStringExtra("palabra");
        caracterViews = new TextView[palabra.length()];

        for(int i = 0; i < palabra.length(); i++){
            caracterViews[i] = new TextView(this);
            caracterViews[i].setText(""+palabra.charAt(i));
            caracterViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            caracterViews[i].setGravity(Gravity.CENTER);
            caracterViews[i].setTextColor(Color.WHITE);
            caracterViews[i].setTextSize(40);
            caracterViews[i].setBackgroundResource(R.drawable.letter_bg); //Para la linea debajo de la letra ya se da el estilo desde este xml
            botonesLetras.addView(caracterViews[i]);

        }
        adaptador = new Adaptador(this);
        gridView.setAdapter(adaptador);
        numCorr = 0;
        numChar = palabra.length();
        actualParte = 0;
    }

    public void letraPresionada(View view){
        Intent intent = getIntent();
        String palabra = intent.getStringExtra("palabra");
        String letra = ((TextView)view).getText().toString(); //El boton lo convertimos a letra
        char letraCaracter = letra.charAt(0); //De String se pasa a Character

        view.setEnabled(false);

        boolean correct = false;

        for(int i = 0; i< palabra.length(); i++){

            if(palabra.charAt(i) == letraCaracter){
                correct = true;
                numCorr++; //Contamos la cantidad de aciertos
                caracterViews[i].setTextColor(Color.BLACK);
            }

        }

        if(correct){
            if(numCorr == numChar){
                //Usuario ganó
                tiempoFin = System.currentTimeMillis(); //Acabo y gano
                long duracion = tiempoFin - tiempoInicio; //en milisegundos
                System.out.println(duracion/1000);
                tiemposString.add(String.valueOf(duracion/1000));
                deshabilitarBotones();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ganaste :D");
                builder.setMessage("Felicidades!\n\n La respuesta era: \n\n " + palabra +
                        "\n\n Terminó en " + duracion/1000 + " segundos.");
                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recreate();
                    }
                });
                builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TeleGame.this.finish();
                    }
                });
                builder.show();

            }
        } else if (actualParte < cantidadpartes-1) {
            //Usuario erra
            partesHumano[actualParte].setVisibility(View.VISIBLE);
            actualParte++;

        }else{
            partesHumano[actualParte].setVisibility(View.VISIBLE);
            tiempoFin = System.currentTimeMillis(); //Acabo y perdió
            long duracion = tiempoFin - tiempoInicio; //en milisegundos
            System.out.println(duracion/1000); //calculo en segundos
            tiemposString.add(String.valueOf(duracion/1000));
            deshabilitarBotones(); //Falta logica cuano actualParte = 6
            //Usuario ya perdió
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Perdiste :(");
            builder.setMessage("Sigue intentando!\n\n La respuesta era: \n\n " + palabra +
                    "\n\n Terminó en " + duracion/1000 + " segundos.");
            builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();
                }
            });
            builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TeleGame.this.finish();
                }
            });
            builder.show();

        }

    }

    public void deshabilitarBotones(){
        for(int i = 0; i < gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }


    public void onClickNewGame(View view) {
        if(view.getId() == R.id.newGame){ //Cuando le damos clic a "Nuevo Juego"
            tiemposString.add("Canceló"); //Se agrega el canceló
            recreate(); //Refresca la actividad si le damos clic a Nuevo Juego
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == R.id.estadisticas){
            Bundle historialbundle = new Bundle();
            historialbundle.putStringArrayList("historial", (ArrayList<String>) tiemposString);
            System.out.println(tiemposString.size());
            Intent intent = new Intent(this, StatsActivity.class);
            intent.putExtras(historialbundle);
            startActivity(intent);

        }
        return true;

    }

}