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



    public MyAdapter(ArrayList<String> stringsArray, String activityName) {

        this.stringsArray = stringsArray;
        this.activityName = activityName;

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
                    // Save the selected positions to the SparseBooleanArray
//                    if (selectedItems.get(getAdapterPosition(), false)) {
//                        selectedItems.delete(getAdapterPosition());
//                        itemView.setSelected(false);
//                    }
//                    else {
//                        selectedItems.put(getAdapterPosition(), true);
//                        itemView.setSelected(true);
//                    }
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

                    switch (activityName) {
                        case ("MainActivity"):
                            Intent intent = new Intent(textView.getContext(), ProtocolActivity.class);
                            intent.putExtra("StudentName", textView.getText().toString());
                            itemView.getContext().startActivity(intent);
                            break;
                        case ("ProtocolActivity"):
                            //Если активность protocolActivity, то при нажатии на пробу заполняется массив этой пробы и
                            //TODO

                            break;
                    }
                }

            });

        }

    }
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        Log.d("ViewHolder", "stringsArray во ViewHolder" +stringsArray);
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



