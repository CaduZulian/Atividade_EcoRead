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
import com.ecoread.dao.ProprietarioDAO;
import com.ecoread.model.Apartamento;
import com.ecoread.model.Proprietario;
import com.example.atividade_ecoread.R;
import java.util.List;

public class CadastroApartamentoActivity extends AppCompatActivity {

    private EditText editNumero, editBloco;
    private Spinner spinnerProprietario;
    private ApartamentoDAO apartamentoDAO;
    private ProprietarioDAO proprietarioDAO;
    private List<Proprietario> proprietarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_apartamento);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        editNumero = findViewById(R.id.editNumero);
        editBloco = findViewById(R.id.editBloco);
        spinnerProprietario = findViewById(R.id.spinnerProprietario);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        apartamentoDAO = new ApartamentoDAO(this);
        proprietarioDAO = new ProprietarioDAO(this);

        carregarProprietarios();

        btnSalvar.setOnClickListener(v -> salvar());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarProprietarios() {
        proprietarioDAO.open();
        proprietarios = proprietarioDAO.getAll();
        proprietarioDAO.close();

        if (proprietarios.isEmpty()) {
            Toast.makeText(this, "Cadastre um proprietário primeiro!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayAdapter<Proprietario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, proprietarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProprietario.setAdapter(adapter);
    }

    private void salvar() {
        String numero = editNumero.getText().toString();
        String bloco = editBloco.getText().toString();
        Proprietario p = (Proprietario) spinnerProprietario.getSelectedItem();

        if (numero.isEmpty() || bloco.isEmpty() || p == null) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Apartamento a = new Apartamento(numero, bloco, p.getId());
        apartamentoDAO.open();
        long id = apartamentoDAO.insert(a);
        apartamentoDAO.close();

        if (id != -1) {
            Toast.makeText(this, "Apartamento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar apartamento", Toast.LENGTH_SHORT).show();
        }
    }
}
