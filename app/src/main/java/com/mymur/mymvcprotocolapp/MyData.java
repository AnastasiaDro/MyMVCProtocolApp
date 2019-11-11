package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.mymur.mymvcprotocolapp.Interfaces.Observable;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyData implements Observable {
    private static MyData instance;
   // private DataBaseClass dataBaseClass;
    private DataBaseHelper dbHelper;
    private List<Observer> observers;
    private ArrayList<String> namesArray;
    private ArrayList <String> studentTrialsArray;
    // Hashmap для проб студента
   private HashMap <String, Integer> studentsHashMap;
    private HashMap <String, Integer> studentsTrialsHashMap;

    String newString;

    ArrayList <String> newStudentsNames;
    ArrayList <String> newTrialsNames;



    public void setCurrentStudentNameAndId(String currentStudentName) {
        this.currentStudentName = currentStudentName;
        this.currentStudentID = studentsHashMap.get(currentStudentName);
    }



    public void setCurrentTrialNameAndId(String currentTrialName) {

        //и надо id пробы тут поменять
        this.currentTrialName = currentTrialName;
      currentTrialId = studentsTrialsHashMap.get(currentTrialName);
    }

    //переменные для записи в массив
    int currentStudentID;
    String currentStudentName;
    int currentTrialId;
    String currentTrialName;
    int currentResCode;

//    public int getCurrentStudentID() {
//        return currentStudentID;
//    }






    public void setCurrentTrialId(int currentTrialId) {
        this.currentTrialId = currentTrialId;
    }

    public void setCurrentResCode(int currentResCode) {
        this.currentResCode = currentResCode;
    }







    //делаем из класса синглтон
    //private MyData(DataBaseClass dataBaseClass){

    private MyData(DataBaseHelper dbHelper){
        studentTrialsArray = new ArrayList<>();
        newStudentsNames = new ArrayList<>();
        newTrialsNames = new ArrayList<>();
        //наш хэшмап
        studentsTrialsHashMap = new HashMap<>();
        studentsHashMap = new HashMap<>();

        observers = new LinkedList<>();
       // this.dataBaseClass = dataBaseClass;
        this.dbHelper = dbHelper;
        //в получившийся аррэйлист загружаем данные из БД
        namesArray = new ArrayList<>();
        loadNamesFromDb(namesArray);
        currentTrialId = -1;
        currentStudentID = -1;
        currentResCode = -1;
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
    protected ArrayList <String> loadNamesFromDb(ArrayList namesArray){
        //dataBaseClass.turnONdataBase();
//        dataBaseClass.createTablesForDb();
        studentsHashMap = dbHelper.extractStudents();
        //добавим все имена студентов из хэщмапа с их именами
        //если массив не пустой, то добавляем в массив имен студентов
        if (!studentsHashMap.isEmpty()) {
            namesArray.addAll(studentsHashMap.keySet());
        }
        return namesArray;
    }

    protected ArrayList<String> loadTrialsFromDb() {
        extractTrialsOfStudentMap();
        studentTrialsArray.addAll(studentsTrialsHashMap.keySet());
        return studentTrialsArray;
    }


    protected HashMap <String, Integer> extractTrialsOfStudentMap() {
        studentsTrialsHashMap = dbHelper.extractTrialsOfStudent(currentStudentName);
        return studentsTrialsHashMap;
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

    //метод записи результата в BD
    public void saveResultOfTrial() {
        if (currentStudentID != -1 && currentTrialId != -1 && currentResCode != -1) {
            dbHelper.addTrialsResult(currentStudentID, currentTrialId, currentResCode, dbHelper.getWritableDatabase());
        } else {

        }
    }

}
