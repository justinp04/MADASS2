package com.example.recycleviewdemo;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel
{
    public MutableLiveData<Integer> clickedValue;

    // We need a boolean value to see if we are adding a new contact or modifying an existing contact
    public boolean modify;
    public int position;

    // Constants to refer to
    int CONTACT_LIST = 0;
    int CONTACT_CARD = 1;

    public MainActivityData()
    {
        clickedValue = new MediatorLiveData<Integer>();
        clickedValue.setValue(CONTACT_LIST);
        position = 0;
        modify = false;
    }

    // Accessors
    public int getClickedValue()
    {
        return clickedValue.getValue();
    }

    // Mutators
    // The following method will result in setting the clicked value to navigate to the contact list
    public void toContactList()
    {
        clickedValue.setValue(CONTACT_LIST);
    }

    // The following method will result in setting the clicked value to navigate to the contact card
    public void toContactCard()
    {
        clickedValue.setValue(CONTACT_CARD);
    }
}