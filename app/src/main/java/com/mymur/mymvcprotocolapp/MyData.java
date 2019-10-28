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
    private DataBaseClass dataBaseClass;
    private List<Observer> observers;
    private ArrayList<String> namesArray;
    private ArrayList <String> studentTrialsArray;
    String newString;

    ArrayList <String> newStudentsNames;
    ArrayList <String> newTrialsNames;

    //делаем из класса синглтон
    private MyData(DataBaseClass dataBaseClass){
        studentTrialsArray = new ArrayList<>();
        newStudentsNames = new ArrayList<>();
        newTrialsNames = new ArrayList<>();
        observers = new LinkedList<>();
        this.dataBaseClass = dataBaseClass;
        //в получившийся аррэйлист загружаем данные из БД
        namesArray = loadNamesFromDb();
    }

    public static MyData getInstance(DataBaseClass dataBaseClass){
        if (instance == null) {
            instance = new MyData(dataBaseClass);
        }
        return instance;
    }




//    public MyData(DataBaseClass dataBaseClass){
//        studentTrialsArray = new ArrayList<>();
//        observers = new LinkedList<>();
//        this.dataBaseClass = dataBaseClass;
//        namesArray = new ArrayList<>();
//        //в получившийся аррэйлист загружаем данные из БД
//        namesArray = loadNamesFromDb();
//    }

    public ArrayList<String> getNamesArray() {
        return namesArray;
    }
    public ArrayList<String> getStudentTrialsArray() {
        return studentTrialsArray;
    }

    //загрузка имен студентов из базы данных
    protected ArrayList <String> loadNamesFromDb(){
        dataBaseClass.turnONdataBase();
        dataBaseClass.createTablesForDb();
        namesArray = dataBaseClass.extractStudentsNamesArray();
        dataBaseClass.turnOFFdataBase();
        return namesArray;
    }

    protected ArrayList<String> loadTrialsFromDb(String studentName) {
        studentTrialsArray = dataBaseClass.extractTrialsOfStudent(studentName);
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
        }
        if (activityName == "ProtocolActivity"){
            this.studentTrialsArray.add(newEnteredText);
            this.newTrialsNames.add(newEnteredText);
        }
        notifyObservers();

    }


    public void sendNewStudentsToDb() {
        dataBaseClass.saveStudentsToDb(newStudentsNames);
    }

}
