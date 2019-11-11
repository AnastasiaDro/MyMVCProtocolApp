package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProtocolActivity extends AppCompatActivity {

    private final String activityName = "ProtocolActivity";
   // DataBaseClass dataBaseClass;
    DataBaseHelper dbHelper;
    int placeId = R.id.placeholderForProtocol;
    private MyData myData;
    int selectedStudentId;
    int selectedTrialId;
    int resultCode;

    Button badBtn;
    Button withHintBtn;
    Button goodBtn;


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

        badBtn = findViewById(R.id.btnBadAnswer);
        withHintBtn = findViewById(R.id.btnWHanswer);
        goodBtn= findViewById(R.id.btnGoodAnswer);

    }


    public void onResultClick(View view) {
        switch (view.getId()) {
            case (R.id.btnBadAnswer): {
                resultCode = 1;
                break;
            }
            case (R.id.btnWHanswer): {
                resultCode = 2;
                break;
            }
            case (R.id.btnGoodAnswer):{
                resultCode = 3;
                break;
            }
        }
        //ЗДЕСЬ МЕТОД ОТПРАВКИ В БАЗУ ДАННЫХ ПРОБЫ
        myData.setCurrentResCode(resultCode);
        myData.saveResultOfTrial();
    }
}
