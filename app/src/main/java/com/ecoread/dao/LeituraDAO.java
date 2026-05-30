package com.ecoread.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ecoread.model.Leitura;
import java.util.ArrayList;
import java.util.List;

public class LeituraDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public LeituraDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(Leitura leitura) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LEI_APT_ID, leitura.getApartamentoId());
        values.put(DatabaseHelper.COLUMN_LEI_DATA, leitura.getData());
        values.put(DatabaseHelper.COLUMN_LEI_LUZ, leitura.getValorLuz());
        values.put(DatabaseHelper.COLUMN_LEI_GAS, leitura.getValorGas());
        return db.insert(DatabaseHelper.TABLE_LEITURA, null, values);
    }

    public List<Leitura> getLeiturasByApartamento(long apartamentoId) {
        List<Leitura> leituras = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_LEI_APT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(apartamentoId) };
        
        Cursor cursor = db.query(DatabaseHelper.TABLE_LEITURA, null, selection, selectionArgs, null, null, DatabaseHelper.COLUMN_LEI_DATA + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Leitura l = new Leitura();
                l.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_ID)));
                l.setApartamentoId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_APT_ID)));
                l.setData(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_DATA)));
                l.setValorLuz(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_LUZ)));
                l.setValorGas(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_GAS)));
                leituras.add(l);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return leituras;
    }

    public Leitura getLeituraPorMesEApartamento(long apartamentoId, String mesAno) {
        String selection = DatabaseHelper.COLUMN_LEI_APT_ID + " = ? AND " + DatabaseHelper.COLUMN_LEI_DATA + " LIKE ?";
        String[] selectionArgs = { String.valueOf(apartamentoId), "%" + mesAno + "%" };
        
        Cursor cursor = db.query(DatabaseHelper.TABLE_LEITURA, null, selection, selectionArgs, null, null, null);
        
        Leitura l = null;
        if (cursor.moveToFirst()) {
            l = new Leitura();
            l.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_ID)));
            l.setApartamentoId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_APT_ID)));
            l.setData(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_DATA)));
            l.setValorLuz(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_LUZ)));
            l.setValorGas(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEI_GAS)));
        }
        cursor.close();
        return l;
    }
}
