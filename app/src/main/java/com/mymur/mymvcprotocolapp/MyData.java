package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.util.Log;

import com.mymur.mymvcprotocolapp.Interfaces.Observable;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyData implements Observable {
    private static MyData instance;
   // private DataBaseClass dataBaseClass;
    private DataBaseHelper dbHelper;
    private List<Observer> observers;
    private ArrayList<String> namesArray;
    private ArrayList <String> studentTrialsArray;
    String newString;

    ArrayList <String> newStudentsNames;
    ArrayList <String> newTrialsNames;

    //делаем из класса синглтон
    //private MyData(DataBaseClass dataBaseClass){

    private MyData(DataBaseHelper dbHelper){
        studentTrialsArray = new ArrayList<>();
        newStudentsNames = new ArrayList<>();
        newTrialsNames = new ArrayList<>();
        observers = new LinkedList<>();
       // this.dataBaseClass = dataBaseClass;
        this.dbHelper = dbHelper;
        //в получившийся аррэйлист загружаем данные из БД
        namesArray = loadNamesFromDb();
    }


public static MyData getInstance(DataBaseHelper dbHelper){
        if (instance == null) {
            instance = new MyData(dbHelper);
        }
        return instance;
    }


    public ArrayList<String> getNamesArray() {
        return namesArray;
    }
    public ArrayList<String> getStudentTrialsArray() {
        return studentTrialsArray;
    }

    //загрузка имен студентов из базы данных
    protected ArrayList <String> loadNamesFromDb(){
        //dataBaseClass.turnONdataBase();
//        dataBaseClass.createTablesForDb();

        namesArray = dbHelper.extractStudentsNamesArray();

        return namesArray;
    }

    protected ArrayList<String> loadTrialsFromDb(String studentName) {
        studentTrialsArray = dbHelper.extractTrialsOfStudent(studentName);
        return studentTrialsArray;
    }






    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateViewData(newString);
        }
    }

    @Override
    public void changeArrayList(String newEnteredText, String activityName) {
        this.newString = newEnteredText;
        if (activityName == "MainActivity") {
            this.namesArray.add(newEnteredText);
            Log.d("7", "namesArray внутри MyData"+ namesArray.toString());
            this.newStudentsNames.add(newEnteredText);
            System.out.println("Это newStudentsArr внутри MyData"+ newStudentsNames.toString());
            saveNewStudentsToDb();

        }
        if (activityName == "ProtocolActivity"){
            this.studentTrialsArray.add(newEnteredText);
           // this.newTrialsNames.add(newEnteredText);
           // сохраняем пробу в базу данных проб
            saveNewTrialToDb(newEnteredText);
        }
        notifyObservers();

    }


    public void saveNewStudentsToDb() {
        dbHelper.saveStudentsToDb(newStudentsNames);
    }

    public void saveNewTrialToDb(String newTrial) {
       dbHelper.saveNewTrialToDbIfNotExists(newTrial);
    }

    //метод записи в кэш данных
    //каждые 10 проб отправляем в базу
    public void saveResultOfTrialToArr() {

    }

}
