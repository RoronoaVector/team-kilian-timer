package com.example.myapplication;

import android.app.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TemporizadorActivity extends Activity {

    private TextView tvTemporizador;
    private TextView tvAsaltos;
    private TextView tvHoraSistema;
    private TextView tvResumenAsaltos;
    private Button btnVolverMain;
    private Button btnVolverConfiguracion;
    private Button btnVolverPresets;
    private CountDownTimer countDownTimer;
    private long tiempoRestanteEnMilisegundos;
    private int asaltosRestantes;
    private int totalAsaltos;
    private long duracionAsalto;
    private long duracionDescanso;
    private boolean esAsalto = true;

    private MediaPlayer boxingBellmediaPlayer;
    private MediaPlayer gongmediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);

        // Vincular vistas
        tvTemporizador = findViewById(R.id.tv_temporizador);
        tvAsaltos = findViewById(R.id.tv_asaltos);
        tvHoraSistema = findViewById(R.id.tv_hora_sistema);
        tvResumenAsaltos = findViewById(R.id.tv_resumen_asaltos);
        btnVolverMain = findViewById(R.id.btn_volver_main);
        btnVolverConfiguracion = findViewById(R.id.btn_volver_configuracion);
        btnVolverPresets = findViewById(R.id.btn_volver_presets);

        boxingBellmediaPlayer = MediaPlayer.create(this, R.raw.boxingbell);
        gongmediaPlayer = MediaPlayer.create(this, R.raw.gong);

        // Obtener datos del Intent
        Intent intent = getIntent();
        totalAsaltos = intent.getIntExtra("numeroAsaltos", 3);
        duracionAsalto = intent.getLongExtra("duracionAsalto", 60000);
        duracionDescanso = intent.getLongExtra("descanso", 30000);
        asaltosRestantes = totalAsaltos;
        tiempoRestanteEnMilisegundos = duracionAsalto;

        // Configurar botones de navegación
        btnVolverMain.setOnClickListener(v -> {
            Intent mainIntent = new Intent(TemporizadorActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish(); // Opcional: cierra esta actividad
        });

        btnVolverConfiguracion.setOnClickListener(v -> {
            Intent configIntent = new Intent(TemporizadorActivity.this, ConfiguracionActivity.class);
            startActivity(configIntent);
            finish(); // Opcional: cierra esta actividad
        });

        btnVolverPresets.setOnClickListener(v -> {
            Intent presetsIntent = new Intent(TemporizadorActivity.this, PresetsActivity.class);
            startActivity(presetsIntent);
            finish(); // Opcional: cierra esta actividad
        });

        // Inicializar temporizador
        actualizarResumenAsaltos();
        actualizarAsaltos();
        iniciarTemporizador();
        actualizarHoraSistema();
    }

    private void iniciarTemporizador() {
        if (esAsalto) {
            gongmediaPlayer.start();
        }
        countDownTimer = new CountDownTimer(tiempoRestanteEnMilisegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempoRestanteEnMilisegundos = millisUntilFinished;
                actualizarTemporizador();
                actualizarHoraSistema();
            }

            @Override
            public void onFinish() {
                tvTemporizador.setText("00:00");

                if (esAsalto) {
                    asaltosRestantes--;
                    actualizarAsaltos();

                    if (asaltosRestantes > 0) {
                        boxingBellmediaPlayer.start();
                        esAsalto = false;
                        tiempoRestanteEnMilisegundos = duracionDescanso;
                        tvAsaltos.setText("Descanso");
                        boxingBellmediaPlayer.setOnCompletionListener(mp -> {
                            iniciarTemporizador();
                        });
                    } else {
                        tvAsaltos.setText("¡Fin de los asaltos!");
                    }
                } else {
                    esAsalto = true;
                    tiempoRestanteEnMilisegundos = duracionAsalto;
                    actualizarAsaltos();
                    iniciarTemporizador();
                }
            }
        }.start();
    }

    private void actualizarTemporizador() {
        int minutos = (int) (tiempoRestanteEnMilisegundos / 1000) / 60;
        int segundos = (int) (tiempoRestanteEnMilisegundos / 1000) % 60;
        String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
        tvTemporizador.setText(tiempoFormateado);
    }

    private void actualizarAsaltos() {
        if (esAsalto) {
            tvAsaltos.setText("Asaltos restantes: " + asaltosRestantes);
        }
    }

    private void actualizarHoraSistema() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String horaActual = sdf.format(new Date());
        tvHoraSistema.setText(horaActual);
    }

    private void actualizarResumenAsaltos() {
        int minutosAsalto = (int) (duracionAsalto / 1000) / 60;
        int segundosDescanso = (int) (duracionDescanso / 1000);
        String resumen = String.format(Locale.getDefault(),"%d x %dm x %ds", totalAsaltos, minutosAsalto, segundosDescanso);
        tvResumenAsaltos.setText(resumen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (boxingBellmediaPlayer != null) {
            boxingBellmediaPlayer.release();
            boxingBellmediaPlayer = null;
        }

        if (gongmediaPlayer != null) {
            gongmediaPlayer.release();
            gongmediaPlayer = null;
        }
    }
}