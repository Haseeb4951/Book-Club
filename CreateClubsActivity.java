package com.example.bookclubapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CreateClubsActivity extends AppCompatActivity {

    private EditText editTextClubTitle;
    private EditText editTextClubDescription;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_clubs_activity);

        // Setup ActionBar with back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        editTextClubTitle = findViewById(R.id.editTextClubTitle);
        editTextClubDescription = findViewById(R.id.editTextClubDescription);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Set click listener for the "Create Club" button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createClub();
            }
        });
    }

    private void createClub() {
        // Retrieve input from EditText fields
        String clubName = editTextClubTitle.getText().toString().trim();
        String clubDescription = editTextClubDescription.getText().toString().trim();

        // Check if all fields are filled
        if (clubName.isEmpty() || clubDescription.isEmpty()) {
            // If any field is empty, show a toast message
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // If all fields are filled, show a success message
        Toast.makeText(this, "Club created successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle back button click
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

