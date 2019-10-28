package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseClass {
    private SQLiteDatabase myDb;
    private Activity activity;
    int ACTIVITY_TITLE;
    ArrayList<String> studentsNamesArr;
    ArrayList<String> trialsNamesArr;
   // ArrayList<String> newStudentsNamesArr;
    //ArrayList<String> newStudentTrialsArr;
    //ArrayList с id проб студента
    ArrayList <Integer> trialsIdsArr;




    public DataBaseClass (Activity activity) {
        this.activity = activity;
        //массив студента
        studentsNamesArr = new ArrayList<>();
        trialsNamesArr = new ArrayList<>();
//        newStudentsNamesArr = new ArrayList<>();
//        newStudentTrialsArr = new ArrayList<>();
    }


    public void turnONdataBase() {
        myDb = activity.openOrCreateDatabase("my.db", MODE_PRIVATE, null);
    }

    public void turnOFFdataBase() {
        myDb.close();
    }

    public void createTablesForDb(){
        //clearBd(myDb);
        //3 таблицы-справочника и последняя - таблица результатов
        turnONdataBase();
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Students (id_student INT, name VARCHAR(40), trials TEXT)");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Trials (id_trial INT, name VARCHAR(20))");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Result_Codes(res_code INT, res_name)");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS practisingSet(id_answer INT, id_trial INT, id_student INT, date DATETIME, res_code INT)");
        turnOFFdataBase();
    }

    //получим имена учеников
    public ArrayList <String> extractStudentsNamesArray() {
        //тут заполним данными массив с именами студентов

        turnONdataBase();
        Cursor myCursor = myDb.rawQuery("select name from Students", null);
        while (myCursor.moveToNext()) {
            studentsNamesArr.add(myCursor.getString(0));
        }
        myCursor.close();
        turnOFFdataBase();
        return studentsNamesArr;
    }

    //получим имена проб
    public ArrayList <String> extractTrialsOfStudent(String studentName) {
        turnONdataBase();
        //сначала выберем студента из списка

        Cursor nameIdCursor = myDb.rawQuery("select id_student from Students where name =" + studentName, null);
        int studentId = nameIdCursor.getInt(0);
        nameIdCursor.close();

        Cursor myCursor = myDb.rawQuery("select id_trial INT from practisingSet where id_student = " + studentId, null);
        trialsIdsArr = new ArrayList<>();
        while (myCursor.moveToNext()){
            trialsIdsArr.add(myCursor.getInt(0));
        }
        myCursor.close();

        Cursor trialsNamesCursor;
        for (int i = 0; i < trialsIdsArr.size(); i++) {
            trialsNamesCursor = myDb.rawQuery("select name from Trials where id_trial =" + trialsIdsArr.get(i),null);
            trialsNamesArr.add(trialsNamesCursor.getString(0));
            Log.d("trialsNamesCursor", "мой массив TrialsNamesArr "+ trialsNamesArr.toString());
        }
        turnOFFdataBase();

        return trialsNamesArr;
    }


    public void saveStudentsToDb(ArrayList <String> newStudentsNamesArr) {
        ContentValues studentRow = new ContentValues();
        String studentName;
        turnONdataBase();
        for (int i = 0; i < newStudentsNamesArr.size(); i++) {
            studentName = newStudentsNamesArr.get(i);
            studentRow.put("id_student", i + 1);
            studentRow.put("name", studentName);
            myDb.insert("Students", null, studentRow);


        }
        turnOFFdataBase();
    }


}