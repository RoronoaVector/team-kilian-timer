package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextView tvHoraActual;
    private ImageButton btnConfiguracion;
    private ImageButton btnPresets;
    private Handler handler;
    private Runnable actualizarHoraRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHoraActual = findViewById(R.id.tv_hora_actual);
        btnConfiguracion = findViewById(R.id.btn_configuracion);
        btnPresets = findViewById(R.id.btn_presets);

        // Configurar OnClickListener para btnConfiguracion
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para btnPresets
        btnPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PresetsActivity.class);
                startActivity(intent);
            }
        });

        // Configurar actualización de la hora
        handler = new Handler(Looper.getMainLooper());
        actualizarHoraRunnable = new Runnable() {
            @Override
            public void run() {
                actualizarHora();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(actualizarHoraRunnable);
    }

    private void actualizarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String horaActual = sdf.format(new Date());
        tvHoraActual.setText(horaActual);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && actualizarHoraRunnable != null) {
            handler.removeCallbacks(actualizarHoraRunnable);
        }
    }
}