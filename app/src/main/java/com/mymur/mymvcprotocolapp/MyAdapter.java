package com.mymur.mymvcprotocolapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> mTextArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;



        public MyViewHolder(View itemView, View.OnClickListener onClickListener)  {
            super(itemView);
            textView = itemView.findViewById(R.id.textName);
            itemView.setOnClickListener(onClickListener);

        }
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
