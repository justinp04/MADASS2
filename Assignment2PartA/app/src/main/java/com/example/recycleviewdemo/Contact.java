package com.example.recycleviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "contacts")
@TypeConverters({BitmapConverter.class})
public class Contact
{
    @ColumnInfo(name = "contact_name")
    @NonNull
    private String name;

    @PrimaryKey
    @NonNull
    private String phoneNumber;

    private Bitmap image;
    private String email;

    public Contact(String name, String phoneNumber, String email)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = BitmapFactory.decodeFile("Assignment2PartA/app/src/main/res/drawable/default_icon.png");

    }
    public Contact(String name, String phoneNumber, String email, Bitmap bitmap)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = bitmap;
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

    public Bitmap getImage()
    {
        return image;
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

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    //Method to check if a new contract uses the same phone number as another.
    public static boolean contactExists(String phoneCheck, ContactDAO contactDAO)
    {
        List<Contact> contacts = contactDAO.getAllContacts();
        boolean exists = false;
        for (int i = 0; i < contacts.size(); i++)
        {
            Contact currContact = contacts.get(i);
            if (currContact.getPhoneNumber().equals(phoneCheck))
                exists = true;
        }
        return exists;
    }
    public boolean contactExists(ContactDAO contactDAO)
    {
        List<Contact> contacts = contactDAO.getAllContacts();
        boolean exists = false;
        for (int i = 0; i < contacts.size(); i++)
        {
            Contact currContact = contacts.get(i);
            if (currContact.getPhoneNumber().equals(phoneNumber))
                exists = true;
        }
        return exists;
    }
}
