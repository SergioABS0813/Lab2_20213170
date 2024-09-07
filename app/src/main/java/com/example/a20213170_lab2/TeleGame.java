package com.example.a20213170_lab2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tele_game);

        botonesLetras = findViewById(R.id.botonesLetras);
        gridView = findViewById(R.id.letters);
        newGame = findViewById(R.id.newGame);

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
                deshabilitarBotones();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ganaste");
                builder.setMessage("Felicidades!\n\n La respuesta era: \n\n " + palabra);
                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TeleGame.this.jugarGame();
                    }
                });
                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
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
            deshabilitarBotones(); //Falta logica cuano actualParte = 6
            //Usuario ya perdió
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Perdiste");
            builder.setMessage("La respuesta era: \n\n " + palabra);
            builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TeleGame.this.jugarGame();
                }
            });
            builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
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
            recreate();
        }

    }
}