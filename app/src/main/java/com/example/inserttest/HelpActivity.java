package com.example.inserttest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");

        lv = (ListView) findViewById(R.id.poiList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myList);

        lv.setAdapter(arrayAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myList.removeAll(myList);

    }
}