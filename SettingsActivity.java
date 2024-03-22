package com.example.bookclubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        mSpinner = findViewById(R.id.spinner_navigation);
        String[] options = {"Homepage", "Dashboard", "Settings", "Clubs"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
                Toast.makeText(SettingsActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                switch (selectedOption) {
                    case "Homepage":
                        break;
                    case "Dashboard":
                        startActivity(new Intent(SettingsActivity.this, DashboardActivity.class));
                        break;
                    case "Clubs":
                        startActivity(new Intent(SettingsActivity.this, ClubsActivity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onLogoutButtonClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}

