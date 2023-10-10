package com.example.assignment2partb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> data;

    public MainActivity()
    {
        data = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            data.add("Hello");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int span = 2;
        RecyclerView rv= findViewById(R.id.imageList);
        rv.setLayoutManager(new GridLayoutManager(this, span));
        ImageDataAdapter adapter = new ImageDataAdapter(data);

        rv.setAdapter(adapter);
    }

    public byte[] toByteArray(Bitmap image)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}