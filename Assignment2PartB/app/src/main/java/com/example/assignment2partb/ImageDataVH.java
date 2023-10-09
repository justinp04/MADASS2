package com.example.assignment2partb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageDataVH extends RecyclerView.ViewHolder
{
    //public ImageView image;
    public TextView label;

    public ImageDataVH(@NonNull View itemView)
    {
        super(itemView);

        //image = itemView.findViewById(R.id.image);
        label = itemView.findViewById(R.id.label);
    }
}
