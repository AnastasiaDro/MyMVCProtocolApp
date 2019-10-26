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
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;

public class ListFragment extends Fragment implements Observer {

    MaterialButton addNewBtn;

    //массив для списка
    ArrayList<String> stringsArray;
    String activityName;
    String myNewString;
    Fragment fragment;
    DataBaseClass dataBaseClass;
    RecyclerView recyclerView;
    int placeId;
    String studentName;
    MyAdapter myAdapter;

    public ListFragment(String activityName, DataBaseClass dataBaseClass, int placeId) {
        this.activityName = activityName;
        this.dataBaseClass = dataBaseClass;
        this.fragment = this;
        this.placeId = placeId;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerForFragment);
        stringsArray = new ArrayList<>();
        System.out.println("тут метод с аррэйлистом");
        myNewString = "";
        //выгрузить из базы данных
        if (activityName == "MainActivity") {
            dataBaseClass.turnONdataBase();
            System.out.println("Это стрингсАррэй" + stringsArray.toString());
            System.out.println("Это результат метода" + dataBaseClass.extractStudentsNamesArray());
            stringsArray.addAll(dataBaseClass.extractStudentsNamesArray());
        } else if (activityName == "ProtocolActivity") {
            getActivity().getIntent().getStringExtra("");
        }


        //    recyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(stringsArray, activityName);
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //делаем кликлистенер для кнопки
        View.OnClickListener addNewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Это myNewString " + myNewString);
                //createInputDialog(getContext(), recyclerView);
                final EditText input = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.enter_name);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myNewString = input.getText().toString();

                        System.out.println("Это String myNewString ВНУТРИ метода " + myNewString);
                        //        if (myNewString != null) {
                        stringsArray.add(myNewString);
                        System.out.println("Это myNewString внутри диалогового окна " + myNewString);
                        System.out.println("Это stringsArray после добавления новой строки " + stringsArray.toString());
                        MyAdapter newAdapter = new MyAdapter(stringsArray, activityName);
                        recyclerView.setAdapter(newAdapter);
                        recyclerView.refreshDrawableState();

                        reDrawFragment(fragment);
                        System.out.println("мой адаптер массив строк "+ newAdapter.stringsArray.toString());
//
//                recyclerView.refreshDrawableState();

                        //       }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                System.out.println("myNewString перед билдером " + myNewString);
                builder.show();


            }
        };

        //задаём кнопке addnew кликлистенер
        addNewBtn = view.findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(addNewClickListener);
        return view;
    }


    //делаем диалог с юзером для добавления нового значения в отображаемый массив
//    protected void createInputDialog(Context context, RecyclerView recyclerView) {
//
//        final EditText input = new EditText(context);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(R.string.enter_name);
//        builder.setView(input);
//        // Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                myNewString = input.getText().toString();
//
//                System.out.println("Это String myNewString ВНУТРИ метода " + myNewString);
//      //        if (myNewString != null) {
//                   stringsArray.add(myNewString);
//                   System.out.println("Это myNewString внутри диалогового окна " + myNewString);
//                  System.out.println("Это stringsArray после добавления новой строки " + stringsArray.toString());
//
////                  recyclerView.getAdapter().notifyDataSetChanged();
////
////                recyclerView.refreshDrawableState();
//
//       //       }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//
//    }



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


    public void setMyNewString(String mNewString) {
        this.myNewString = mNewString;
    }

    @Override
    public void updateViewData(String newString) {

    }
}