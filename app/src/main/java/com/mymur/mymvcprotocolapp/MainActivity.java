package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String activityName = "MainActivity";
    DataBaseClass dataBaseClass;
    int placeId = R.id.placeholder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseClass = new DataBaseClass(this);
        dataBaseClass.createTablesForDb();
        dataBaseClass.turnONdataBase();

        Bundle bundle = new Bundle();
        bundle.putInt("CurrentPosition", 0);

        //Создаём фрагмент со списком
        ListFragment fragment = new ListFragment(activityName, dataBaseClass, placeId);
        fragment.setArguments(bundle);
        fragment.postFragment(this);
    }
}
