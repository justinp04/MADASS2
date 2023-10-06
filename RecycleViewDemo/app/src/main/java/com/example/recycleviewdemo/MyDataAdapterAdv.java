package com.example.recycleviewdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyDataAdapterAdv extends RecyclerView.Adapter<MyDataVHAdv> {

    ArrayList<MyData> data;
    public MyDataAdapterAdv(ArrayList<MyData> data){
        this.data = data;
    }
    @NonNull
    @Override
    public MyDataVHAdv onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout_adv,
                parent,false);
        MyDataVHAdv myDataVHolderAdv = new MyDataVHAdv(view,parent);
        return myDataVHolderAdv;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataVHAdv holder, int position) {
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
