package com.ecoread.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ecoread.dao.ProprietarioDAO;
import com.ecoread.model.Proprietario;
import com.ecoread.util.MascaraUtil;
import com.example.atividade_ecoread.R;

public class CadastroProprietarioActivity extends AppCompatActivity {

    private EditText editNome, editCPF, editContato;
    private ProprietarioDAO proprietarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_proprietario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        editNome = findViewById(R.id.editNome);
        editCPF = findViewById(R.id.editCPF);
        editContato = findViewById(R.id.editContato);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // Aplica as máscaras
        editCPF.addTextChangedListener(MascaraUtil.insert(MascaraUtil.MASCARA_CPF, editCPF));
        editContato.addTextChangedListener(MascaraUtil.insert(MascaraUtil.MASCARA_FONE, editContato));

        proprietarioDAO = new ProprietarioDAO(this);

        btnSalvar.setOnClickListener(v -> salvar());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void salvar() {
        String nome = editNome.getText().toString().trim();
        String cpfFormatado = editCPF.getText().toString().trim();
        String contatoFormatado = editContato.getText().toString().trim();

        if (nome.isEmpty() || cpfFormatado.isEmpty() || contatoFormatado.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MascaraUtil.isCPFValido(cpfFormatado)) {
            editCPF.setError("CPF inválido");
            editCPF.requestFocus();
            return;
        }

        // Remove máscara para salvar dados limpos no banco
        String cpfLimpo = MascaraUtil.unmask(cpfFormatado);
        String contatoLimpo = MascaraUtil.unmask(contatoFormatado);

        Proprietario p = new Proprietario(nome, cpfLimpo, contatoLimpo);
        proprietarioDAO.open();
        long id = proprietarioDAO.insert(p);
        proprietarioDAO.close();

        if (id != -1) {
            Toast.makeText(this, "Proprietário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar proprietário", Toast.LENGTH_SHORT).show();
        }
    }
}
