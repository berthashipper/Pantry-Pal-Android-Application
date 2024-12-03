package com.example.pantrypalandroidprototype.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.ItemPantryBinding;
import com.example.pantrypalandroidprototype.databinding.ItemRecipeBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;

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
    List<String> ingredients;
    PantryViewHolder.OnItemClickListener listener;


    public PantryAdapter(List<String> ingredients, PantryViewHolder.OnItemClickListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

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
        // Inflate the item_pantry layout for each item in the list
        //ItemPantryBinding binding = ItemPantryBinding.inflate(LayoutInflater.from(context), parent, false);
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
        // Get the ingredient name at the current position
        String ingredientName = ingredients.get(position);
        holder.ingredientName.setText(ingredientName);
        holder.deleteIcon.setOnClickListener(v -> {
            // Handle delete action
            listener.onDeleteClick(ingredientName);
        });
    }

    /**
     * Returns the total number of pantry items in the list.
     *
     * @return The number of pantry items in the list.
     */
    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    /**
     * The {@code PantryViewHolder} class holds references to the views for each pantry item.
     */
    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        ImageView deleteIcon;

        /**
         * Constructs a {@code PantryViewHolder} and initializes its views.
         *
         * @param itemView The binding representing an individual pantry item.
         */
        public PantryViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }

        public interface OnItemClickListener {
            void onDeleteClick(String ingredient);
        }
    }
}
