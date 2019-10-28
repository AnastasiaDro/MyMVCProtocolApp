package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String activityName = "MainActivity";
    DataBaseClass dataBaseClass;
    int placeId = R.id.placeholder;
    private MyData myData;
   // ArrayList <String> namesArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   namesArray = new ArrayList<>();
        dataBaseClass = new DataBaseClass(this);
        myData = MyData.getInstance(dataBaseClass);



        Bundle bundle = new Bundle();
        bundle.putInt("CurrentPosition", 0);

        //Создаём фрагмент со списком
        ListFragment fragment = new ListFragment(activityName, myData, placeId);
        fragment.setArguments(bundle);
        fragment.postFragment(this);
    }


    @Override
    public void onPause(){
        super.onPause();
        myData.sendNewStudentsToDb();
    }

    @Override
    public void onStop(){
        super.onStop();
        myData.sendNewStudentsToDb();
    }
}
