package com.example.a20213170_lab2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            historialTiempos = bundle.getStringArrayList("historial");

            LinearLayout linearLayoutHistorial = findViewById(R.id.linearLayoutHistorial);



            if (historialTiempos != null) {
                for (String tiempo : historialTiempos) {
                    TextView textView = new TextView(this);
                    textView.setText(tiempo);
                    textView.setTextSize(30);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(16, 16, 16, 16);
                    linearLayoutHistorial.addView(textView);
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