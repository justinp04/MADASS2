package com.example.recycleviewdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        Log.d("CREATE VIEW ","View created for fragment");

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list_, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Contact> data = new ArrayList<Contact>();

        data.add(new Contact("AAAAA","0000000"));
        data.add(new Contact("BBBBB","000312321"));
        data.add(new Contact("CCCCC","054545454"));
        data.add(new Contact("DDDDDD","000005"));
        data.add(new Contact("EEEEEE","0000050"));
        data.add(new Contact("FFFFFF","60000600"));
        data.add(new Contact("GGGGGG","60000444"));
        data.add(new Contact("HHHHHH","70003333"));
        data.add(new Contact("IIIIII","90004444"));
        data.add(new Contact("JJJJJJ","80077777"));
        data.add(new Contact("KKKKKK","80444444"));
        data.add(new Contact("LLLLLL","600054353"));
        data.add(new Contact("MMMMMM","5000543545"));
        data.add(new Contact("NNNNNN","3000543543"));
        data.add(new Contact("OOOOOO","2000545435"));
        data.add(new Contact("PPPPPP","1000666666"));
        data.add(new Contact("QQQQQQ","343543"));
        data.add(new Contact("QQQQQQ","343543"));
        data.add(new Contact("RRRRRR","343543"));
        data.add(new Contact("SSSSSS","343543"));

        Log.d("IMPORT", "All data values have been added");

        // Make a reference to the RecyclerView
        RecyclerView rv = view.findViewById(R.id.recView);

        // Set the layout manager
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));

        ContactAdapter adapter = new ContactAdapter(data);

        /*this is the advanced adapter*/
        // ContactAdapterAdv adapter = new ContactAdapterAdv(data);
        rv.setAdapter(adapter);
    }
}