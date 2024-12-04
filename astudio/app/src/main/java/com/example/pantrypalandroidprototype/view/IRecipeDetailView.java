package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

public interface IRecipeDetailView {
    // Delegate the logic of saving and updating to the Controller
    void onDialogEditDone(int requestCode, String newValue);

    interface Listener {
        void onDoneViewingRecipe();
        void onEditRecipeIngredients();
        void onAddRecipeIngredients();
        void onDeleteRecipeIngredients();
        void onEditInstructions();
        void onScaleRecipeMenu();
    }
}
