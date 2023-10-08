package com.example.recycleviewdemo;

import android.content.Context;

import androidx.room.Room;

// Instance of the database following a Singleton pattern
/* Singletons can only have one instance, so when a new instance is attempted to be made,
*  it will point back to the same instance, for a database, this is useful */
public class ContactDBInstance
{
    private static ContactDatabase database;

    public static ContactDatabase getDatabase(Context context)
    {
        if(database == null)
        {
            database = Room.databaseBuilder(context, ContactDatabase.class, "app_database").allowMainThreadQueries().build();
        }

        return database;
    }
}
