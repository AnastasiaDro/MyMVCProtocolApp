package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    //константы
    private static final String DATABASE_NAME = "protocols.bd";
    private static final int DATABASE_VERSION = 1;
    String [] resNames;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        resNames = new String[]{"bad", "with_hint", "good"};
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //создадим 3 таблицы-справочника
        StudentsTable.createTable(db);
        TrialsTable.createTable(db);
        ResCodeTable.createTable(db);
        //создадим таблицу для результатов
        PracticingResultsTable.createTable(db);
        //заполним таблицу резалтКодов сразу же
        ResCodeTable.addAllResNames(resNames, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
