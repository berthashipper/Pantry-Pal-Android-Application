package com.example.pantrypalandroidprototype.view;

import android.view.View;

/**
 * Interface that defines the methods for a view that allows users to search for recipes.
 */
public interface ISearchRecipeView {

    /**
     * Listener interface for handling recipe search events.
     */
    interface Listener {

        /**
         * Initiates a search for recipes based on the provided query.
         *
         * @param query The search query entered by the user.
         */
        void onSearchRecipe(String query);

        /**
         * Handles the event when the recipe search operation is complete.
         */
        void onSearchDone();
    }
}