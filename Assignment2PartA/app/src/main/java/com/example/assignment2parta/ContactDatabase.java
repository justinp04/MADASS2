package com.example.assignment2parta;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// This is the database instance that will hold the actual database
// It must have the @Database tag including the entities array that lists all of hte data associated
// Must also be an abstract class

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase
{
    // There must be an abstract method for each DAO class that is associated with this Database
    public abstract ContactDAO contactDAO();

    public static boolean checkContactExists(int phoneNumber)
    {
        String query = "SELECT * FROM contacts WHERE phoneNumber = " + phoneNumber;
        return true;
    }
}
