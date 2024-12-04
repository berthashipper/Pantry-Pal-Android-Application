package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

/**
 * The {@code IAddRecipeView} interface defines the contract for views that handle the creation and saving of a recipe.
 * It provides methods to save a recipe and an inner interface, {@link IAddRecipeView.Listener}, for notifying when
 * a recipe has been successfully created.
 */
public interface IAddRecipeView {
    /**
     * Called to save the recipe.
     * This method is typically triggered when the user has completed creating a recipe and wishes to save it.
     * The implementation should handle the logic for saving the recipe to a database, file, or other storage mechanism.
     */
    void onSaveRecipe();

    /**
     * The {@code Listener} interface defines the method for notifying the presenter or controller when a recipe is created.
     * Implementations of this interface are responsible for responding to the event when a recipe has been successfully created.
     */
    interface Listener {
        /**
         * Called when a new recipe is successfully created.
         * The listener implementation will handle the new recipe, such as saving it or displaying it in a list.
         *
         * @param recipe The recipe that has been created.
         */
        void onRecipeCreated(Recipe recipe);
        void onNavigateToCookbook();
    }

}
