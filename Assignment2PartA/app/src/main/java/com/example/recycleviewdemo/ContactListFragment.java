package com.example.recycleviewdemo;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ContactAdapter adapter;
    private ContactCardFragment card;
    private RecyclerView rv;
    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;

    ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    processPickContactResult(data);
                }
            }
    );


    public ContactListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactList_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactListFragment newInstance(String param1, String param2) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list_, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pickContactLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        processPickContactResult(data);
                    }
                }
        );

        TextView add = view.findViewById(R.id.addContact);
        TextView importContact = view.findViewById(R.id.importButton);

        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        ContactDAO contactDAO = ContactDBInstance.getDatabase(getContext().getApplicationContext()).contactDAO();
        ArrayList<Contact> data = new ArrayList<Contact>(contactDAO.getAllContacts());

        // Make a reference to the RecyclerView
        rv = view.findViewById(R.id.recView);

        // Set the layout manager
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));

        adapter = new ContactAdapter(data, card);

        RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.d("adapter changeD?", "adapter changeD?");

            }
        };

        /*this is the advanced adapter*/
        // ContactAdapterAdv adapter = new ContactAdapterAdv(data);
        rv.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Will set the value to go to the contact card fragment
                mainActivityData.toContactCard();
            }
        });

        // This listener will, when clicked, import contacts from the built in contacts app.
        importContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACT_PERMISSION);
                } else {
                    importButtonClicked();
                }
                adapter.notifyDataSetChanged();
                Log.d("Data update", "Data update");
            }
        });
    }

    private void importButtonClicked() {
        // App to app interactions use explicit intents
        Intent intent = new Intent();

        // Declare the action as starting up the contacts app
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }

    private void processPickContactResult(Intent data) {
        Log.d("poo","poo");
        Uri contactUri = data.getData();
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String name = "";
        String phone = "";
        String email = "";
        Integer id = null;

        String[] queryFields = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };

        Cursor c = getActivity().getContentResolver().query(
                contactUri, queryFields, null, null, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                id = c.getInt(0);
                name = c.getString(1);
            }
        } finally {
            c.close();
        }

        queryFields = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String[] whereValues = new String[]{
                String.valueOf(id)
        };
        c = getActivity().getContentResolver().query(
                phoneUri, queryFields, whereClause, whereValues, null);
        try {
            c.moveToFirst();
            do {
                phone = c.getString(0);
            }
            while (c.moveToNext());
        } finally {
            c.close();
        }

        queryFields = new String[]{
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };
        whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        whereValues = new String[]{
                String.valueOf(id)
        };
        c = getActivity().getContentResolver().query(
                emailUri, queryFields, whereClause, whereValues, null);
        try {
            c.moveToFirst();
            do {
                email = c.getString(0);
            }
            while (c.moveToNext());
        } finally {
            c.close();
        }

        Contact newContact = new Contact(name, phone, email, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_icon));
        ContactDAO contactDAO = ContactDBInstance.getDatabase(getContext().getApplicationContext()).contactDAO();
        boolean exists = newContact.contactExists(contactDAO);
        if (exists)
        {
            Toast toast = Toast.makeText(getContext(), "Contact already exists", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            contactDAO.insert(newContact);
            ArrayList<Contact> newData = new ArrayList<Contact>(contactDAO.getAllContacts());
            adapter = new ContactAdapter(newData, card);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Contact Reading Permission Granted",
                        Toast.LENGTH_SHORT).show();
                importButtonClicked();
            }
        }
    }

    public void setCard(ContactCardFragment card) {
        this.card = card;
    }
}