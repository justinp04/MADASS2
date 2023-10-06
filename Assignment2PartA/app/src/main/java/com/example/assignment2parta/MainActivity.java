package com.example.assignment2parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MyData> data;
    public MainActivity(){
        data = new ArrayList<MyData>();
        data.add(new MyData("AAAAA","0000000"));
        data.add(new MyData("BBBBB","000312321"));
        data.add(new MyData("CCCCC","054545454"));
        data.add(new MyData("DDDDDD","000005"));
        data.add(new MyData("EEEEEE","0000050"));
        data.add(new MyData("FFFFFF","60000600"));
        data.add(new MyData("GGGGGG","60000444"));
        data.add(new MyData("HHHHHH","70003333"));
        data.add(new MyData("IIIIII","90004444"));
        data.add(new MyData("JJJJJJ","80077777"));
        data.add(new MyData("KKKKKK","80444444"));
        data.add(new MyData("LLLLLL","600054353"));
        data.add(new MyData("MMMMMM","5000543545"));
        data.add(new MyData("NNNNNN","3000543543"));
        data.add(new MyData("OOOOOO","2000545435"));
        data.add(new MyData("PPPPPP","1000666666"));
        data.add(new MyData("QQQQQQ","343543"));
        data.add(new MyData("QQQQQQ","343543"));
        data.add(new MyData("RRRRRR","343543"));
        data.add(new MyData("SSSSSS","343543"));

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
        int spanCount = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount,
                GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        /* if you want to check linearlayout uncomment linear layout and commented out Gridlayout*/

        /* This is the simple adpater
        MyDataAdapter adapter = new MyDataAdapter(data);
        * */
        /*this is the advanced adapter*/
        MyDataAdapterAdv adapter = new MyDataAdapterAdv(data);
        rv.setAdapter(adapter);
    }
}