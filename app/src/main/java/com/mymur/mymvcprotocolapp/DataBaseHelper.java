package com.mymur.mymvcprotocolapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
   // HashMap <Integer, String> studentsMap;
    HashMap <String, Integer> studentsMap;
    ArrayList <Integer> studentTrialsIDArr;
    HashSet  <String> allTrialsSet;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        resNames = new String[]{"bad", "with_hint", "good"};
        studentsMap = new HashMap<>();
        studentsNamesArr = new ArrayList<>();
        studentTrialsNamesArr = new ArrayList<>();
        newStudentsNamesArr = new ArrayList<>();
        newStudentTrialsArr = new ArrayList<>();
        studentTrialsIDArr = new ArrayList<>();
        allTrialsSet = new HashSet<>();
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


     public HashMap<String, Integer>  extractStudents() {
          studentsMap = StudentsTable.getAllStudentsNames(this.getWritableDatabase());
          return studentsMap;
     }


//    public HashMap<String, Integer>  extractTrialsOfStudent(String studentName) {
//        SQLiteDatabase database = this.getReadableDatabase();
//        //хэшмап для проб студента
//        HashMap<String, Integer> studentsTrialsHashMap = new HashMap<>();
//        //здесь код из 3х таблиц
//        //Получаем ID студента по его имени
//        int studentId = StudentsTable.getStudentId(database, studentName);
//        //Получаем все ID-шники проб студента из практики
//        studentTrialsIDArr = PracticingResultsTable.getStudentTrialsIDArray(studentId, database);
//        studentTrialsNamesArr = TrialsTable.getNamesOfAllStudentTrial(studentTrialsIDArr, database);
//        if (studentTrialsIDArr.size() == studentTrialsNamesArr.size()) {
//            for (int i = 0; i < studentTrialsIDArr.size(); i++) {
//            studentsTrialsHashMap.put(studentTrialsNamesArr.get(i), studentTrialsIDArr.get(i));
//            }
//
//        }
//        return studentsTrialsHashMap;
//    }



        public void saveNewTrialToDbIfNotExists (String trialName) {
        TrialsTable.addTrialIfNotExists(trialName, this.getWritableDatabase());
    }

    public void addTrialsResult (int studentId, int trialId, int resCode, SQLiteDatabase database) {
        PracticingResultsTable.addPracticing(studentId, trialId, resCode, database);
    }

    public int getTrialIdByName(String trialName) {
        int trialId = -1;
        trialId = TrialsTable.getAllTrialsNamesAndId(this.getReadableDatabase()).get(trialName);
        return trialId;
    }

    public ArrayList <String> getAllStudentTrialsNamesByIdArrayList(int studentId){
        ArrayList <Integer> allStudentTrialsIds = new ArrayList<>();
        allStudentTrialsIds = PracticingResultsTable.getStudentTrialsIDArray(studentId, this.getReadableDatabase());
        HashMap <Integer, String> allTrialsMap = new HashMap<>();
        allTrialsMap = TrialsTable.getAllTrialsIdAndNames(this.getReadableDatabase());
        ArrayList<String> studentTrialsNames = new ArrayList<>();
        for (int i = 0; i < allStudentTrialsIds.size(); i++) {
            studentTrialsNames.add(allTrialsMap.get(allStudentTrialsIds.get(i)));
        }
        return studentTrialsNames;
    }




}




