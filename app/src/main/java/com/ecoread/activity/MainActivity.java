package com.ecoread.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import com.example.atividade_ecoread.R;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Install the splash screen before calling super.onCreate()
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("EcoRead");
        }

        Button btnProprietario = findViewById(R.id.btnCadastrarProprietario);
        Button btnApartamento = findViewById(R.id.btnCadastrarApartamento);
        Button btnLeitura = findViewById(R.id.btnRegistrarLeitura);
        Button btnMedias = findViewById(R.id.btnConsultarMedias);
        Button btnLeiturasMes = findViewById(R.id.btnLeiturasMes);

        btnProprietario.setOnClickListener(v -> startActivity(new Intent(this, CadastroProprietarioActivity.class)));
        btnApartamento.setOnClickListener(v -> startActivity(new Intent(this, CadastroApartamentoActivity.class)));
        btnLeitura.setOnClickListener(v -> startActivity(new Intent(this, RegistroLeituraActivity.class)));
        btnMedias.setOnClickListener(v -> startActivity(new Intent(this, ConsultaMediaActivity.class)));
        btnLeiturasMes.setOnClickListener(v -> startActivity(new Intent(this, ConsultaMesActivity.class)));
    }
}
