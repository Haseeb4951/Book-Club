package com.example.bookclubapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPdfActivity extends AppCompatActivity {

    private EditText editTextPdfTitle, editTextPdfDescription;
    private ImageButton buttonBack;
    private Button btnPickPdfFile, btnAddPdf;

    private DatabaseReference pdfsRef;
    private StorageReference pdfStorageRef;

    private Uri pdfFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pdf_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pdfsRef = FirebaseDatabase.getInstance().getReference("pdfs");
        pdfStorageRef = FirebaseStorage.getInstance().getReference();

        editTextPdfTitle = findViewById(R.id.editTextPdfTitle);
        editTextPdfDescription = findViewById(R.id.editTextPdfDescription);
        buttonBack = findViewById(R.id.buttonBack);
        btnPickPdfFile = findViewById(R.id.btnPickPdfFile);
        btnAddPdf = findViewById(R.id.btnAddPdf);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to DashboardActivity
                startActivity(new Intent(AddPdfActivity.this, DashboardActivity.class));
                // Finish the current activity
                finish();
            }
        });

        btnPickPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPdfFile();
            }
        });

        btnAddPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPdf();
            }
        });
    }

    private void pickPdfFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            pdfFileUri = data.getData();
            Toast.makeText(this, "File Selected: " + pdfFileUri.getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addPdf() {
        final String title = editTextPdfTitle.getText().toString().trim();
        final String description = editTextPdfDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || pdfFileUri == null) {
            Toast.makeText(this, "Please fill all fields and select a file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload the PDF file to Firebase Storage
        StorageReference fileRef = pdfStorageRef.child(title + ".pdf");
        fileRef.putFile(pdfFileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL for the PDF file
                    fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        // Add PDF data to Firebase Realtime Database
                        addPdfToDatabase(title, description, downloadUri.toString());
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddPdfActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPdfActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void addPdfToDatabase(String title, String description, String fileUrl) {
        String pdfId = pdfsRef.push().getKey();
        if (pdfId != null) {
            Map<String, Object> pdfData = new HashMap<>();
            pdfData.put("id", pdfId);
            pdfData.put("title", title);
            pdfData.put("description", description);
            pdfData.put("fileUrl", fileUrl);

            // Save PDF data to Firebase Realtime Database
            pdfsRef.child(pdfId).setValue(pdfData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddPdfActivity.this, "PDF added successfully", Toast.LENGTH_SHORT).show();
                        // Navigate back to DashboardActivity
                        startActivity(new Intent(AddPdfActivity.this, DashboardActivity.class));
                        // Finish the current activity
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddPdfActivity.this, "Failed to add PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
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
















