package com.example.assignment2partb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataVH>
{
    // Make reference to the list of data that we will be binding to VH in this adapter
    ArrayList<Bitmap> data;

    public ImageDataAdapter(ArrayList<Bitmap> data)
    {
        this.data = data;
    }

    @NonNull
    @Override
    public ImageDataVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Get the layout inflater from the parent context
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.image_list_item, parent, false);

        ImageDataVH imageDataVH = new ImageDataVH(view);

        return imageDataVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDataVH holder, int position)
    {
        // Bind the data to the VH
        holder.label.setText("" + position);
        holder.image.setImageBitmap(data.get(position));

        holder.image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send to firebase
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public Bitmap toBitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
