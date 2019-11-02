package com.mymur.mymvcprotocolapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseHelper extends SQLiteOpenHelper {
    //константы
    private static final String DATABASE_NAME = "protocols.bd";
    private static final int DATABASE_VERSION = 1;
    String [] resNames;
    ArrayList<String> studentsNamesArr;
    ArrayList<String> studentTrialsNamesArr;
    ArrayList<String> newStudentsNamesArr;
    ArrayList<String> newStudentTrialsArr;
    //ArrayList с id проб студента
    ArrayList<Integer> trialsIdsArr;
    HashMap <Integer, String> studentsMap;
    ArrayList <Integer> studentTrialsIDArr;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        resNames = new String[]{"bad", "with_hint", "good"};
        studentsMap = new HashMap<>();
        studentsNamesArr = new ArrayList<>();
        studentTrialsNamesArr = new ArrayList<>();
        newStudentsNamesArr = new ArrayList<>();
        newStudentTrialsArr = new ArrayList<>();
        studentTrialsIDArr = new ArrayList<>();

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

    public void saveStudentsToDb(ArrayList <String> newStudentsArray) {
        for (int i = 0; i < newStudentsArray.size(); i++) {
            StudentsTable.addStudent(newStudentsArray.get(i), this.getWritableDatabase());
        }
    }
    public void saveTrialsToDbIfNotExists(ArrayList <String> newTrialsArray){
        for (int i = 0; i < newTrialsArray.size(); i++) {

        }
        }

     public ArrayList<String> extractStudentsNamesArray() {
         extractStudents();
             studentsNamesArr.addAll(studentsMap.values());
             return studentsNamesArr;
         }


     public void  extractStudents() {
          studentsMap = StudentsTable.getAllStudentsNames(this.getWritableDatabase());
     }


    public ArrayList<String>  extractTrialsOfStudent(String studentName) {
        SQLiteDatabase database = this.getReadableDatabase();
        //здесь код из 3х таблиц
        //Получаем ID студента по его имени
        int studentId = StudentsTable.getStudentId(database, studentName);
        //Получаем все ID-шники проб студента из практики
        studentTrialsIDArr = PracticingResultsTable.getStudentTrialsIDArray(studentId, database);
        studentTrialsNamesArr = TrialsTable.getNamesOfAllStudentTrial(studentTrialsIDArr, database);
        return studentTrialsNamesArr;
    }

}




