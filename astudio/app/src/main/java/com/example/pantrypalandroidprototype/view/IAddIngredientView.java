package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;

/**
 * The {@code IAddIngredientView} interface defines the contract for views that handle adding ingredients to a pantry.
 * It provides an inner interface, {@link IAddIngredientView.Listener}, for communicating with the presenter or controller
 * responsible for managing the ingredient addition logic.
 */
public interface IAddIngredientView {

    /**
     * The {@code Listener} interface defines methods for handling actions related to adding an ingredient.
     * Implementations of this interface are responsible for responding to user interactions in the {@link IAddIngredientView}.
     */
    interface Listener {
        /**
         * Called when the user adds a new ingredient.
         * This method is used to pass the ingredient details (name, quantity, unit, and dietary tags)
         * to the presenter or controller for processing.
         *
         * @param name The name of the ingredient to be added.
         * @param qty  The quantity of the ingredient to be added.
         * @param unit The unit of measurement for the ingredient (e.g., "kg", "g", "cup").
         * @param tags A set of dietary tags associated with the ingredient, such as "Vegan", "Gluten-Free", etc.
         * @return
         */
        boolean onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);

        /**
         * Called when the user finishes adding ingredients and is done with the process.
         * This method is typically used to notify the controller or presenter that the ingredient addition process is complete.
         */
        void onItemsDone();
    }
}