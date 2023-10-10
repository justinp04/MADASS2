package com.example.recycleviewdemo;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Character.*;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactCardFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean imageCaptured = false;

    ImageView contactPicture;

    // ActivityResultLauncher to retrieve and store the information.
    ActivityResultLauncher<Intent> pictureLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK)
                        {
                            Intent data = result.getData();
                            Bitmap image = (Bitmap) data.getExtras().get("data");
                            if (image != null)
                            {
                                contactPicture.setImageBitmap(image);
                            }
                        }
                    });

    public ContactCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactCardFragment newInstance(String param1, String param2) {
        ContactCardFragment fragment = new ContactCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_card, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        EditText name = view.findViewById(R.id.name);
        EditText number = view.findViewById(R.id.phoneNumber);
        EditText email = view.findViewById(R.id.email);

        contactPicture = view.findViewById(R.id.image);

        Button capture = view.findViewById(R.id.captureImage);

        TextView back = view.findViewById(R.id.backButton);
        TextView save = view.findViewById(R.id.addContact);

        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        // getActivity() retrieves the activity the fragment is associated with, hence it will be the same as MainActivity.

        ContactDAO contactDAO = ContactDBInstance.getDatabase(getContext().getApplicationContext()).contactDAO();

        // If we are modifying a contact instead of adding new data
        mainActivityData.modify.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bboolean) {
                if(mainActivityData.modify.equals(true))
                {
                    // Retrieve the correct contact data
                    List<Contact> list = contactDAO.getAllContacts();
                    Contact temp = list.get(mainActivityData.position);

                    imageCaptured = true;

                    // Set the values to what should already exist
                    Log.d("modify", ""+temp.getName());
                    name.setText(temp.getName());
                    number.setText(temp.getPhoneNumber());
                    email.setText(temp.getEmail());
                    contactPicture.setImageBitmap(temp.getImage());

                    mainActivityData.modify.setValue(false);
                    Log.d("EXISTS","The contact exists" + temp.getName());
                }
            }
            });


        if (mainActivityData.modify.equals(true)) {
            // Retrieve the correct contact data
            List<Contact> list = contactDAO.getAllContacts();
            Contact temp = list.get(mainActivityData.position);

            imageCaptured = true;

            // Set the values to what should already exist
            Log.d("modify", ""+temp.getName());
            name.setText(temp.getName());
            number.setText(temp.getPhoneNumber());
            email.setText(temp.getEmail());
            contactPicture.setImageBitmap(temp.getImage());

            mainActivityData.modify.setValue(false);
            Log.d("EXISTS","The contact exists" + temp.getName());
        }


        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Reset the values of the EditText views
                name.setText("Name");
                number.setText("Mobile Number");
                email.setText("Email");
                Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_icon);
                contactPicture.setImageBitmap(icon);

                // Will set the value so that it goes back to the contact list
                mainActivityData.toContactList();
                mainActivityData.modify.setValue(false);
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Check if values are null or not
                if(name.getText() == null || number.getText() == null){}
                else if(!isNumber(number.getText().toString()))
                {
                    Toast toast = Toast.makeText(getContext(), "Mobile Number invalid", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    if (mainActivityData.modify.equals(true)) {
                        String phone = mainActivityData.currPhone;
                        Contact currContact = contactDAO.getContactByNumber(Integer.parseInt(phone));

                    } else {
                        Contact newContact = new Contact(name.getText().toString(), number.getText().toString(), email.getText().toString());
                        if (imageCaptured)
                            newContact.setImage(((BitmapDrawable) contactPicture.getDrawable()).getBitmap());
                        else
                            newContact.setImage(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_icon));
                        contactDAO.insert(newContact);
                    }

                    // Reset the values of the EditText views
                    name.setText("Name");
                    number.setText("Mobile Number");
                    email.setText("Email");
                    contactPicture.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_icon));

                    mainActivityData.toContactList();
                    mainActivityData.modify.setValue(false);
                }
            }
        });

        capture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Implicit intent to call the photo app.
                Intent intent = new Intent();

                // Set the action to navigate to the photo app.
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureLauncher.launch(intent);

                imageCaptured = true;

            }
        });
    }

    // The following method will check if a given string contains letters or not
    private boolean isNumber(String string)
    {
        boolean isNumber = true;
        try
        {
            int number = Integer.parseInt(string);
        }
        catch(NumberFormatException exception)
        {
            isNumber = false;
        }

        return isNumber;
    }
}