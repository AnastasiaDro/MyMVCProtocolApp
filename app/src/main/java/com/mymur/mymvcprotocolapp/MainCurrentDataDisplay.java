package com.mymur.mymvcprotocolapp;


import androidx.recyclerview.widget.RecyclerView;

import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;



//Этот класс выступает как View
public class MainCurrentDataDisplay implements Observer {
    MainModel mainModel;
    RecyclerView recyclerView;


    public MainCurrentDataDisplay(MainModel mainModel, RecyclerView recyclerView) {
        this.mainModel = mainModel;
        this.recyclerView = recyclerView;
        mainModel.registerObserver(this);
    }



    @Override
    public void updateViewData(ArrayList <String> myStudentsArray) {
       //ListFragment.setArray(myStudentsArray);
        System.out.println("Это MainCurrentDataDisplay");
    }
}
