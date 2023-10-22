package com.example.assignment2partb;
import android.graphics.Bitmap;

//Custom listener class to update contact card information when the adapter is clicked.
public interface OnAdapterClick {
    void onAdapterClick(String label, Bitmap bitmap);
}