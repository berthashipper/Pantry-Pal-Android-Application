package com.example.pantrypalandroidprototype.view;

import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.List;
import java.util.Set;

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
        void onClearPantry();
    }
}
