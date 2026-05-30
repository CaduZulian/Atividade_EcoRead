package com.ecoread.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ecoread.model.Apartamento;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ApartamentoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(Apartamento apartamento) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_APT_NUMERO, apartamento.getNumero());
        values.put(DatabaseHelper.COLUMN_APT_BLOCO, apartamento.getBloco());
        values.put(DatabaseHelper.COLUMN_APT_PROP_ID, apartamento.getProprietarioId());
        return db.insert(DatabaseHelper.TABLE_APARTAMENTO, null, values);
    }

    public List<Apartamento> getAll() {
        List<Apartamento> apartamentos = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_APARTAMENTO, null, null, null, null, null, DatabaseHelper.COLUMN_APT_NUMERO);

        if (cursor.moveToFirst()) {
            do {
                Apartamento a = new Apartamento();
                a.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APT_ID)));
                a.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APT_NUMERO)));
                a.setBloco(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APT_BLOCO)));
                a.setProprietarioId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APT_PROP_ID)));
                apartamentos.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return apartamentos;
    }
}
