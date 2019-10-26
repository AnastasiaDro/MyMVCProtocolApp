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

    public ListFragment(ArrayList <String> stringsArray, String activityName){
        this.stringsArray = stringsArray;
        this.activityName = activityName;
        this.fragment = this;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerForFragment);


        recyclerView.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(stringsArray);
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


    public void reDrawFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }



}
