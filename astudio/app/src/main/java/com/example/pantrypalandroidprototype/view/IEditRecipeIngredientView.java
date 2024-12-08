package com.example.pantrypalandroidprototype.view;


/**
 * Interface that defines the methods for a view that allows a user to edit ingredients in a recipe.
 */
public interface IEditRecipeIngredientView {

    /**
     * Listener interface for handling ingredient editing events.
     */
    interface Listener {

        /**
         * Called when a recipe ingredient is edited.
         *
         * @param name The name of the ingredient.
         * @param newQty The new quantity for the ingredient.
         */
        void onEditRecipeIngredient(String name, double newQty);

        /**
         * Called when the editing of the recipe is completed.
         */
        void onEditRecipeDone();

        /**
         * Called when the user wants to navigate back to the recipe.
         */
        void onBackToRecipe();

        /**
         * Checks if an ingredient exists in the recipe.
         *
         * @param name The name of the ingredient.
         * @return true if the ingredient exists, false otherwise.
         */
        boolean isIngredientExists(String name);
    }
}