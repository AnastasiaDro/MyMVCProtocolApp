package com.mymur.mymvcprotocolapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

   private MaterialButton addNewBtn;
    private TextView listTitleText;
    //массив для списка
    protected ArrayList<String> stringsArray;
    private String activityName;
    private String myNewString;


    //Fragment fragment;
   private MyData myData;
   private int placeId;
   private MyAdapter myAdapter;
//
    public ListFragment() {
       // All Fragment classes you create must have a public, no-arg constructor.
       // In general, the best practice is to simply never define any constructors at all and rely
       // on Java to generate the default constructor for you. But you could also write something like this:

        // doesn't do anything special
    }


    public ListFragment(String activityName, MyData myData, int placeId) {

        this.activityName = activityName;
        this.myData = myData;
        //this.fragment = this;
        this.placeId = placeId;

        myData.registerObserver(this);
    }

    //Фабричный метод
//    public static ListFragment newInstance(String activityName, int placeId) {
//        Bundle args = new Bundle();
//        args.putString("activityName", activityName);
//        args.putInt("placeId", placeId);
//             ListFragment f = new ListFragment();
//        f.setArguments(args);
//        return f;
//    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stringsArray = new ArrayList<>();
//        this.myData = MyData.getMyData();
//        myData.registerObserver(this);
       // placeId = getArguments().getInt("placeId");
        System.out.println("это placeId  в OncreateView"+ placeId);
        //activityName = getArguments().getString("activityName");
        makeStringsArray();
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerForFragment);

        myAdapter = new MyAdapter(stringsArray, activityName, myData);
        System.out.println("Это стрингсаррэй "+stringsArray.toString());
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //делаем кликлистенер для кнопки
        View.OnClickListener addNewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                final EditText input = new EditText(context);
                createInputDialog(context, input, recyclerView);
            }
        };

        //задаём кнопке addnew кликлистенер
        MaterialButton addNewBtn = view.findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(addNewClickListener);
        //ищем текстВью с заголовком
        listTitleText = view.findViewById(R.id.listTitleText);
        if (activityName == "ProtocolActivity"){
            listTitleText.setText(R.string.set_trial);
        }


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
                //изменяем текущие имя и ID на новые
               // myData.setCurrentStudentNameAndId(myNewString);
                //говорим адаптеру, что данные изменились
                myAdapter.notifyDataSetChanged();
                //тут надо выделить новый элемент




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
        ft.replace(placeId, this);
        ft.commit();

    }



    @Override
    public void updateViewData(String newString) {
        //говорим адаптеру, что данные изменились
        myAdapter.notifyDataSetChanged();

    }

    private void makeStringsArray () {
        //выгрузить из базы данных
        if (activityName == "MainActivity") {
            stringsArray = myData.getNamesArray();
            System.out.println("Это стрингсаррэй в  makeStringsArray ()"+stringsArray.toString());
        } else if (activityName == "ProtocolActivity") {
            String studentName = getActivity().getIntent().getStringExtra("StudentName");
            System.out.println("Имя ученика = "+ studentName);
            stringsArray = myData.loadTrialsFromDb();
        }
    }

}



