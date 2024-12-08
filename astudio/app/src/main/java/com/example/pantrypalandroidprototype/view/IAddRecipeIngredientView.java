package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;

/**
 * Interface for the view responsible for adding ingredients to a recipe.
 * This interface defines the necessary methods for interacting with the UI
 * and handling ingredient addition events.
 */
public interface IAddRecipeIngredientView {
    /**
     * Listener interface for handling ingredient editing events.
     * This interface defines the methods that are called in response to user actions
     * regarding the addition and modification of recipe ingredients.
     */
    interface Listener {
        /**
         * Called when a new ingredient is added to the recipe.
         *
         * @param name The name of the ingredient.
         * @param newQty The quantity of the ingredient.
         * @param unit The unit of measurement for the ingredient.
         * @param tags The dietary tags associated with the ingredient.
         */
        void onAddRecipeIngredient(String name, double newQty,String unit, Set<Ingredient.dietary_tags> tags);

        /**
         * Called when the user has completed adding ingredients to the recipe.
         */
        void onAddRecipeDone();

        /**
         * Called when the user decides to go back to the recipe view.
         */
        void onBackToRecipe();
    }
}
