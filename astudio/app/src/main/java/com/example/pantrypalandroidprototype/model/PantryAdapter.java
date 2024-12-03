package com.example.pantrypalandroidprototype.model;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.ItemPantryBinding;

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
        // Inflate the layout using ViewBinding
        ItemPantryBinding binding = ItemPantryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PantryViewHolder(binding);
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
        ItemPantryBinding binding;

        /**
         * Constructs a {@code PantryViewHolder} and initializes its views.
         *
         * @param binding The binding representing an individual pantry item.
         */
        public PantryViewHolder(ItemPantryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds the data of a pantry item to the views.
         *
         * @param ingredient The {@link Ingredient} object containing the data to display.
         */
        public void bind(Ingredient ingredient) {
            binding.itemName.setText(ingredient.getName());
            binding.itemQty.setText(String.valueOf(ingredient.getQuantity()));
        }
    }
}
