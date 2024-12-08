package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;
/**
 * Interface that defines the methods for a view allowing the user to scale a recipe.
 */
public interface IScaleRecipeView {

    /**
     * Listener interface for handling recipe scaling events.
     */
    interface Listener {

        /**
         * Scales the recipe by the specified factor.
         *
         * @param scaleFactor The factor by which to scale the recipe.
         */
        void onScaleRecipe(double scaleFactor);

        /**
         * Handles the event when the recipe has been successfully scaled.
         *
         * @param scaledRecipe The recipe after it has been scaled.
         */
        void onRecipeScaled(Recipe scaledRecipe);

        /**
         * Handles the event when the user wants to return to the recipe details.
         */
        void onBackToRecipe();
    }
}
