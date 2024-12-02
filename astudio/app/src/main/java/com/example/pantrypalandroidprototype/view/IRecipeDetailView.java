package com.example.pantrypalandroidprototype.view;

public interface IRecipeDetailView {
    interface Listener {
        void onDoneViewingRecipe();
        void onEditIngredients();
        void onAddIngredients();
        void onEditInstructions();
        void onDeleteIngredients();
    }
}
