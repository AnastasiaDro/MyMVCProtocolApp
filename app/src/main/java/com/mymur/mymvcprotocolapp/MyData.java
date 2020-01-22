package com.mymur.mymvcprotocolapp;


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

        System.out.println("Это метод setCurrentStudentNameAndId ");
        System.out.println("Это currentStudentName " + currentStudentName);
        System.out.println("Это currentStudentID " + currentStudentID);
        System.out.println("Это значения id в studentsHashMap " + studentsHashMap.toString());

    }



    public void setCurrentTrialNameAndId(String currentTrialName) {

        //и надо id пробы тут поменять
        this.currentTrialName = currentTrialName;
        System.out.println("Это метод setCurrentTrialNameAndId(String currentTrialName)");
        System.out.println("Это studentsTrialsHashMap " + studentsTrialsHashMap.toString());
        currentTrialId = getTrialId(currentTrialName);
    }

    //переменные для записи в массив
    private int currentStudentID;
    private String currentStudentName;
    private int currentTrialId;
    private String currentTrialName;
    private int currentResCode;


    public void setCurrentResCode(int currentResCode) {
        this.currentResCode = currentResCode;
    }

    //делаем из класса синглтон

    private MyData(DataBaseHelper dbHelper){
        studentTrialsArray = new ArrayList<>();
        newStudentsNames = new ArrayList<>();
        newTrialsNames = new ArrayList<>();
        //наш хэшмап
        studentsTrialsHashMap = new HashMap<>();
        studentsHashMap = new HashMap<>();

        observers = new LinkedList<>();
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



    public static MyData getMyData(){
       return instance;
        }


    public ArrayList<String> getNamesArray() {
        return namesArray;
    }
    public ArrayList<String> getStudentTrialsArray() {
        return studentTrialsArray;
    }

    //TODO
    public void setNamesArray(ArrayList arrayList) {
        namesArray = arrayList;
    }

    //загрузка имен студентов из базы данных
    protected ArrayList <String> loadNamesFromDb(ArrayList namesArray){

        studentsHashMap = dbHelper.extractStudents();
        //добавим все имена студентов из хэщмапа с их именами
        //если массив не пустой, то добавляем в массив имен студентов
        if (!studentsHashMap.isEmpty()) {
            namesArray.addAll(studentsHashMap.keySet());
        }
        return namesArray;
    }

    //TODO
//        protected ArrayList <String> loadNamesFromDb(final ArrayList namesArray){
//        Thread thread = new Thread(new Runnable() {
//            ArrayList <String> tNamesArray = new ArrayList();
//            @Override
//            public void run() {
//                studentsHashMap = dbHelper.extractStudents();
//                //добавим все имена студентов из хэщмапа с их именами
//                //если массив не пустой, то добавляем в массив имен студентов
//                if (!studentsHashMap.isEmpty()) {
//                    tNamesArray.addAll(studentsHashMap.keySet());
//                    setNamesArray(tNamesArray);
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e){
//            System.out.printf("%s has been interrupted", thread.getName());
//        }
//
//        return namesArray;
//    }

    protected ArrayList<String> loadTrialsFromDb() {
        studentTrialsArray.clear();
        studentTrialsArray.addAll(dbHelper.getAllStudentTrialsNamesByIdArrayList(currentStudentID));
        return studentTrialsArray;
    }


    //выгрузка пробы конкретного студента из БД
//    //TODO
//    protected HashMap <String, Integer> extractTrialsOfStudentMap() {
//        studentsTrialsHashMap = dbHelper.extractTrialsOfStudent(currentStudentName);
//        System.out.println("Это extractTrialsOfStudentMap() и studentsTrialsHashMap " + studentsTrialsHashMap.toString());
//        return studentsTrialsHashMap;
//    }

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
            this.newStudentsNames.add(newEnteredText);
            saveNewStudentsToDb();
            namesArray.clear();
            loadNamesFromDb(this.namesArray);
            System.out.println("Это newStudentsArr внутри MyData"+ newStudentsNames.toString());

        }
        if (activityName == "ProtocolActivity"){
            this.studentTrialsArray.add(newEnteredText);
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

    private int getTrialId(String trialName){
        return dbHelper.getTrialIdByName(currentTrialName);
    }


    //метод записи результата в BD
    public void saveResultOfTrial() {
        //проблемы с Id пробы
        System.out.println("saveResultOfTrial() currentStudentId: "+ currentStudentID);
        System.out.println("saveResultOfTrial() currentTrialId: "+ currentTrialId);
        System.out.println("saveResultOfTrial() currentResCode: "+ currentResCode);
        if (currentStudentID != -1 && currentTrialId != -1 && currentResCode != -1) {
            dbHelper.addTrialsResult(currentStudentID, currentTrialId, currentResCode, dbHelper.getWritableDatabase());
            System.out.println("Сработал saveResultOfTrial() ");
        } else {

        }
    }

}
