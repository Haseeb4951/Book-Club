package com.example.bookclubapplication;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfViewerActivity extends AppCompatActivity {

    private ImageView imageView;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_viewer_activity);
        imageView = findViewById(R.id.imageView);
        displayPdfFromFirebase("sample.pdf");
    }

    private void displayPdfFromFirebase(String fileName) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference pdfRef = storage.getReference().child("pdfs/" + fileName);

        pdfRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    File file = new File(getCacheDir(), fileName);
                    FileOutputStream output = new FileOutputStream(file);
                    output.write(bytes);
                    output.close();
                    openRenderer(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PdfViewerActivity.this, "Error opening PDF", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PdfViewerActivity.this, "Error downloading PDF from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openRenderer(File file) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        displayPage(0);
    }

    private void displayPage(int index) {
        if (currentPage != null) {
            currentPage.close();
        }
        currentPage = pdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentPage != null) {
            currentPage.close();
        }
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
    }
}



