package com.example.bookclubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclubapplication.adapters.NavigationAdapter;

public class HomepageActivity extends AppCompatActivity {

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        mSpinner = findViewById(R.id.spinner_navigation);

        String[] options = {"Homepage", "Dashboard", "Settings", "Clubs"};
        NavigationAdapter adapter = new NavigationAdapter(this, R.layout.navigation_dropdown_item, options);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
                Toast.makeText(HomepageActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();

                switch (selectedOption) {
                    case "Dashboard":
                        startActivity(new Intent(HomepageActivity.this, DashboardActivity.class));
                        break;
                    case "Settings":
                        startActivity(new Intent(HomepageActivity.this, SettingsActivity.class));
                        break;
                    case "Clubs":
                        startActivity(new Intent(HomepageActivity.this, ClubsActivity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

