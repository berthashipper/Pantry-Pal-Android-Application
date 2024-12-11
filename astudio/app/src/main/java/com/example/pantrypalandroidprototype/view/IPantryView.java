package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

/**
 * The {@code IPantryView} interface defines the contract for a view that manages pantry actions.
 * It includes methods for adding, deleting, editing, viewing pantry items, generating recipes,
 * searching for ingredients, and navigating to related menus such as the cookbook.
 * This interface communicates with the associated presenter or controller to manage the user's interactions with the pantry.
 */
public interface IPantryView {
    /**
     * The {@code Listener} interface defines the methods for handling user actions related to pantry management.
     * It allows the view to notify the controller or presenter about various actions, such as adding ingredients,
     * deleting ingredients, editing pantry items, generating recipes, or navigating to different pantry-related menus.
     */
    interface Listener {
        /**
         * Called when the user triggers the add ingredients menu.
         * The implementation should handle navigation to a screen where ingredients can be added to the pantry.
         */
        void onAddIngredientsMenu();

        /**
         * Called when the user triggers the delete ingredients menu.
         * The implementation should handle navigation to a screen where ingredients can be deleted from the pantry.
         */
        void onDeleteIngredientsMenu();

        /**
         * Called when the user triggers the view pantry menu.
         * The implementation should handle displaying the contents of the pantry, such as a list of available ingredients.
         */
        void onViewPantryMenu();

        /**
         * Called when the user triggers the edit ingredients menu.
         * The implementation should handle navigation to a screen where ingredients can be edited in the pantry.
         */
        void onEditIngredientsMenu();

        /**
         * Called when the user triggers the view cookbook menu.
         * The implementation should handle navigating to a screen where the user can view and interact with the cookbook.
         */
        void onViewCookbookMenu();

        /**
         * Called when the user triggers the generate recipes action.
         * The implementation should handle generating recipes based on the available pantry ingredients.
         */
        void onGenerateRecipes();

        /**
         * Called when the user triggers the search ingredients menu.
         * The implementation should handle navigating to a screen where the user can search for specific ingredients in the pantry.
         */
        void onSearchIngredientsMenu();

        /**
         * Clears the pantry, removing all ingredients.
         */
        void onClearPantry();

        /**
         * Opens the edit menu for a specific ingredient, allowing the user to modify its details.
         *
         * @param ingredient The ingredient to be edited.
         */
        void onEditIngredientMenu(Ingredient ingredient); // Open edit menu for ingredient

        /**
         * Handles the deletion of a specific ingredient from the pantry.
         *
         * @param ingredient The ingredient to be deleted.
         */
        void onDeleteIngredientMenu(Ingredient ingredient); // Handle deletion of ingredient

        /**
         * Opens the grocery list menu, allowing the user to view or manage their grocery list.
         */
        void onViewGroceryListMenu();
    }
}
