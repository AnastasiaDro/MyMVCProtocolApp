package com.mymur.mymvcprotocolapp;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
   //Связано с выделением:
  // private SparseBooleanArray selectedItems;

    ArrayList<String> stringsArray;
    String activityName;
    int selectedPosition;
    MyData myData;



    public MyAdapter(ArrayList<String> stringsArray, String activityName, MyData myData) {

        this.stringsArray = stringsArray;
        this.activityName = activityName;
       this.myData = myData;
        //Связано с выделением
       // selectedItems = new SparseBooleanArray();
        selectedPosition = 0;

    }


    //Сюда поставим наследование интерфейса OnCreateContextMenuListener
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView textView;

        //ДЛЯ КОНТЕКСТНОГО МЕНЮ
        //сюда вставляем наш контейнер от recyclerView
        LinearLayout myLinearCard;


        public MyViewHolder(final View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.textName);

            //ДЛЯ КОНТЕКСТНОГО МЕНЮ
            //инициализируем контейнер recyclerView
            myLinearCard = itemView.findViewById(R.id.myLinearCard);
            //ДЛЯ КОНТЕКСТНОГО МЕНЮ
            //делаем слушатель контекстного меню
            myLinearCard.setOnCreateContextMenuListener(this);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Связано с выделением
                   int currentPosition = getAdapterPosition();
                   selectItemView(currentPosition, itemView);
                   String textViewContent = textView.getText().toString();
                    switch (activityName) {
                        case ("MainActivity"):
                            Intent intent = new Intent(textView.getContext(), ProtocolActivity.class);
                            intent.putExtra("StudentName", textViewContent);
                            //заменим в MyData имя текущего студента
                            myData.setCurrentStudentNameAndId(textViewContent);
                            itemView.getContext().startActivity(intent);
                            break;
                        case ("ProtocolActivity"):
                            myData.setCurrentTrialNameAndId(textViewContent);
                            break;
                    }
                }

            });



        }
        //здесь делаем меню
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            switch (activityName) {
                case ("MainActivity"):
                    menu.add(this.getAdapterPosition(), 111, 100, R.string.aboutStudent);
                    break;
                case ("ProtocolActivity"):
                    menu.add(this.getAdapterPosition(), 222, 100, R.string.aboutTrial);
                    break;
            }
            menu.add(this.getAdapterPosition(), 333, 200, R.string.hide);
        }


        //Обработка нажатия элемента контекстного меню
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch (item.getItemId()) {
                case 111:
                    //тут показываем инфу о студенте
                    return true;
                case 222:
                    //тут показываем инфу о пробе
                    return true;
                case 333:
                    //тут скрываем из списка
                    removeItem(selectedPosition);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

    }








    //метод скрытия студента из списка
    public void removeItem(int position) {
        stringsArray.remove(position);
        notifyDataSetChanged();
        //TODO
        //соединение с май дата
        //тут в дата базу нужно внести в таблицу студентов, что он визибл фолс - добавить то есть визибл параметр

    }


    public void selectItemView (int currentPosition, View itemView) {
        if (selectedPosition != currentPosition) {
            // Temporarily save the last selected position
            int lastSelectedPosition = selectedPosition;
            // Save the new selected position
            selectedPosition = currentPosition;
            // update the previous selected row
            notifyItemChanged(lastSelectedPosition);
            // select the clicked row
            itemView.setSelected(true);
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        //Связано с выделением
        // Set the selected state of the row depending on the position
       //holder.itemView.setSelected(selectedItems.get(position, false));
        if (position == selectedPosition) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
        //get element from your dataset at this position
        //replace the contents of the view with that element
        holder.textView.setText(stringsArray.get(position));
    }

    //Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stringsArray.size();
    }
}



