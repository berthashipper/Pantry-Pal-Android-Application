package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;
/**
 * Interface for the view responsible for adding ingredients to the grocery list.
 * This interface defines the necessary methods for interacting with the UI
 * and handling events related to adding ingredients to the grocery list.
 */
public interface IAddToGroceryListView {

    /**
     * Listener interface for handling ingredient addition and completion events.
     * This interface defines the methods that are called when the user adds an ingredient
     * to the grocery list or when they finish adding items.
     */
    interface Listener {

        /**
         * Called when an ingredient is added to the grocery list.
         *
         * @param name The name of the ingredient.
         * @param qty The quantity of the ingredient to be added.
         * @param unit The unit of measurement for the ingredient.
         */
        void onAddIngredientToGroceryList(String name, double qty, String unit);

        /**
         * Called when the user has finished adding items to the grocery list.
         */
        void onItemsDone();
    }
}
