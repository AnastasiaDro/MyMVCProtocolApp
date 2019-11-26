package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

        dbHelper = new DataBaseHelper(this);
        myData = MyData.getInstance(dbHelper);

        //Создаём фрагмент со списком


            ListFragment fragment = new ListFragment(activityName, myData, placeId);
            System.out.println("Это плэйсайди в мэйн активити oncreate " + placeId);
            //ListFragment fragment = ListFragment.newInstance(activityName,  bundle.getInt("CurrentPosition"), myData);
            // fragment.setArguments(bundle);
            fragment.postFragment(this);

    }

    //создадим options menu
    //тут почему-то не пришлось даже создавать тулбар, он был сразу
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_students, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        //handle item selection
        switch (item.getItemId()) {
            case R.id.to_statistics:
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);

               // intent.putExtra()
                startActivity(intent);
                return true;
            case R.id.allStudentsList:
                //...
                //fill
                //
            return true;
            case R.id.aboutProgram:
               Intent intent1 = new Intent(MainActivity.this, Activity_about_app.class);
               startActivity(intent1);
             return true;

            case R.id.endSession:
                //ДОДЕЛАТЬ
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
