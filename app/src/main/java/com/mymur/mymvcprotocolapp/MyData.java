package com.mymur.mymvcprotocolapp;

import com.mymur.mymvcprotocolapp.Interfaces.Observable;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

public class MyData implements Observable {


    private List<Observer> observers;
    private ArrayList<String> namesArray;
    private ArrayList <String> trialsArray;
    String newString;

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void changeData(String newEnteredText) {

    }
}
