package com.example.assignment2parta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;

public class BitmapConverter
{
    // This method will convert a bitmap to byte array
    @TypeConverter
    public byte[] fromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // This method will convert a byte array to bitmap
    @TypeConverter
    public Bitmap toBitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
