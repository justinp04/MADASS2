package com.example.assignment2parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ContactData> data;
    public MainActivity()
    {
        // Eventually have this be via a button onClick listener
        data = new ArrayList<ContactData>();
        data.add(new ContactData("AAAAA","0000000","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("BBBBB","000312321","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("CCCCC","054545454","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("DDDDDD","000005","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("EEEEEE","0000050","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("FFFFFF","60000600","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("GGGGGG","60000444","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("HHHHHH","70003333","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("IIIIII","90004444","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("JJJJJJ","80077777","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("KKKKKK","80444444","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("LLLLLL","600054353","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("MMMMMM","5000543545","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("NNNNNN","3000543543","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("OOOOOO","2000545435","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("PPPPPP","1000666666","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("QQQQQQ","343543","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("QQQQQQ","343543","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("RRRRRR","343543","place@holder.com",R.drawable.empty_view));
        data.add(new ContactData("SSSSSS","343543","place@holder.com",R.drawable.empty_view));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv= findViewById(R.id.recView);
        /* For setting vertical scrolling
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
         */
        /* For setting Horizontal scrolling
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true));
        */
        /*Grid layout with two column*/
        int spanCount = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount,
                GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        /* if you want to check linearlayout uncomment linear layout and commented out Gridlayout*/

        /*
        * */
        /*this is the advanced adapter*/
        ContactDataAdapter adapter = new ContactDataAdapter(data);
        rv.setAdapter(adapter);
    }
}