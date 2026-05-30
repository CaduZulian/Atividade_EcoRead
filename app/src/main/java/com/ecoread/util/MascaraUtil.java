package com.ecoread.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MascaraUtil {

    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_DATA = "##/####";
    public static final String MASCARA_FONE = "(##) #####-####";

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                StringBuilder mascara = new StringBuilder();
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#') {
                        if (str.length() > i) {
                            mascara.append(m);
                        }
                        continue;
                    }
                    try {
                        mascara.append(str.charAt(i));
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                String result = mascara.toString();
                ediTxt.setText(result);

                // Lógica de cursor: 
                // Se estiver apagando, mantém o cursor onde estava (start).
                // Se estiver inserindo, avança o cursor.
                int pos;
                if (count > before) { // Inserindo
                    pos = start + count;
                    // Avança o cursor se ele parou exatamente antes de um separador recém-adicionado
                    while (pos < result.length() && mask.charAt(pos - 1) != '#') {
                        pos++;
                    }
                } else { // Apagando
                    pos = start;
                }

                if (pos > result.length()) pos = result.length();
                if (pos < 0) pos = 0;
                
                try {
                    ediTxt.setSelection(pos);
                } catch (Exception ignored) {}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    public static boolean isCPFValido(String cpf) {
        cpf = unmask(cpf);
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int primDig = 11 - (soma % 11);
            if (primDig > 9) primDig = 0;
            if (primDig != (cpf.charAt(9) - '0')) return false;

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int segDig = 11 - (soma % 11);
            if (segDig > 9) segDig = 0;
            return segDig == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}
