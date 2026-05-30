package com.ecoread.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ecoread.dao.ApartamentoDAO;
import com.ecoread.dao.LeituraDAO;
import com.ecoread.model.Apartamento;
import com.ecoread.model.Leitura;
import com.example.atividade_ecoread.R;
import java.util.List;
import java.util.Locale;

public class ConsultaMediaActivity extends AppCompatActivity {

    private Spinner spinnerApartamento;
    private RadioButton radio3Meses;
    private TextView txtResultado;
    private ApartamentoDAO apartamentoDAO;
    private LeituraDAO leituraDAO;
    private List<Apartamento> apartamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_media);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerApartamento = findViewById(R.id.spinnerApartamento);
        radio3Meses = findViewById(R.id.radio3Meses);
        txtResultado = findViewById(R.id.txtResultado);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        apartamentoDAO = new ApartamentoDAO(this);
        leituraDAO = new LeituraDAO(this);

        carregarApartamentos();

        btnCalcular.setOnClickListener(v -> calcular());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarApartamentos() {
        apartamentoDAO.open();
        apartamentos = apartamentoDAO.getAll();
        apartamentoDAO.close();

        if (apartamentos.isEmpty()) {
            Toast.makeText(this, "Nenhum apartamento cadastrado!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayAdapter<Apartamento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, apartamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApartamento.setAdapter(adapter);
    }

    private void calcular() {
        Apartamento a = (Apartamento) spinnerApartamento.getSelectedItem();
        if (a == null) return;

        int periodo = radio3Meses.isChecked() ? 3 : 6;

        leituraDAO.open();
        List<Leitura> leituras = leituraDAO.getLeiturasByApartamento(a.getId());
        leituraDAO.close();

        if (leituras.size() < periodo) {
            txtResultado.setText("Não há leituras suficientes para calcular a média de " + periodo + " meses.");
            return;
        }

        double somaLuz = 0;
        double somaGas = 0;
        for (int i = 0; i < periodo; i++) {
            somaLuz += leituras.get(i).getValorLuz();
            somaGas += leituras.get(i).getValorGas();
        }

        Leitura ultima = leituras.get(0);
        double mediaLuz = somaLuz / periodo;
        double mediaGas = somaGas / periodo;

        String resultado = String.format(Locale.getDefault(), 
                "Último consumo:\nLuz: %.2f kWh\nGás: %.2f m³\n\nMédias (%d meses):\nLuz: %.2f kWh\nGás: %.2f m³",
                ultima.getValorLuz(), ultima.getValorGas(), periodo, mediaLuz, mediaGas);

        txtResultado.setText(resultado);
    }
}
