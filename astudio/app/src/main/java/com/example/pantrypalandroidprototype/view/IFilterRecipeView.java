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
    }
}

