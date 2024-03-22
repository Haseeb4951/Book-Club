package com.example.bookclubapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclubapplication.models.Pdf;
import com.example.bookclubapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> implements Filterable {

    private List<Pdf> pdfList;
    private List<Pdf> pdfListFull;
    private Context context;

    public PdfAdapter(List<Pdf> pdfList, Context context) {
        this.pdfList = pdfList;
        this.pdfListFull = new ArrayList<>(pdfList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pdf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pdf pdf = pdfList.get(position);
        holder.titleTv.setText(pdf.getTitle());
        holder.descriptionTv.setText(pdf.getDescription());
        holder.categoryTv.setText(pdf.getCategory());
        holder.sizeTv.setText("File Size Placeholder");
        holder.dateTv.setText("Date Placeholder");
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    @Override
    public Filter getFilter() {
        return pdfFilter;
    }

    private Filter pdfFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pdf> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pdfListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pdf pdf : pdfListFull) {
                    if (pdf.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(pdf);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pdfList.clear();
            pdfList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            categoryTv = itemView.findViewById(R.id.categoryTv);
            sizeTv = itemView.findViewById(R.id.sizeTv);
            dateTv = itemView.findViewById(R.id.dateTv);
        }
    }

    public void filterList(List<Pdf> filteredList) {
        pdfList = filteredList;
        notifyDataSetChanged();
    }
}

