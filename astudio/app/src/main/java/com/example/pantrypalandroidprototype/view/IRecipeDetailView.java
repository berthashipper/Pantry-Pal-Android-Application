package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

public interface IRecipeDetailView {
    interface Listener {
        void onDoneViewingRecipe();
        void onEditRecipeIngredients();
        void onAddRecipeIngredients();
        void onDeleteRecipeIngredients();
        void onEditInstructions();
        void onScaleRecipeMenu();
    }
}
