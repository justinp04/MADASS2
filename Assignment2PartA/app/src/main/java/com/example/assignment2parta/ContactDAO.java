package com.example.assignment2parta;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    // This is the insert method is used to insert a single contact into the database
    @Insert
    void insert(Contact... contact);

    // This is the update method
    @Update
    void update(Contact... contact);

    // This is the delete method
    @Delete
    void delete(Contact... contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM contacts WHERE phoneNumber LIKE '%' || :number || '%'")
    Contact getContactByNumber(String number);

    // When using LIKE with a paramterized query, need to concatenate it into a string hence the following
    @Query("SELECT * FROM contacts WHERE contact_name LIKE '%' || :searchName || '%'")
    Contact getContactByName(String searchName);
}
