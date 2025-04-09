package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ConfiguracionActivity extends Activity {

    private TextView tvNumeroAsaltos;
    private TextView tvDuracionAsalto;
    private TextView tvDescanso;
    private Button btnMenosAsaltos, btnMasAsaltos;
    private Button btnMenosDuracion, btnMasDuracion;
    private Button btnMenosDescanso, btnMasDescanso;
    private Button btnConfirmar;
    private Button btnVolverMain;

    private int numeroAsaltos = 3;
    private int duracionAsaltoSegundos = 60;
    private int descansoSegundos = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Vincular vistas
        tvNumeroAsaltos = findViewById(R.id.tv_numero_asaltos);
        tvDuracionAsalto = findViewById(R.id.tv_duracion_asalto);
        tvDescanso = findViewById(R.id.tv_descanso);
        btnMenosAsaltos = findViewById(R.id.btn_menos_asaltos);
        btnMasAsaltos = findViewById(R.id.btn_mas_asaltos);
        btnMenosDuracion = findViewById(R.id.btn_menos_duracion);
        btnMasDuracion = findViewById(R.id.btn_mas_duracion);
        btnMenosDescanso = findViewById(R.id.btn_menos_descanso);
        btnMasDescanso = findViewById(R.id.btn_mas_descanso);
        btnConfirmar = findViewById(R.id.btn_confirmar);
        btnVolverMain = findViewById(R.id.btn_volver_main);

        actualizarValores();

        // Configurar botones de ajuste
        btnMenosAsaltos.setOnClickListener(v -> {
            if (numeroAsaltos > 1) {
                numeroAsaltos--;
                actualizarValores();
            }
        });
        btnMasAsaltos.setOnClickListener(v -> {
            numeroAsaltos++;
            actualizarValores();
        });

        btnMenosDuracion.setOnClickListener(v -> {
            if (duracionAsaltoSegundos > 0) {
                duracionAsaltoSegundos -= 10;
                actualizarValores();
            }
        });
        btnMasDuracion.setOnClickListener(v -> {
            duracionAsaltoSegundos += 10;
            actualizarValores();
        });

        btnMenosDescanso.setOnClickListener(v -> {
            if (descansoSegundos > 0) {
                descansoSegundos -= 10;
                actualizarValores();
            }
        });
        btnMasDescanso.setOnClickListener(v -> {
            descansoSegundos += 10;
            actualizarValores();
        });

        // Botón para iniciar el entrenamiento
        btnConfirmar.setOnClickListener(v -> {
            long duracionAsaltoMs = duracionAsaltoSegundos * 1000;
            long descansoMs = descansoSegundos * 1000;

            Intent intent = new Intent(ConfiguracionActivity.this, TemporizadorActivity.class);
            intent.putExtra("numeroAsaltos", numeroAsaltos);
            intent.putExtra("duracionAsalto", duracionAsaltoMs);
            intent.putExtra("descanso", descansoMs);
            startActivity(intent);
        });

        // Botón para volver a MainActivity
        btnVolverMain.setOnClickListener(v -> {
            Intent intent = new Intent(ConfiguracionActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opcional: cierra esta actividad
        });
    }

    private void actualizarValores() {
        tvNumeroAsaltos.setText(String.valueOf(numeroAsaltos));
        tvDuracionAsalto.setText(getFormattedString(duracionAsaltoSegundos));
        tvDescanso.setText(getFormattedString(descansoSegundos));
    }

    private String getFormattedString(int time) {
        String formattedString;
        formattedString = String.valueOf(time);
        if (time > 59) {
            int minutos = time / 60;
            int segundos = time % 60;
            formattedString = String.format(Locale.getDefault(),"%02d:%02d", minutos, segundos);
        }
        return formattedString;
    }
}