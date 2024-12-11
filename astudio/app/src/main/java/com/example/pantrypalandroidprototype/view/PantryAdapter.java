package com.example.pantrypalandroidprototype.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.databinding.ItemIngredientBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;

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
    PantryViewHolder.OnItemClickListener listener;
    List<Ingredient> ingredients;


    public PantryAdapter(List<Ingredient> ingredients, PantryViewHolder.OnItemClickListener listener) {
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
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        Ingredient ingredient = ingredients.get(position);
        holder.binding.ingredientName.setText(ingredient.getName());
        holder.binding.ingredientDetails.setText("Amount: " + ingredient.getQuantity() + " " + ingredient.getUnit() + ", Tags: " + ingredient.getTags());

        // Show ingredient details when card is clicked
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.ingredientDetails.setVisibility(View.VISIBLE);
        });

        // Set onClickListener for the edit icon
        holder.binding.editIcon.setOnClickListener(v -> {
            if (listener != null) listener.onEditIngredient(ingredient);
        });

        // Set onClickListener for the delete icon
        holder.binding.deleteIcon.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteIngredient(ingredient);
            }
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
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        ItemIngredientBinding binding;

        /**
         * Constructs a {@code PantryViewHolder} and initializes its views.
         *
         * @param binding The binding representing an individual pantry item.
         */
        public PantryViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public interface OnItemClickListener {
            void onEditIngredient(Ingredient ingredient);
            void onDeleteIngredient(Ingredient ingredient);
        }
    }
}