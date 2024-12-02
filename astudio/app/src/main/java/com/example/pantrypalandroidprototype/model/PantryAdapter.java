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

/**
 * The {@code PantryAdapter} class is a custom adapter for displaying a list of pantry items
 * in a {@link RecyclerView}. Each item in the RecyclerView represents an ingredient with its
 * name and quantity.
 * <p>
 * This adapter handles the binding of pantry data to views and provides functionality to update
 * the displayed list of pantry items dynamically.
 * </p>
 */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {


    /**
     * List of {@link Ingredient} objects representing the pantry items to be displayed.
     */
    private List<Ingredient> pantryItems = new ArrayList<>();

    /**
     * Inflates the view for an individual pantry item and creates a new {@link PantryViewHolder}.
     *
     * @param parent   The parent view group into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new {@link PantryViewHolder} holding the inflated view.
     */
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }


    /**
     * Binds the data of the pantry item at the specified position to the {@link PantryViewHolder}.
     *
     * @param holder   The {@link PantryViewHolder} to bind data to.
     * @param position The position of the pantry item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Ingredient ingredient = pantryItems.get(position);
        holder.bind(ingredient);
    }

    /**
     * Returns the total number of pantry items in the list.
     *
     * @return The number of pantry items in the list.
     */
    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    /**
     * Updates the pantry items in the adapter.
     * @param items the list of new pantry items to display.
     */
    public void updatePantryItems(List<Ingredient> items) {
        this.pantryItems = items;
        notifyDataSetChanged();
    }


    /**
     * The {@code PantryViewHolder} class holds references to the views for each pantry item.
     */
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemNameText;
        private final TextView itemQtyText;


        /**
         * Constructs a {@code PantryViewHolder} and initializes its views.
         *
         * @param itemView The view representing an individual pantry item.
         */
        public PantryViewHolder(View itemView) {
            super(itemView);
            itemNameText = itemView.findViewById(R.id.itemName);
            itemQtyText = itemView.findViewById(R.id.itemQty);
        }

        /**
         * Binds the data of a pantry item to the views.
         *
         * @param ingredient The {@link Ingredient} object containing the data to display.
         */
        public void bind(Ingredient ingredient) {
            itemNameText.setText(ingredient.getName());
            itemQtyText.setText(String.valueOf(ingredient.getQuantity()));
        }
    }
}
