package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;

public class ListFragment extends Fragment implements Observer {

    MaterialButton addNewBtn;

    //массив для списка
    ArrayList<String> stringsArray;
    String activityName;
    String myNewString;
    Fragment fragment;
    MyData myData;
   // RecyclerView recyclerView;
    int placeId;
    String studentName;
    MyAdapter myAdapter;

    public ListFragment(String activityName, MyData myData, int placeId) {

        this.activityName = activityName;
        this.myData = myData;
        this.fragment = this;
        this.placeId = placeId;
        fragment = this;
        stringsArray = this.makeStringsArray();
        myData.registerObserver(this);
        Log.d("1", "stringsArray из конструктора фрагмента: "+ stringsArray.toString());

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
       final RecyclerView recyclerView = view.findViewById(R.id.recyclerForFragment);
//        stringsArray = new ArrayList<>();
//        System.out.println("тут метод с аррэйлистом");
//     //   myNewString = "";
//        //выгрузить из базы данных
//        if (activityName == "MainActivity") {
//            stringsArray = myData.getNamesArray();
//        } else if (activityName == "ProtocolActivity") {
//            String studentName = getActivity().getIntent().getStringExtra("StudentName");
//            stringsArray = myData.loadTrialsFromDb(studentName);
//
//        }


        //    recyclerView.setHasFixedSize(true);
        Log.d("2", "stringsArray перед передачей адаптеру: "+ stringsArray.toString());
        myAdapter = new MyAdapter(stringsArray, activityName);
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //делаем кликлистенер для кнопки
        View.OnClickListener addNewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d("3","Это myNewString " + myNewString);
                Log.d("4", "stringsArray во время addNewClickListener: "+ stringsArray.toString());
                final Context context = v.getContext();
                final EditText input = new EditText(context);
                createInputDialog(context, input, recyclerView);


            }
        };

        //задаём кнопке addnew кликлистенер
        addNewBtn = view.findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(addNewClickListener);
        return view;
    }


    //делаем диалог с юзером для добавления нового значения в отображаемый массив
    protected void createInputDialog(Context context, final EditText input, final RecyclerView recyclerView) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enter_name);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               myNewString = input.getText().toString();
                //изменяем данные в Observable классе и в нем уже уведомляем об этом наблюдателей
                myData.changeArrayList(myNewString, activityName);
                //говорим адаптеру, что данные изменились
                myAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    public void postFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(placeId, this);
        ft.commit();

    }



    @Override
    public void updateViewData(String newString) {
        stringsArray.add(newString);
    }

    private ArrayList <String> makeStringsArray () {
       ArrayList makeStringsArray = new ArrayList<>();
        System.out.println("тут метод с аррэйлистом");
        //   myNewString = "";
        //выгрузить из базы данных
        if (activityName == "MainActivity") {
            stringsArray = myData.getNamesArray();
        } else if (activityName == "ProtocolActivity") {
            String studentName = getActivity().getIntent().getStringExtra("StudentName");
            stringsArray = myData.loadTrialsFromDb(studentName);

        }
        return makeStringsArray;
    }

}



