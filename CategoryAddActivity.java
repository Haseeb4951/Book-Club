package com.example.bookclubapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookclubapplication.models.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryAddActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference categoriesRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add_activity);

        firebaseAuth = FirebaseAuth.getInstance();

        categoriesRef = FirebaseDatabase.getInstance().getReference("Categories");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding category...");
        progressDialog.setCancelable(false);

        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });
    }

    private void addCategory() {
        EditText editTextCategoryName = findViewById(R.id.editTextCategoryName);
        String categoryName = editTextCategoryName.getText().toString().trim();

        if (!categoryName.isEmpty()) {
            progressDialog.show();

            String categoryId = categoriesRef.push().getKey();
            String uid = firebaseAuth.getCurrentUser().getUid();
            long timestamp = System.currentTimeMillis();
            String bookTitle = "";
            String bookDescription = "";

            Category category = new Category(categoryId, categoryName, uid, timestamp, bookTitle, bookDescription);

            categoriesRef.child(categoryId).setValue(category)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(CategoryAddActivity.this, "Category added successfully", Toast.LENGTH_SHORT).show();
                            editTextCategoryName.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CategoryAddActivity.this, "Failed to add category: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show();
        }
    }
}













