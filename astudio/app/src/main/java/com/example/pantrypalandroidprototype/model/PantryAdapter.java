package com.example.pantrypalandroidprototype.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<Ingredient> pantryItems = new ArrayList<>();

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Ingredient ingredient = pantryItems.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public void setPantryItems(List<Ingredient> items) {
        this.pantryItems = items;
        notifyDataSetChanged();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemNameText;
        private final TextView itemQtyText;

        public PantryViewHolder(View itemView) {
            super(itemView);
            itemNameText = itemView.findViewById(R.id.itemName);
            itemQtyText = itemView.findViewById(R.id.itemQty);
        }

        public void bind(Ingredient ingredient) {
            itemNameText.setText(ingredient.getName());
            itemQtyText.setText(String.valueOf(ingredient.getQuantity()));
        }
    }
}
