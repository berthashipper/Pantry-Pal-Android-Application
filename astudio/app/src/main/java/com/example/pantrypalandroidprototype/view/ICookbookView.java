package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.util.Set;

/**
 * The {@code ICookbookView} interface defines the contract for a view that displays a cookbook.
 * It includes methods for managing recipes, searching for recipes, and interacting with the cookbook's data.
 * This interface communicates with the associated presenter or controller to manage the user's actions within the cookbook.
 */
public interface ICookbookView {
    /**
     * Called when a recipe is created.
     * This method will notify the view that a new recipe has been successfully created, and should be reflected in the UI.
     *
     * @param recipe The recipe that has been created.
     */
    void onRecipeCreated(Recipe recipe);

    /**
     * Called when the user triggers the search menu for recipes.
     * This method provides the opportunity to display a search interface or navigate to a search fragment.
     */
    void onSearchRecipesMenu();
    /**
     * Called when the cookbook's recipes are successfully loaded.
     * This method notifies the view that the recipes have been loaded, and the UI should be updated accordingly.
     *
     * @param cookbook The {@link Cookbook} object containing the loaded recipes.
     */
    void onCookbookRecipesLoaded(Cookbook cookbook);
    /**
     * The {@code Listener} interface defines the methods for handling user actions in the cookbook view.
     * It allows the view to notify the controller or presenter about various actions, such as viewing the cookbook,
     * clicking on a recipe, or navigating to the add recipe screen.
     */
    interface Listener {
        /**
         * Called when the user wants to view the cookbook.
         * This method may navigate to the cookbook's main screen or display a list of recipes.
         */
        void onViewCookbookMenu();
        /**
         * Called when a recipe is clicked by the user.
         * The implementation should handle navigating to the recipe details or taking an appropriate action.
         *
         * @param recipe The recipe that was clicked by the user.
         */
        void onRecipeClick(Recipe recipe);
        /**
         * Called when the user wishes to navigate to the add recipe screen.
         * The implementation should handle navigation to a fragment or activity where a user can add a new recipe.
         */
        void onNavigateToAddRecipe();
        /**
         * Called when the user triggers the search menu for recipes.
         * The implementation should handle the search functionality, either by displaying a dialog or navigating to a search fragment.
         */
        void onSearchRecipesMenu();
        /**
         * Called when a new recipe is created and should be reflected in the UI.
         * This method is triggered after the recipe has been created, allowing the presenter or controller to handle it.
         *
         * @param recipe The recipe that has been created.
         */
        void onRecipeCreated(Recipe recipe);

        void onCookbookUpdated(Cookbook cookbook);
    }
}