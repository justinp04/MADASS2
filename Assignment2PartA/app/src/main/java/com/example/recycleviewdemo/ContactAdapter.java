package com.example.recycleviewdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactVH> {

    ArrayList<Contact> data;
    public ContactAdapter(ArrayList<Contact> data){
        this.data = data;
    }
    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout,parent,false);
        ContactVH contactVHolder = new ContactVH(view);
        return contactVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position)
    {
        Contact singleData = data.get(position);
        holder.nameTextBox.setText(singleData.getName());
        holder.callButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Calling "+ singleData.getPhoneNumber(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Values", singleData.getPhoneNumber());
            }
        });

        holder.nameTextBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
