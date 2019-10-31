package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseClass extends SQLiteOpenHelper {
    private SQLiteDatabase myDb;
    private Activity activity;
    ArrayList<String> studentsNamesArr;
    ArrayList<String> trialsNamesArr;
    ArrayList<String> newStudentsNamesArr;
    ArrayList<String> newStudentTrialsArr;
    //ArrayList с id проб студента
    ArrayList<Integer> trialsIdsArr;
    public static int DATABASE_VERSION = 1;
    private int studentId;



    public DataBaseClass(Activity activity) {
        super(activity, "myDb", null, 1);
        this.activity = activity;
        //массив студента

        studentsNamesArr = new ArrayList<>();
        trialsNamesArr = new ArrayList<>();
        newStudentsNamesArr = new ArrayList<>();
        newStudentTrialsArr = new ArrayList<>();

    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
       // myDb.execSQL("CREATE TABLE IF NOT EXISTS Students (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

       myDb.execSQL("CREATE TABLE IF NOT EXISTS Students (id_student INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Trials (id_trial INT PRIMARY KEY, name TEXT)");


        myDb.execSQL("CREATE TABLE IF NOT EXISTS Result_Codes(res_code INT PRIMARY KEY, res_name)");
        //пример:    db.execSQL("create table mytable (" + "id integer primary key autoincrement," + "name text," + "email text" + ");");
        myDb.execSQL("CREATE TABLE IF NOT EXISTS practisingSet(id_training INT PRIMARY KEY, id_trial INT, id_student INT, date DATETIME, res_code INT)");
       // clearBd(myDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //      подключаемся к базе данных
    public void turnONdataBase() {
        // myDb = activity.openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        myDb = getWritableDatabase();
    }
//
//    public void turnOFFdataBase() {
//        myDb.close();
//    }

//    public void createTablesForDb(){
//       // clearBd(myDb);
//        //3 таблицы-справочника и последняя - таблица результатов
//        turnONdataBase();
//        myDb.execSQL("CREATE TABLE IF NOT EXISTS Students (id_student INTEGER UNIQUE PRIMARY KEY, name TEXT)");
//        myDb.execSQL("CREATE TABLE IF NOT EXISTS Result_Codes(res_code INT PRIMARY KEY, res_name)");
//        myDb.execSQL("CREATE TABLE IF NOT EXISTS practisingSet(id_training INT PRIMARY KEY, id_trial INT, id_student INT, date DATETIME, res_code INT)");
//        turnOFFdataBase();
//    }

    //получим имена учеников
    public ArrayList<String> extractStudentsNamesArray() {
        //тут заполним данными массив с именами студентов

        // turnONdataBase();
        Cursor myCursor = myDb.rawQuery("select name from Students", null);
        int l = 0;
        while (myCursor.moveToNext()) {
            l++;
            studentsNamesArr.add(myCursor.getString(0));
            System.out.println(l + " добавили студента");
        }
        //поищем айди студентов
        Cursor pracCursor = myDb.rawQuery("select id_student from Students", null);
     //   Cursor pracCursor = myDb.rawQuery("select _id from Students", null);

        while (myCursor.moveToNext()){
            System.out.println("Это пробный курсор из DataBAse-класса и значение ID " + pracCursor.getInt(0));
        }



        myCursor.close();
        //  turnOFFdataBase();
        return studentsNamesArr;
    }

    //получим имена проб
    public ArrayList<String> extractTrialsOfStudent(String studentName) {
        turnONdataBase();
        //сначала выберем студента из списка
        String sqlString = "select id_student from Students where name = :" + studentName;

        Cursor nameIdCursor = myDb.rawQuery(sqlString, null);
        if (nameIdCursor.moveToFirst()) {
            // Cursor nameIdCursor = myDb.query("Students", "id_student", "name ="+studentName, null, null, null);
            studentId = nameIdCursor.getInt(1);
        } else {
            Toast myToast = Toast.makeText(activity, R.string.have_not_trials, Toast.LENGTH_LONG);
            myToast.show();
        }
        nameIdCursor.close();

        //тут надо проверку на не пуста ли таблица проб

        Cursor myCursor = myDb.query("practisingSet", null, null, null, null, null, null);
        //  myDb.rawQuery("select id_trial INT from practisingSet where id_student = " + studentId, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        trialsIdsArr = new ArrayList<>();
        if (myCursor.moveToFirst()) {
            myCursor = myDb.rawQuery("select id_trial INT from practisingSet where id_student = " + studentId, null);

            while (myCursor.moveToNext()) {
                trialsIdsArr.add(myCursor.getInt(0));
                System.out.println(" trialsIdsArr " + trialsIdsArr.toString());
            }
        } else {
            Toast myToast = Toast.makeText(activity, R.string.have_not_trials, Toast.LENGTH_LONG);
            myToast.show();
        }
        myCursor.close();

        // Cursor trialsNamesCursor;
        Cursor trialsNamesCursor = myDb.query("Trials", null, null, null, null, null, null);
        if (trialsNamesCursor.moveToFirst()) {
            //ПОДУМАТЬ!!!!
            for (int i = 0; i < trialsIdsArr.size(); i++) {
                trialsNamesCursor = myDb.rawQuery("select name from Trials where id_trial =" + trialsIdsArr.get(i), null);
                trialsNamesArr.add(trialsNamesCursor.getString(0));
                Log.d("trialsNamesCursor", "мой массив TrialsNamesArr " + trialsNamesArr.toString());
//        }
                //         trialsNamesCursor = myDb.rawQuery("select name from Trials where id_trial =" + trialsIdsArr.get(i),null);
            }
//        for (int i = 0; i < trialsIdsArr.size(); i++) {
//            trialsNamesCursor = myDb.rawQuery("select name from Trials where id_trial =" + trialsIdsArr.get(i),null);
//            trialsNamesArr.add(trialsNamesCursor.getString(0));
//            Log.d("trialsNamesCursor", "мой массив TrialsNamesArr "+ trialsNamesArr.toString());
//        }

            //turnOFFdataBase();


        }
        return trialsNamesArr;
    }

    public void saveStudentsToDb(ArrayList <String> newStudentsNamesArr) {
        ContentValues studentRow = new ContentValues();
        studentRow.clear();
        String studentName;
        int startId = 0;
        int endId = 100;
        int randomInt;

        turnONdataBase();
        for (int i = 0; i < newStudentsNamesArr.size(); i++) {
            studentName = newStudentsNamesArr.get(i);
            System.out.println("имена новых студентов в  saveStudents"+newStudentsNamesArr.toString());
  //          randomInt = startId + (int) (Math.random() * endId);
            //studentRow.put("id_student", i + 1);



//            String sql = "INSERT INTO Students (name) VALUES (" + studentName +")";
//            myDb.execSQL(sql);
          //  studentRow.put("id_student", randomInt);
            //System.out.println("сработал studentRow.put");
            studentRow.put("name", studentName);
            long rowID = myDb.insert("Students", null, studentRow);
            Log.d("LOG_TAG", "row inserted, ID = " + rowID);
            studentRow.clear();
        }
            Cursor trainingCursor = myDb.rawQuery("select * from Students", null);

            while (trainingCursor.moveToNext()){
                String name = trainingCursor.getString(1);
                System.out.println("Это имя "+ name);
                int id = trainingCursor.getInt(0);
                System.out.println("Это id " + id);
            }

            trainingCursor.close();
//        turnOFFdataBase();

    }

    public void saveTrialsToDb(ArrayList <String> newStudentTrialsArr) {
        ContentValues trialRow = new ContentValues();
        trialRow.clear();
        String trialName;

        for (int i = 0; i < newStudentTrialsArr.size(); i++) {
            trialName = newStudentTrialsArr.get(i);
            System.out.println("имена новых проб в  saveTrials"+newStudentTrialsArr.toString());
            //          randomInt = startId + (int) (Math.random() * endId);
            //studentRow.put("id_student", i + 1);



//            String sql = "INSERT INTO Students (name) VALUES (" + studentName +")";
//            myDb.execSQL(sql);
            //  studentRow.put("id_student", randomInt);
            //System.out.println("сработал studentRow.put");
            trialRow.put("name", trialName);
            long rowID = myDb.insert("Trials", null, trialRow);
            Log.d("LOG_TAG", "TRIAL row inserted, ID = " + rowID);
           trialRow.clear();
        }


    }




    private void clearBd(SQLiteDatabase myDb) {
        myDb.delete("Students", null, null);
        myDb.delete("Trials", null, null);
    }



}