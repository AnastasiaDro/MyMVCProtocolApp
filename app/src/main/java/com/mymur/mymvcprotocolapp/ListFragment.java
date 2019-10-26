package com.mymur.mymvcprotocolapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ListFragment extends Fragment {

    MaterialButton addNewBtn;

    //массив для списка
    ArrayList <String> stringsArray;
    String activityName;
    String myNewSting;
    Fragment fragment;
    DataBaseClass dataBaseClass;
    int placeId;
    String studentName;

    public ListFragment(String activityName, DataBaseClass dataBaseClass, int placeId){
        this.stringsArray = stringsArray;
        this.activityName = activityName;
        this.dataBaseClass = dataBaseClass;
        this.fragment = this;
        this.placeId = placeId;
        ArrayList <String> stringsArray = new ArrayList<>();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerForFragment);

        //выгрузить из базы данных
            if (activityName == "MainActivity") {
                dataBaseClass.turnONdataBase();
                stringsArray.addAll(dataBaseClass.extractStudentsNamesArray());
        } else if (activityName == "ProtocolActivity"){
                getActivity().getIntent().getStringExtra("");
            }



        recyclerView.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(stringsArray, activityName);
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //делаем кликлистенер для кнопки
        View.OnClickListener addNewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInputDialog(v.getContext());
                if (myNewSting != null) {
                    stringsArray.add(myNewSting);
                    recyclerView.refreshDrawableState();
                    reDrawFragment(fragment);


                }
            }
        };

        //задаём кнопке addnew кликлистенер
        addNewBtn = view.findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(addNewClickListener);
        return view;
    }



    //делаем диалог с юзером для добавления нового значения в отображаемый массив
    private void createInputDialog(Context context){
        final EditText input = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enter_name);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               myNewSting = input.getText().toString();
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


    private void reDrawFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }

    public void postFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(placeId, this);
        ft.commit();

    }


}
