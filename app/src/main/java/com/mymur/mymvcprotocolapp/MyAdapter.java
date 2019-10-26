package com.mymur.mymvcprotocolapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> stringsArray;
    String activityName;

    public MyAdapter(ArrayList<String> stringsArray, String activityName) {
        this.stringsArray = stringsArray;
        this.activityName = activityName;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (activityName) {
                        case ("MainActivity"):
                            Intent intent = new Intent(textView.getContext(), ProtocolActivity.class);
                            intent.putExtra("Child", textView.getText().toString());
                            textView.getContext().startActivity(intent);
                            break;
                        case ("Protocol activity"):
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
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        //get element from your dataset at this position
        //replace the contents of the view with that element
        holder.textView.setText(stringsArray.get(position));
    }

    //Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 0;
    }
}



