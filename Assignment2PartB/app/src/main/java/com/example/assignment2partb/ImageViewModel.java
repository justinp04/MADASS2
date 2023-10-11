package com.example.assignment2partb;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// The purpose of this class is for storing images
public class ImageViewModel extends ViewModel
{
    // Since we are using a recycler view,
    public MutableLiveData<Bitmap> image;

    public ImageViewModel()
    {
        image = new MutableLiveData<>();
    }

    public void setImage(Bitmap image)
    {
        this.image.postValue(image);
    }

    public Bitmap getImage()
    {
        return image.getValue();
    }
}
