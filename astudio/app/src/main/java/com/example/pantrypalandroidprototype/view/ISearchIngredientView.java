package com.example.pantrypalandroidprototype.view;
/**
 * Interface that defines the methods for a view allowing the user to search for ingredients.
 */
public interface ISearchIngredientView {

    /**
     * Listener interface for handling ingredient search events.
     */
    interface Listener {

        /**
         * Initiates a search for ingredients based on the provided query.
         *
         * @param query The search query entered by the user.
         */
        void onSearchIngredient(String query);

        /**
         * Handles the event when the ingredient search operation is complete.
         */
        void onSearchIngredientDone();
    }
}
