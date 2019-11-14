package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final String activityName = "MainActivity";
    //DataBaseClass dataBaseClass;
    DataBaseHelper dbHelper;
    int placeId = R.id.placeholder;
    private MyData myData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dataBaseClass = new DataBaseClass(this);
        dbHelper = new DataBaseHelper(this);
       // myData = MyData.getInstance(dbHelper);
        myData = MyData.getInstance(dbHelper);
//        Bundle bundle = new Bundle();
//        bundle.putInt("CurrentPosition", 0);

        //Создаём фрагмент со списком
        ListFragment fragment = new ListFragment();
        fragment = fragment.newInstance(activityName,  0);
        //ListFragment fragment = ListFragment.newInstance(activityName,  bundle.getInt("CurrentPosition"), myData);
       // fragment.setArguments(bundle);
        fragment.postFragment(this);
    }
}
