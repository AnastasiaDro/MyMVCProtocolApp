package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProtocolActivity extends AppCompatActivity {

    private final String activityName = "ProtocolActivity";
   // DataBaseClass dataBaseClass;
    DataBaseHelper dbHelper;
    int placeId = R.id.placeholderForProtocol;
    private MyData myData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        //фэйк, так как все равно ссылаемся на myData, у которого база данных в mainActivity
        dbHelper = new DataBaseHelper(this);
        myData = MyData.getInstance(dbHelper);

        Bundle bundle = new Bundle();
        bundle.putInt("CurrentPosition", 0);

        ListFragment fragment = new ListFragment(activityName, myData, placeId);
        fragment.setArguments(bundle);
        fragment.postFragment(this);
    }


}
