package com.example.bookclubapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookclubapplication.R;

public class NavigationAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mOptions;

    public NavigationAdapter(@NonNull Context context, int resource, String[] options) {
        super(context, resource, options);
        mContext = context;
        mOptions = options;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.navigation_dropdown_item, parent, false);
        TextView optionTextView = view.findViewById(R.id.optionTextView);
        optionTextView.setText(mOptions[position]);
        return view;
    }
}

