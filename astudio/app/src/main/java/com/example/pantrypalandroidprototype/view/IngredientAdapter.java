package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.databinding.ItemIngredientBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.List;

/**
 * The {@code IngredientAdapter} class is a custom adapter for displaying a list of ingredients
 * in a {@link RecyclerView}. Each item in the RecyclerView represents an ingredient, showing
 * its name and allowing interaction for managing dietary tags.
 * <p>
 * This adapter handles the binding of ingredient data to views and provides functionality
 * to manage and display dietary tags through a dialog.
 * </p>
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    /**
     * List of {@link Ingredient} objects to be displayed in the RecyclerView.
     */
    final List<Ingredient> ingredientList;

    /**
     * Context for inflating views and creating dialogs.
     */
    final Context context;


    /**
     * Constructs an {@code IngredientAdapter} with the specified ingredient list and context.
     *
     * @param ingredientList The list of ingredients to display.
     * @param context        The context used for inflating views and creating dialogs.
     */
    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    /**
     * Inflates the view for an individual ingredient item and creates a new {@link IngredientViewHolder}.
     *
     * @param parent   The parent view group into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new {@link IngredientViewHolder} holding the inflated view.
     */
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IngredientViewHolder(binding);
    }

    /**
     * Binds the data of the ingredient at the specified position to the {@link IngredientViewHolder}.
     *
     * @param holder   The {@link IngredientViewHolder} to bind data to.
     * @param position The position of the ingredient in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.binding.ingredientName.setText(ingredient.getName());
    }

    /**
     * Returns the total number of ingredients in the list.
     *
     * @return The number of ingredients in the list, or 0 if the list is null.
     */
    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    /**
     * The {@code IngredientViewHolder} class holds references to the views for each ingredient item.
     */
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        ItemIngredientBinding binding;
        /**
         * Constructs an {@code IngredientViewHolder} and initializes its views.
         *
         * @param binding The binding representing an individual ingredient item.
         */
        public IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
