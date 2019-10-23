package com.mymur.mymvcprotocolapp;

import android.util.Log;


import com.mymur.mymvcprotocolapp.Interfaces.Observable;
import com.mymur.mymvcprotocolapp.Interfaces.Observer;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainModel implements Observable {
    //ИМЕННО ЗДЕСЬ важно указать тип переменных в List-e
    private List<Observer> observers;
    //массив списка
    private ArrayList <String>  myStudentsArray;
    int currentActivity;

    //в конструкторе создаем новый список наблюдателей
    public MainModel(){observers = new LinkedList<>(); }

    @Override
    public void registerObserver(Observer newObserver){
        observers.add(newObserver);
        Log.d("MainModel", "Наблюдатель добавлен");
    }

    @Override
    public void removeObserver(Observer newObserver) {observers.remove(newObserver);}

    @Override
    //метод обновляет данные методом "updateData" из класса-наблюдателя CurrentDataDisplay
    public void notifyObservers() {
        //РАБОТЕТ 1
        for (Observer observer : observers)
            observer.updateViewData(myStudentsArray);
        System.out.println("Это notifyObservers ");
    }

    @Override
    public void changeData(String newEnteredText) {
        myStudentsArray.add(newEnteredText);
    }

}
