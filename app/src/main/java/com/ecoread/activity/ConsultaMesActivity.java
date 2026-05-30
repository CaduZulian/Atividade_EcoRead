package com.ecoread.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ecoread.adapter.LeituraAdapter;
import com.ecoread.dao.ApartamentoDAO;
import com.ecoread.dao.LeituraDAO;
import com.ecoread.model.Apartamento;
import com.ecoread.model.Leitura;
import com.example.atividade_ecoread.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConsultaMesActivity extends AppCompatActivity {

    private EditText editMes, editAno;
    private ListView listViewLeituras;
    private ApartamentoDAO apartamentoDAO;
    private LeituraDAO leituraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_mes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        editMes = findViewById(R.id.editMes);
        editAno = findViewById(R.id.editAno);
        listViewLeituras = findViewById(R.id.listViewLeituras);
        Button btnConsultar = findViewById(R.id.btnConsultar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        apartamentoDAO = new ApartamentoDAO(this);
        leituraDAO = new LeituraDAO(this);

        btnConsultar.setOnClickListener(v -> consultar());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void consultar() {
        String mesStr = editMes.getText().toString().trim();
        String anoStr = editAno.getText().toString().trim();

        if (mesStr.isEmpty() || anoStr.isEmpty()) {
            Toast.makeText(this, "Informe o mês e o ano", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int mes = Integer.parseInt(mesStr);
            int ano = Integer.parseInt(anoStr);
            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

            if (mes < 1 || mes > 12) {
                editMes.setError("Mês inválido (01-12)");
                editMes.requestFocus();
                return;
            }

            if (ano < 2000 || ano > anoAtual + 1) {
                editAno.setError("Ano inválido");
                editAno.requestFocus();
                return;
            }

            // Formato esperado: MM/AAAA
            String mesFormatado = String.format("%02d", mes);
            String mesAno = mesFormatado + "/" + anoStr;

            apartamentoDAO.open();
            List<Apartamento> apartamentos = apartamentoDAO.getAll();
            apartamentoDAO.close();

            if (apartamentos.isEmpty()) {
                Toast.makeText(this, "Nenhum apartamento cadastrado", Toast.LENGTH_SHORT).show();
                return;
            }

            leituraDAO.open();
            List<Leitura> leituras = new ArrayList<>();
            for (Apartamento a : apartamentos) {
                Leitura l = leituraDAO.getLeituraPorMesEApartamento(a.getId(), mesAno);
                leituras.add(l);
            }
            leituraDAO.close();

            LeituraAdapter adapter = new LeituraAdapter(this, apartamentos, leituras);
            listViewLeituras.setAdapter(adapter);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}
