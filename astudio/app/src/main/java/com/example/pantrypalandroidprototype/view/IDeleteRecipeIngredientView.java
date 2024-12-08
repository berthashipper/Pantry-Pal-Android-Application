package com.example.pantrypalandroidprototype.view;
public interface IDeleteRecipeIngredientView {

    /**
     * Listener interface for handling ingredient deleting events.
     */
    interface Listener {

        /**
         * Called when an ingredient is deleted from a recipe.
         *
         * @param name The name of the ingredient to be deleted.
         */
        void onDeleteRecipeIngredient(String name);

        /**
         * Called when the recipe deletion process is completed.
         */
        void onDeleteRecipeDone();

        /**
         * Called when the user decides to go back to the recipe view without deleting any ingredient.
         */
        void onBackToRecipe();
    }
}
