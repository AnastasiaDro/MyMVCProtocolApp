package com.mymur.mymvcprotocolapp;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//класс таблицы проб
public class TrialsTable {
    private final static String TABLE_NAME = "Trials";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";


    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);");
    }

    public static void addTrial(String trialName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, trialName);
        database.insert(TABLE_NAME, null, values);
    }

    public static void addTrialIfNotExists(String trialName, SQLiteDatabase database){
       if (!getAllTrialsNames(database).containsValue(trialName));
        addTrial(trialName, database);
    }

    public static ArrayList <String> getNamesOfAllStudentTrial(ArrayList <Integer> studentTrialsIDArr, SQLiteDatabase database){
        ArrayList <String> namesOfAllStudentTrial = new ArrayList<>();
        HashMap <Integer, String> trialsHashMap = getAllTrialsNames(database);
        String trialName;
        for (int i = 0; i < studentTrialsIDArr.size(); i++) {
            trialName = trialsHashMap.get(studentTrialsIDArr.get(i));
            namesOfAllStudentTrial.add(trialName);
        }
        return namesOfAllStudentTrial;
    }


    public static HashMap<Integer, String> getAllTrialsNames(SQLiteDatabase database){
        HashMap<Integer, String> trialsHashMap = new HashMap<>();
        // Cursor myCursor = database.rawQuery("select name from Students", null);
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            trialsHashMap.put(myCursor.getInt(idIndex), myCursor.getString(nameIndex));
            System.out.println(" добавили студента");
        }
        return trialsHashMap;

    }

}