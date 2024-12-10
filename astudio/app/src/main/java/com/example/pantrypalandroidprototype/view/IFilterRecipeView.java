package com.example.pantrypalandroidprototype.view;

public interface IFilterRecipeView {

    /**
     * Listener interface for handling recipe filtering events.
     */
    interface Listener {
        /**
         * Filters recipes based on the specified criteria.
         *
         * @param dietaryTag The selected dietary preference.
         */
        void onFilterRecipes(String dietaryTag);
        /**
         * Called when the user decides to go back to the Cookbook view.
         */
        void onNavigateToCookbook();
    }
}

