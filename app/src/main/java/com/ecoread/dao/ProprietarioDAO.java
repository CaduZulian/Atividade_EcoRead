package com.ecoread.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ecoread.model.Proprietario;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ProprietarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(Proprietario proprietario) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PROP_NOME, proprietario.getNome());
        values.put(DatabaseHelper.COLUMN_PROP_CPF, proprietario.getCpf());
        values.put(DatabaseHelper.COLUMN_PROP_CONTATO, proprietario.getContato());
        return db.insert(DatabaseHelper.TABLE_PROPRIETARIO, null, values);
    }

    public List<Proprietario> getAll() {
        List<Proprietario> proprietarios = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PROPRIETARIO, null, null, null, null, null, DatabaseHelper.COLUMN_PROP_NOME);

        if (cursor.moveToFirst()) {
            do {
                Proprietario p = new Proprietario();
                p.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROP_ID)));
                p.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROP_NOME)));
                p.setCpf(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROP_CPF)));
                p.setContato(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PROP_CONTATO)));
                proprietarios.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return proprietarios;
    }
}
