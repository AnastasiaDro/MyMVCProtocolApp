package com.mymur.mymvcprotocolapp;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;

//класс таблицы студентов
public class StudentsTable {
    private final static String TABLE_NAME = "Students";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";



    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT);");
    }

    public static void addStudent(String studentName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, studentName);
        database.insert(TABLE_NAME, null, values);
    }

    public static HashMap<String, Integer> getAllStudentsNames(SQLiteDatabase database){
        HashMap<String, Integer> studentsHashMap = new HashMap<>();
       // Cursor myCursor = database.rawQuery("select name from Students", null);
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            studentsHashMap.put(myCursor.getString(nameIndex), myCursor.getInt(idIndex));
            System.out.println(" добавили студента");
        }
        return studentsHashMap;

    }


    //TODO
    //вот тут проблема no such column kolya
    public static int getStudentId(SQLiteDatabase database, String studentName) {
        int studentId = -1;
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + "  FROM "  +TABLE_NAME + " WHERE " + COLUMN_NAME +" LIKE '" + studentName + "'",  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        myCursor.moveToFirst();
        while (myCursor.moveToNext()) {
            studentId = myCursor.getInt(idIndex);
        }


        System.out.println("Это id индекс "+ idIndex);

        return studentId;
    }

}
