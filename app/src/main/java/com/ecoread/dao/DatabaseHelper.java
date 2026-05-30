package com.ecoread.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ecoread.db";
    private static final int DATABASE_VERSION = 1;

    // Tabelas
    public static final String TABLE_PROPRIETARIO = "proprietario";
    public static final String TABLE_APARTAMENTO = "apartamento";
    public static final String TABLE_LEITURA = "leitura";

    // Colunas Proprietario
    public static final String COLUMN_PROP_ID = "id";
    public static final String COLUMN_PROP_NOME = "nome";
    public static final String COLUMN_PROP_CPF = "cpf";
    public static final String COLUMN_PROP_CONTATO = "contato";

    // Colunas Apartamento
    public static final String COLUMN_APT_ID = "id";
    public static final String COLUMN_APT_NUMERO = "numero";
    public static final String COLUMN_APT_BLOCO = "bloco";
    public static final String COLUMN_APT_PROP_ID = "proprietario_id";

    // Colunas Leitura
    public static final String COLUMN_LEI_ID = "id";
    public static final String COLUMN_LEI_APT_ID = "apartamento_id";
    public static final String COLUMN_LEI_DATA = "data";
    public static final String COLUMN_LEI_LUZ = "valor_luz";
    public static final String COLUMN_LEI_GAS = "valor_gas";

    // SQL de Criação
    private static final String CREATE_TABLE_PROPRIETARIO = "CREATE TABLE " + TABLE_PROPRIETARIO + " (" +
            COLUMN_PROP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PROP_NOME + " TEXT NOT NULL, " +
            COLUMN_PROP_CPF + " TEXT NOT NULL, " +
            COLUMN_PROP_CONTATO + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_APARTAMENTO = "CREATE TABLE " + TABLE_APARTAMENTO + " (" +
            COLUMN_APT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_APT_NUMERO + " TEXT NOT NULL, " +
            COLUMN_APT_BLOCO + " TEXT NOT NULL, " +
            COLUMN_APT_PROP_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_APT_PROP_ID + ") REFERENCES " + TABLE_PROPRIETARIO + "(" + COLUMN_PROP_ID + "));";

    private static final String CREATE_TABLE_LEITURA = "CREATE TABLE " + TABLE_LEITURA + " (" +
            COLUMN_LEI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LEI_APT_ID + " INTEGER NOT NULL, " +
            COLUMN_LEI_DATA + " TEXT NOT NULL, " +
            COLUMN_LEI_LUZ + " REAL NOT NULL, " +
            COLUMN_LEI_GAS + " REAL NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_LEI_APT_ID + ") REFERENCES " + TABLE_APARTAMENTO + "(" + COLUMN_APT_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROPRIETARIO);
        db.execSQL(CREATE_TABLE_APARTAMENTO);
        db.execSQL(CREATE_TABLE_LEITURA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEITURA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APARTAMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPRIETARIO);
        onCreate(db);
    }
}
