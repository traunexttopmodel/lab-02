package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView cityList; // UI list component
    private ArrayAdapter<String> cityAdapter; // bridges data to ListView
    private ArrayList<String> dataList;

    private int chosen = -1;
    private View chosenView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // loads the main layout xml: activity_main


        //cityList = java object linked to city_list from activity_main
        cityList = findViewById(R.id.city_list); // connects variable to the ListView in activity_main

        // cities to be fed to cityList
        String []cities = {"Edmonton", "Vancouver", "Moscow"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // link content.xml to dataList so each element is displayed in its own row
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);





        // LAB EXERCISE - Add City
        Button addCity = findViewById(R.id.add_city_button);
        Button submit = findViewById(R.id.submit_button);
        EditText inputCity = findViewById(R.id.input_city);


        addCity.setOnClickListener(v -> {
            inputCity.setVisibility(View.VISIBLE); // display text box and submit button
            submit.setVisibility(View.VISIBLE);

            submit.setOnClickListener(v1 -> {
                String userInput = inputCity.getText().toString();

                dataList.add(userInput);
                cityAdapter.notifyDataSetChanged();
                inputCity.setText(""); // clear text box

                inputCity.setVisibility(View.GONE); // hide text box and submit button again
                submit.setVisibility(View.GONE);
            });
        });



        // LAB EXERCISE - Delete City
        Button deleteCity = findViewById(R.id.delete_city_button);
        deleteCity.setEnabled(false);

        cityList.setOnItemClickListener((parent, view, position, id) -> {

            // this is only to ungray the previously chosen city
            if (chosenView != null) { chosenView.setBackgroundColor(Color.TRANSPARENT); }

            deleteCity.setEnabled(true);
            view.setBackgroundColor(Color.GRAY);
            chosen = position;
            chosenView = view;
            cityAdapter.notifyDataSetChanged();

            deleteCity.setOnClickListener(v -> {
                dataList.remove(position);
                cityAdapter.notifyDataSetChanged();
                System.out.println("Position after: " + position);
                deleteCity.setEnabled(false);
                view.setBackgroundColor(Color.TRANSPARENT);
                chosenView = null;
            });
        });

    }
}