package com.example.recycleviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ContactListFragment contactListFragment = new ContactListFragment();
    ContactCardFragment contactCardFragment = new ContactCardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the DAO for ContactDAO
        ContactDAO contactDAO = ContactDBInstance.getDatabase(getApplicationContext()).contactDAO();

        contactListFragment.setCard(contactCardFragment);
        Fragment contactList = loadContactListFragment();



        // MAke a new view model provider to track information between views
        MainActivityData mainActivityData = new ViewModelProvider(this).get(MainActivityData.class);

        // Observer to look for value changes
        mainActivityData.clickedValue.observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if(mainActivityData.getClickedValue() == mainActivityData.CONTACT_LIST)
                {
                    loadContactListFragment();
                }
                else
                {
                    loadContactCardFragment();
                }
            }
        });


    }

    // The following method loads the ContactListFragment
    private Fragment loadContactListFragment()
    {
        // To load the fragment, we need to create a fragment manager to manage and load fragments
        FragmentManager fm = getSupportFragmentManager();

        // We must then assign the frame that the fm will be loading it in to.
        Fragment frag = fm.findFragmentById(R.id.frameLayout);

        // Either add or replace the fragment depending on the status
        if(frag == null)
        {
            // There is no fragment, thus we need to add
            fm.beginTransaction().add(R.id.frameLayout, contactListFragment).commit();
        }
        else
        {
            // There is a fragment so we must replace
            fm.beginTransaction().replace(R.id.frameLayout, contactListFragment).commit();
        }

        return frag;
    }

    // The following method loads the ContactCardFragment
    private Fragment loadContactCardFragment()
    {// To load the fragment, we need to create a fragment manager to manage and load fragments
        FragmentManager fm = getSupportFragmentManager();

        // We must then assign the frame that the fm will be loading it in to.
        Fragment frag = fm.findFragmentById(R.id.frameLayout);

        // Either add or replace the fragment depending on the status
        if(frag == null)
        {
            // There is no fragment, thus we need to add
            fm.beginTransaction().add(R.id.frameLayout, contactCardFragment).commit();
        }
        else
        {
            // There is a fragment so we must replace
            fm.beginTransaction().replace(R.id.frameLayout, contactCardFragment).commit();
        }

        return frag;
    }
}