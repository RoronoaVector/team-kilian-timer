package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            preset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Si ya hay un preset seleccionado, puedes resetear su estado visual (opcional)
                    if (presetSeleccionado != null) {
                        presetSeleccionado.setTextColor(0xFF999999); // Gris original
                    }
                    presetSeleccionado = (TextView) v;
                    // Opcional: Cambiar el color manualmente al seleccionarlo
                    presetSeleccionado.setTextColor(0xFF81C784); // Verde claro para indicar selección
                }
            });
        }

        // Botón para iniciar el entrenamiento
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presetSeleccionado == null) {
                    presetSeleccionado = presets[0];
                    presetSeleccionado.setTextColor(0xFF81C784); // Verde claro
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
            }
        });

        // Botón para volver a MainActivity
        btnVolverMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresetsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Opcional: cierra esta actividad
            }
        });

        // Opcional: Dar foco inicial al primer preset para evitar el doble clic inicial
        presets[0].requestFocus();
    }

}