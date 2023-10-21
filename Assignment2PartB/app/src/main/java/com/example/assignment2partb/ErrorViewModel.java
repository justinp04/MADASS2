package com.example.assignment2partb;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErrorViewModel extends ViewModel
{
    public MutableLiveData<Integer> errorCode;

    public ErrorViewModel()
    {
        errorCode = new MutableLiveData<>();
        errorCode.setValue(0);
    }

    public void setErrorCode(int code)
    {
        errorCode.postValue(code);
    }

    public int getErrorCode()
    {
        return errorCode.getValue();
    }
}
