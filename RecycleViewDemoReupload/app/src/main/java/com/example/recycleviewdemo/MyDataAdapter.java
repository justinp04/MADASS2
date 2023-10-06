package com.example.recycleviewdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataVH> {

    ArrayList<MyData> data;
    public MyDataAdapter(ArrayList<MyData> data){
        this.data = data;
    }
    @NonNull
    @Override
    public MyDataVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout,parent,false);
        MyDataVH myDataVHolder = new MyDataVH(view);
        return myDataVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataVH holder, int position) {
        MyData singleData = data.get(position);
        holder.nameTextBox.setText(singleData.getName());
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Calling "+ singleData.getPhoneNumber(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Values", singleData.getPhoneNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
