package com.example.assignment2partb;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// The purpose of this ViewModel is to hold the response from the Pixabay search.
public class SearchResponseViewModel extends ViewModel
{
    public MutableLiveData<String> response;

    public SearchResponseViewModel()
    {
        response = new MutableLiveData<>();
    }

    public String getResponse()
    {
        return response.getValue();
    }

    public void setResponse(String value)
    {
        response.postValue(value);
    }
}
