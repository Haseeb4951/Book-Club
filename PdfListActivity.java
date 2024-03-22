package com.example.bookclubapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapplication.adapters.PdfAdapter;
import com.example.bookclubapplication.models.Pdf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfListActivity extends AppCompatActivity {

    private DatabaseReference pdfsRef;
    private List<Pdf> pdfList;
    private PdfAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_list_activity);

        pdfsRef = FirebaseDatabase.getInstance().getReference("pdfs");

        RecyclerView recyclerView = findViewById(R.id.pdfRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfList = new ArrayList<>();
        pdfAdapter = new PdfAdapter(pdfList, this);
        recyclerView.setAdapter(pdfAdapter);

        loadPDFs();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPDFs(newText);
                return true;
            }
        });
    }

    private void loadPDFs() {
        pdfsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pdfList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pdf pdf = snapshot.getValue(Pdf.class);
                    if (pdf != null) {
                        pdfList.add(pdf);
                    }
                }
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PdfListActivity.this, "Failed to load PDFs: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterPDFs(String query) {
        List<Pdf> filteredList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            for (Pdf pdf : pdfList) {
                if (pdf.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(pdf);
                }
            }
        } else {
            filteredList.addAll(pdfList);
        }
        pdfAdapter.filterList(filteredList);
    }
}






