package com.example.assignment2parta;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapterAdv extends RecyclerView.Adapter<ContactVHAdv> {

    ArrayList<Contact> data;
    public ContactAdapterAdv(ArrayList<Contact> data){
        this.data = data;
    }
    @NonNull
    @Override
    public ContactVHAdv onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout_adv, parent,false);
        ContactVHAdv myDataVHolderAdv = new ContactVHAdv(view,parent);
        return myDataVHolderAdv;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVHAdv holder, int position) {
        Contact singleData = data.get(position);
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
