package com.example.recycleviewdemo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactVH extends RecyclerView.ViewHolder{
    public TextView nameTextBox;
    public Button callButton;
    public ContactVH(@NonNull View itemView) {
        super(itemView);
        nameTextBox = itemView.findViewById(R.id.textView);
        callButton = itemView.findViewById(R.id.button);
    }
}

