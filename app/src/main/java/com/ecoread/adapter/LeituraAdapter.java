package com.ecoread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ecoread.model.Apartamento;
import com.ecoread.model.Leitura;
import com.example.atividade_ecoread.R;
import java.util.List;

public class LeituraAdapter extends BaseAdapter {

    private Context context;
    private List<Apartamento> apartamentos;
    private List<Leitura> leituras; // Pode conter nulls para aptos sem leitura

    public LeituraAdapter(Context context, List<Apartamento> apartamentos, List<Leitura> leituras) {
        this.context = context;
        this.apartamentos = apartamentos;
        this.leituras = leituras;
    }

    @Override
    public int getCount() {
        return apartamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return apartamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_leitura, parent, false);
        }

        Apartamento apto = apartamentos.get(position);
        Leitura leitura = leituras.get(position);

        TextView txtAptoInfo = convertView.findViewById(R.id.txtAptoInfo);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        TextView txtLuz = convertView.findViewById(R.id.txtLuz);
        TextView txtGas = convertView.findViewById(R.id.txtGas);
        TextView txtSemLeitura = convertView.findViewById(R.id.txtSemLeitura);
        View layoutValores = convertView.findViewById(R.id.layoutValores);

        txtAptoInfo.setText("Apto " + apto.getNumero() + " - Bloco " + apto.getBloco());

        if (leitura != null) {
            txtStatus.setText("COLETADO");
            txtStatus.setBackgroundResource(R.drawable.bg_status_coletado);
            txtLuz.setText(leitura.getValorLuz() + " kWh");
            txtGas.setText(leitura.getValorGas() + " m³");
            layoutValores.setVisibility(View.VISIBLE);
            txtSemLeitura.setVisibility(View.GONE);
        } else {
            txtStatus.setText("PENDENTE");
            txtStatus.setBackgroundResource(R.drawable.bg_status_pendente);
            layoutValores.setVisibility(View.GONE);
            txtSemLeitura.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
