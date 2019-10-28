package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.util.Log;

import com.mymur.mymvcprotocolapp.Interfaces.Observable;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyData implements Observable {

    private DataBaseClass dataBaseClass;
    private List<Observer> observers;
    private ArrayList<String> namesArray;
    private ArrayList <String> studentTrialsArray;
    String newString;



    public MyData(DataBaseClass dataBaseClass){
        studentTrialsArray = new ArrayList<>();
        observers = new LinkedList<>();
        this.dataBaseClass = dataBaseClass;
        namesArray = new ArrayList<>();
        //в получившийся аррэйлист загружаем данные из БД
        namesArray = loadNamesFromDb();
    }

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
        }
        if (activityName == "ProtocolActivity"){
            this.studentTrialsArray.add(newEnteredText);
        }
        notifyObservers();

    }

}
