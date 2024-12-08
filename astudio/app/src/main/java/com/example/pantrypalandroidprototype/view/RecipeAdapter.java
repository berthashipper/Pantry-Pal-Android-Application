package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.ItemRecipeBinding;
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.util.ArrayList;

/**
 * The {@code RecipeAdapter} class is a custom adapter for displaying a list of recipes
 * in a {@link RecyclerView}. It handles binding recipe data from a {@link Cookbook} to views
 * and provides click handling through an interface.
 * <p>
 * This adapter dynamically displays recipes and updates the list based on changes in the {@code Cookbook}.
 * </p>
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    /**
     * The {@link Cookbook} object containing the recipes to be displayed.
     */
    public Cookbook cookbook;

    //public List<Recipe> recipes;
    /**
     * The {@link Context} in which the adapter is being used.
     */
    public Context context;
    /**
     * Interface to handle recipe item click events.
     */
    public OnRecipeClickListener onRecipeClickListener;
    public OnDeleteRecipeListener onDeleteRecipeListener;

    /**
     * Constructs a {@code RecipeAdapter} with the specified cookbook, context, and click listener.
     *
     * @param cookbook               The {@link Cookbook} containing the list of recipes.
     * @param context                The context in which the adapter will be used.
     * @param onRecipeClickListener  The listener for handling recipe item clicks.
     */
    public RecipeAdapter(Cookbook cookbook, Context context, OnRecipeClickListener onRecipeClickListener,
                         OnDeleteRecipeListener deleteListener) {
        this.cookbook = cookbook;
        this.context = context;
        this.onRecipeClickListener = onRecipeClickListener;
        this.onDeleteRecipeListener = deleteListener;
    }

    /**
     * Inflates the view for an individual recipe item and creates a new {@link RecipeViewHolder}.
     *
     * @param parent    The parent view group into which the new view will be added.
     * @param viewType  The view type of the new view.
     * @return A new {@link RecipeViewHolder} holding the inflated view.
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RecipeViewHolder(binding);
    }

    /**
     * Binds the data of the recipe at the specified position to the {@link RecipeViewHolder}.
     *
     * @param holder    The {@link RecipeViewHolder} to bind data to.
     * @param position  The position of the recipe item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = new ArrayList<>(cookbook.recipeList.values()).get(position);
        // Bind data
        holder.binding.recipeName.setText(recipe.recipeName);

        holder.binding.getRoot().setOnClickListener(v -> onRecipeClickListener.onRecipeClick(recipe));
        holder.binding.deleteIcon.setOnClickListener(v -> onDeleteRecipeListener.onDeleteRecipe(recipe));
    }

    /**
     * Returns the total number of recipes in the cookbook.
     *
     * @return The number of recipes in the cookbook.
     */
    @Override
    public int getItemCount() {
        return cookbook.recipeList.size();
    }

    /**
     * Updates the adapter with a new cookbook and refreshes the displayed data.
     *
     * @param newCookbook The new {@link Cookbook} containing updated recipes.
     */
    public void updateRecipes(Cookbook newCookbook) {
        this.cookbook = newCookbook;
        notifyDataSetChanged();
    }

    /**
     * Sets the listener for recipe click events.
     *
     * @param listener The listener to be notified when a recipe is clicked.
     */
    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.onRecipeClickListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when a recipe is clicked.
     */
    public interface OnRecipeClickListener {
        /**
         * Called when a recipe item is clicked.
         *
         * @param recipe The {@link Recipe} that was clicked.
         */
        void onRecipeClick(Recipe recipe);
    }

    /**
     * Listener interface for handling recipe deletion events.
     */
    public interface OnDeleteRecipeListener {
        /**
         * Called when a recipe is to be deleted.
         *
         * @param recipe The recipe to be deleted.
         */
        void onDeleteRecipe(Recipe recipe);
    }

    /**
     * The {@code RecipeViewHolder} class holds references to the views for each recipe item.
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        ItemRecipeBinding binding;
        /**
         * Constructs a {@code RecipeViewHolder} and initializes its views.
         *
         * @param binding The binding object representing the individual recipe item.
         */
        public RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * Removes a recipe from the cookbook and updates the UI.
     *
     * @param recipe The recipe to be removed.
     */
    public void removeRecipe(Recipe recipe) {
        cookbook.removeRecipe(recipe);
        notifyDataSetChanged();
    }
}