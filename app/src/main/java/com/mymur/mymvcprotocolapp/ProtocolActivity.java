package com.mymur.mymvcprotocolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class ProtocolActivity extends AppCompatActivity {

    private final String activityName = "ProtocolActivity";
    DataBaseHelper dbHelper;
    int placeId = R.id.placeholderForProtocol;
    private MyData myData;
    int resultCode;
    ListFragment fragment;
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

        fragment = new ListFragment(activityName, myData, placeId);
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

    //Добавим options menu в тулбар
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_protocol_trials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        //handle item selection
        switch (item.getItemId()) {
            case R.id.to_statistics:
                Intent intent = new Intent(ProtocolActivity.this, StatisticsActivity.class);

                // intent.putExtra()
                startActivity(intent);
                return true;
            case R.id.allStudentsList:
                //...
                //fill
                //
                return true;
            case R.id.aboutProgram:
                Intent intent1 = new Intent(ProtocolActivity.this, Activity_about_app.class);
                startActivity(intent1);
                return true;

            case R.id.endSession:
                //ДОДЕЛАТЬ
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //        //Обработка нажатия элемента контекстного меню
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        MyAdapter myAdapter= fragment.getMyAdapter();
        switch (item.getItemId()) {
            case 111:
                //тут показываем инфу о студенте
                return true;
            case 222:
                //тут показываем инфу о пробе
                return true;
            case 333:
                //тут скрываем из списка
                myAdapter.removeItem(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
