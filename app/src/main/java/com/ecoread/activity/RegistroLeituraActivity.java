package com.ecoread.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ecoread.dao.ApartamentoDAO;
import com.ecoread.dao.LeituraDAO;
import com.ecoread.model.Apartamento;
import com.ecoread.model.Leitura;
import com.ecoread.util.MascaraUtil;
import com.example.atividade_ecoread.R;
import java.util.List;

public class RegistroLeituraActivity extends AppCompatActivity {

    private Spinner spinnerApartamento;
    private EditText editData, editValorLuz, editValorGas;
    private LeituraDAO leituraDAO;
    private ApartamentoDAO apartamentoDAO;
    private List<Apartamento> apartamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_leitura);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerApartamento = findViewById(R.id.spinnerApartamento);
        editData = findViewById(R.id.editData);
        editValorLuz = findViewById(R.id.editValorLuz);
        editValorGas = findViewById(R.id.editValorGas);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // Aplica a máscara de MM/AAAA
        editData.addTextChangedListener(MascaraUtil.insert(MascaraUtil.MASCARA_DATA, editData));

        leituraDAO = new LeituraDAO(this);
        apartamentoDAO = new ApartamentoDAO(this);

        carregarApartamentos();

        btnRegistrar.setOnClickListener(v -> registrar());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarApartamentos() {
        apartamentoDAO.open();
        apartamentos = apartamentoDAO.getAll();
        apartamentoDAO.close();

        if (apartamentos.isEmpty()) {
            Toast.makeText(this, "Cadastre um apartamento primeiro!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayAdapter<Apartamento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, apartamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApartamento.setAdapter(adapter);
    }

    private void registrar() {
        Apartamento a = (Apartamento) spinnerApartamento.getSelectedItem();
        String dataStr = editData.getText().toString().trim();
        String luzStr = editValorLuz.getText().toString().trim();
        String gasStr = editValorGas.getText().toString().trim();

        if (dataStr.isEmpty() || luzStr.isEmpty() || gasStr.isEmpty() || a == null) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação básica da data MM/AAAA
        if (dataStr.length() != 7) {
            editData.setError("Formato inválido (MM/AAAA)");
            editData.requestFocus();
            return;
        }

        int mes = Integer.parseInt(dataStr.substring(0, 2));
        if (mes < 1 || mes > 12) {
            editData.setError("Mês inválido (01-12)");
            editData.requestFocus();
            return;
        }

        double luz = Double.parseDouble(luzStr);
        double gas = Double.parseDouble(gasStr);

        Leitura l = new Leitura(a.getId(), dataStr, luz, gas);
        leituraDAO.open();
        long id = leituraDAO.insert(l);
        leituraDAO.close();

        if (id != -1) {
            Toast.makeText(this, "Leitura registrada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao registrar leitura", Toast.LENGTH_SHORT).show();
        }
    }
}
