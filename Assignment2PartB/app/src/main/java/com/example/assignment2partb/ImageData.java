package com.example.assignment2partb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

public class ImageData
{
    private Bitmap photo;
    private TextView label;

    public ImageData(byte[] byteArray)
    {
        photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        label.setText("");
    }

    public void setPhoto(byte[] byteArray)
    {
        photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public void setPhoto(Bitmap photo)
    {
        this.photo = photo;
    }
}
