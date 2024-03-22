package com.example.bookclubapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapplication.adapters.CategoryAdapter;
import com.example.bookclubapplication.models.Category;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryDeleteListener, CategoryAdapter.OnCategoryClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference categoriesRef;
    private List<Category> categoryList;
    private List<Category> filteredCategoryList;
    private CategoryAdapter categoryAdapter;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageButton buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the HomepageActivity when the back button is clicked
                startActivity(new Intent(DashboardActivity.this, HomepageActivity.class));
                // Finish the current activity to prevent going back to it when pressing back
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        categoriesRef = FirebaseDatabase.getInstance().getReference("Categories");

        RecyclerView recyclerView = findViewById(R.id.categoriesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryList = new ArrayList<>();
        filteredCategoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(filteredCategoryList, this, this, this);
        recyclerView.setAdapter(categoryAdapter);

        editTextSearch = findViewById(R.id.editTextSearch);

        checkUser();

        loadCategories();

        Button addButton = findViewById(R.id.btnAddCategory);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CategoryAddActivity.class));
            }
        });

        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddPdfActivity.class));
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void loadCategories() {
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    if (category != null) {
                        categoryList.add(category);
                    }
                }
                filterCategories(editTextSearch.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Failed to load categories: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCategories(String searchText) {
        filteredCategoryList.clear();

        for (Category category : categoryList) {
            if (category.getCategory().toLowerCase().contains(searchText.toLowerCase())) {
                filteredCategoryList.add(category);
            }
        }

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryDelete(Category category) {
        DatabaseReference categoryRef = categoriesRef.child(category.getId());
        categoryRef.removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(DashboardActivity.this, "Category deleted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(DashboardActivity.this, "Failed to delete category: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onCategoryClick(Category category) {
        Intent intent = new Intent(DashboardActivity.this, PdfListActivity.class);
        intent.putExtra("categoryId", category.getId());
        startActivity(intent);
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






















