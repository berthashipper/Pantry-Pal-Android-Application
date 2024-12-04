package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;

public interface IAddRecipeIngredientView {
    /**
     * Listener interface for handling ingredient editing events.
     */
    interface Listener {
        void onAddRecipeIngredient(String name, double newQty,String unit, Set<Ingredient.dietary_tags> tags);
        void onAddRecipeDone();
        void onBackToRecipe();
    }
}
