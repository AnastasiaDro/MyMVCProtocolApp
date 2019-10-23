package com.mymur.mymvcprotocolapp;

import com.mymur.mymvcprotocolapp.Interfaces.ControllerInterface;

public class MainController implements ControllerInterface {
    MainModel mainModel;

    String enteredText;




    @Override
    public void updateModelData() {
        //TODO
        System.out.println("Это контроллер updateModelData" + enteredText);
    }
}
