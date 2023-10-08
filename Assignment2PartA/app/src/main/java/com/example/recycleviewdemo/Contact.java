package com.example.recycleviewdemo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact
{
    @ColumnInfo(name = "contact_name")
    @NonNull
    private String name;

    @PrimaryKey
    @NonNull
    private String phoneNumber;

    private String email;
    public Contact(String name, String phoneNumber, String email)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
