package com.example.recycleviewdemo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDataVH extends RecyclerView.ViewHolder{
    public TextView nameTextBox;
    public Button callButton;
    public MyDataVH(@NonNull View itemView) {
        super(itemView);
        nameTextBox = itemView.findViewById(R.id.textView);
        callButton = itemView.findViewById(R.id.button);
    }
}

