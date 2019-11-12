package com.mymur.mymvcprotocolapp;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public MyViewHolder(final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Связано с выделением
                   int currentPosition = getAdapterPosition();
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
                            //Если активность protocolActivity, то при нажатии на пробу берется название этой пробы textViewContent
                            //открываем базу данных

                            //TODO
                            myData.setCurrentTrialNameAndId(textViewContent);
                            break;
                    }
                }

            });

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



