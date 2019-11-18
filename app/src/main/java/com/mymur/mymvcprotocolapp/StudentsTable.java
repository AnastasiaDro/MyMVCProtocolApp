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

    //новые колонки
    //колонка видимости ученика, 0 - false, 1 - true
    private final static String COLUMN_VISIBILITY = "visibility";


    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_VISIBILITY +" INTEGER);");
    }

    public static void addStudent(String studentName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, studentName);
        //новый код, нового студента делаем видимым в списке
        values.put(COLUMN_VISIBILITY, 1);


        database.insert(TABLE_NAME, null, values);
    }

    public static HashMap<String, Integer> getAllStudentsNames(SQLiteDatabase database){
        HashMap<String, Integer> studentsHashMap = new HashMap<>();

        //Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);

        //Теперь берем студентов только если они видимы
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME+ " WHERE " + COLUMN_VISIBILITY +" LIKE '" + 1 + "'",  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            studentsHashMap.put(myCursor.getString(nameIndex), myCursor.getInt(idIndex));
            System.out.println(" добавили студента");
        }
        myCursor.close();
        return studentsHashMap;
    }

    //Метод делания студента невидимым
    public static void makeStudentInvisible(String studentName, SQLiteDatabase database) {

    }

}
