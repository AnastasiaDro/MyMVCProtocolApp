package com.mymur.mymvcprotocolapp;

import android.content.Context;
import android.widget.EditText;

import com.mymur.mymvcprotocolapp.Interfaces.ControllerInterface;

public class ListFragmentController implements ControllerInterface {
    MainModel mainModel;
    //ввод текста поле ЗДЕСЬ
    String enteredText;
    Context context;
    EditText input;

    public ListFragmentController (MainModel mainModel, Context context) {
        this.mainModel = mainModel;
        this.context = context;
        //
    }


    @Override
    public void updateModelData() {
        //TODO
    }
}
