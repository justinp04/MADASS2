package com.example.assignment2parta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactVH> {

    ArrayList<Contact> data;
    OnAdapterClick listener;
    public ContactAdapter(ArrayList<Contact> data, OnAdapterClick listener){
        this.data = data;
        this.listener = listener;
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
                MainActivityData mainActivityData = new ViewModelProvider((AppCompatActivity) view.getContext()).get(MainActivityData.class);
                //call listener to change data in card before it is loaded.
                listener.onAdapterClick(singleData);

                // We need the position to know where in the list of data to reference
                mainActivityData.position = position;
                mainActivityData.currPhone = data.get(position).getPhoneNumber();
                mainActivityData.modify.setValue(true);
                Log.d("mainactmod", ""+mainActivityData.modify.getValue());

                mainActivityData.toContactCard();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
