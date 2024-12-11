package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;
/**
 * Interface that defines the methods for a view displaying recipe details.
 */
public interface IRecipeDetailView {

    /**
     * Handles the completion of an edit dialog.
     *
     * @param requestCode The code representing the type of edit performed.
     * @param newValue    The updated value from the edit dialog.
     */
    void onDialogEditDone(int requestCode, String newValue);

    /**
     * Listener interface for handling user interactions and events related to recipe details.
     */
    interface Listener {

        /**
         * Handles the event when the user is done viewing a recipe.
         */
        void onDoneViewingRecipe();

        /**
         * Opens the menu to edit the ingredients of the recipe.
         */
        void onEditRecipeIngredients();

        /**
         * Opens the menu to add new ingredients to the recipe.
         */
        void onAddRecipeIngredients();

        /**
         * Opens the menu to delete ingredients from the recipe.
         */
        void onDeleteRecipeIngredients();

        /**
         * Opens the menu to scale the recipe by a specified factor.
         */
        void onScaleRecipeMenu();

        /**
         * Opens the edit menu for the recipe instructions.
         *
         * @param currentInstructions The current instructions for the recipe.
         */
        void onEditInstructions(String currentInstructions);

        /**
         * Initiates the process of shopping for a set of ingredients.
         *
         * @param ingredients The set of ingredients to be added to the shopping list.
         */
        void shopFor(Set<Ingredient> ingredients);
    }
}
