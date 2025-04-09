package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PresetsActivity extends Activity {

    private TextView presetSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presets);

        // Vincular los 8 presets
        TextView[] presets = new TextView[] {
                findViewById(R.id.tv_preset_1), findViewById(R.id.tv_preset_2),
                findViewById(R.id.tv_preset_3), findViewById(R.id.tv_preset_4),
                findViewById(R.id.tv_preset_5), findViewById(R.id.tv_preset_6),
                findViewById(R.id.tv_preset_7), findViewById(R.id.tv_preset_8)
        };

        // Vincular botones
        Button btnIniciar = findViewById(R.id.btn_iniciar);
        Button btnVolverMain = findViewById(R.id.btn_volver_main);

        // Configurar clics para los presets
        for (TextView preset : presets) {
            preset.setOnClickListener(v -> {
                if (presetSeleccionado != null) {
                    presetSeleccionado.setBackgroundColor(0xFFE0E0E0);
                }
                presetSeleccionado = (TextView) v;
                presetSeleccionado.setBackgroundColor(0xFF81C784);
            });
        }

        // Botón para iniciar el entrenamiento
        btnIniciar.setOnClickListener(v -> {
            if (presetSeleccionado == null) {
                presetSeleccionado = presets[0];
                presetSeleccionado.setBackgroundColor(0xFF81C784);
            }

            String texto = presetSeleccionado.getText().toString();
            String[] partes = texto.split(" x ");

            int numeroAsaltos = Integer.parseInt(partes[0]);
            int duracionMinutos = Integer.parseInt(partes[1].replace("min", ""));
            int descansoSegundos = Integer.parseInt(partes[2].replace("seg", ""));

            long duracionAsaltoMs = duracionMinutos * 60 * 1000;
            long descansoMs = descansoSegundos * 1000;

            Intent intent = new Intent(PresetsActivity.this, TemporizadorActivity.class);
            intent.putExtra("numeroAsaltos", numeroAsaltos);
            intent.putExtra("duracionAsalto", duracionAsaltoMs);
            intent.putExtra("descanso", descansoMs);
            startActivity(intent);
        });

        // Botón para volver a MainActivity
        btnVolverMain.setOnClickListener(v -> {
            Intent intent = new Intent(PresetsActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opcional: cierra esta actividad
        });
    }
}