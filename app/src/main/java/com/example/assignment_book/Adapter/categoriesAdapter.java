package com.example.assignment_book.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_book.Model.Categories;
import com.example.assignment_book.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.ViewHolder> {
    private ArrayList<Categories> list_categories = null;

    private Context context;

    public categoriesAdapter(ArrayList<Categories> list_categories, Context context) {
        this.list_categories = list_categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false);

        return new categoriesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull categoriesAdapter.ViewHolder holder, int position) {
        Categories categories = list_categories.get(position);
        holder.chip.setText(categories.getName());
    }

    @Override
    public void onBindViewHolder(@NonNull categoriesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return list_categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Chip chip;

        public ViewHolder(View view) {
            super(view);
            chip = view.findViewById(R.id.chip_categories);
            // Define click listener for the ViewHolder's View

        }


    }

}
