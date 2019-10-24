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
    ArrayList<String> studentTrialsNamesArr;
    ArrayList<String> newStudentsNamesArr;
    ArrayList<String> newStudentTrialsArr;
    //ArrayList с пробами студента для Json



    public DataBaseClass (Activity activity) {
        this.activity = activity;
        //массив студента
        studentsNamesArr = new ArrayList<>();
        studentTrialsNamesArr = new ArrayList<>();
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
        //будем сохранять массивы проб и результатов в формате Json...ух!!!
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Students (id_student INT, name VARCHAR(40), trials TEXT)");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Trials (id_trial INT, name VARCHAR(20))");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS practisingSet(id_practising INT, date DATETIME, id_student INT, trialResults TEXT)");
    }

    public HashMap extractStudentsWithTrials() {
        HashMap myStudents = new HashMap();
        //тут заполняем данными хэщмап

        return myStudents;
    }

    //получим имена учеников
    public ArrayList <String> extractStudentsNames() {
        //тут заполним данными массив с именами студентов

        turnONdataBase();
        Cursor myCursor = myDb.rawQuery("select name from Students", null);
        while (myCursor.moveToNext()) {
            studentsNamesArr.add(myCursor.getString(0));
        }
        turnOFFdataBase();
        return studentsNamesArr;
    }

    //получим имена проб заданного ученика
    public ArrayList <String> extractTrialsOfStudent(String studentName) {

        turnONdataBase();
        //ищем студента в базе по имени
        //выгружаем его json-строку  с его пробами в массив  studentTrialsNamesArr
        turnOFFdataBase();
        return studentTrialsNamesArr;
    }


}