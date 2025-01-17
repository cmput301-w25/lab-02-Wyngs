package com.example.list_view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;



public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedCityPosition = -1; // To store the position of the selected city
    EditText cityInput;
    Button confirmAddButton;
    Button addCityButton;
    Button deleteCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize ListView
        cityList = findViewById(R.id.CityList);
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Initialize UI Components
        cityInput = findViewById(R.id.CityInput);
        confirmAddButton = findViewById(R.id.ConfirmAddButton);
        addCityButton = findViewById(R.id.AddCityButton);
        deleteCityButton = findViewById(R.id.DeleteCityButton);

        // Add City Button Click Listener
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleInputField(true);
            }
        });

        // Confirm Add Button Click Listener
        confirmAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCity();
            }
        });

        // Delete City Button Click Listener
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCity();
            }
        });

        // Set ListView Item Click Listener
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCityPosition = position; // Update the selected position
                String selectedCity = dataList.get(position);
                Toast.makeText(MainActivity.this, "Selected: " + selectedCity, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void toggleInputField(boolean show) {
        if (show) {
            cityInput.setVisibility(View.VISIBLE);
            confirmAddButton.setVisibility(View.VISIBLE);
            cityInput.requestFocus();

            // Show the soft keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(cityInput, InputMethodManager.SHOW_IMPLICIT);
        } else {
            cityInput.setVisibility(View.GONE);
            confirmAddButton.setVisibility(View.GONE);

            // Hide the soft keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(cityInput.getWindowToken(), 0);
        }
    }


    private void addCity() {
        // Get the user-input city name
        String newCity = cityInput.getText().toString().trim();

        if (!newCity.isEmpty()) {
            dataList.add(newCity); // Add city to the list
            cityAdapter.notifyDataSetChanged(); // Notify adapter about data change
            Toast.makeText(this, newCity + " added", Toast.LENGTH_SHORT).show();
            cityInput.setText(""); // Clear the input field
            toggleInputField(false); // Hide input field
        } else {
            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCity() {
        // Delete the selected city
        if (selectedCityPosition != -1 && selectedCityPosition < dataList.size()) {
            String removedCity = dataList.remove(selectedCityPosition);
            cityAdapter.notifyDataSetChanged(); // Notify adapter about data change
            Toast.makeText(this, removedCity + " deleted", Toast.LENGTH_SHORT).show();
            selectedCityPosition = -1; // Reset selected position
        } else {
            Toast.makeText(this, "No city selected to delete", Toast.LENGTH_SHORT).show();
        }
    }
}





