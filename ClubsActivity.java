package com.example.bookclubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclubapplication.adapters.NavigationAdapter;

public class ClubsActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private Button createClubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubs_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSpinner = findViewById(R.id.spinner_navigation);
        createClubButton = findViewById(R.id.createClubButton);

        String[] options = {"Homepage", "Dashboard", "Settings", "Clubs"};
        NavigationAdapter adapter = new NavigationAdapter(this, R.layout.navigation_dropdown_item, options);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
                Toast.makeText(ClubsActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                switch (selectedOption) {
                    case "Homepage":
                        break;
                    case "Dashboard":
                        startActivity(new Intent(ClubsActivity.this, DashboardActivity.class));
                        break;
                    case "Settings":
                        startActivity(new Intent(ClubsActivity.this, SettingsActivity.class ));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        createClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClubsActivity.this, CreateClubsActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


