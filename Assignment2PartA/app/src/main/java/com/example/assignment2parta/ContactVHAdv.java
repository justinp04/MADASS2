package com.example.assignment2parta;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactVHAdv extends RecyclerView.ViewHolder{
    public TextView nameTextBox;
    public Button callButton;
    public ContactVHAdv(@NonNull View itemView, ViewGroup parent)
    {
        super(itemView);
        int hSize = parent.getMeasuredHeight() /3;
        //int wSize = parent.getMeasuredWidth()/2;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.height = hSize;
        //lp.width = wSize;
        nameTextBox = itemView.findViewById(R.id.textView);
        callButton = itemView.findViewById(R.id.button);
    }
}

