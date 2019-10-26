package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseClass {
    private SQLiteDatabase myDb;
    private Activity activity;
    int ACTIVITY_TITLE;
    ArrayList<String> studentsNamesArr;
    ArrayList<String> trialsNamesArr;
    ArrayList<String> newStudentsNamesArr;
    ArrayList<String> newStudentTrialsArr;
    //ArrayList с пробами студента для Json



    public DataBaseClass (Activity activity) {
        this.activity = activity;
        //массив студента
        studentsNamesArr = new ArrayList<>();
        trialsNamesArr = new ArrayList<>();
        newStudentsNamesArr = new ArrayList<>();
        newStudentTrialsArr = new ArrayList<>();



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
        turnOFFdataBase();
        return studentsNamesArr;
    }

    //получим имена проб
    public ArrayList <String> extractTrialsOfStudent(String studentName) {

        turnONdataBase();

        return trialsNamesArr;
    }


}