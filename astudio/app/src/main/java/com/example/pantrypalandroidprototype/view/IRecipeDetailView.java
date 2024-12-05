package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.util.List;
import java.util.Set;

public interface IRecipeDetailView {
    // Delegate the logic of saving and updating to the Controller
    void onDialogEditDone(int requestCode, String newValue);

    interface Listener {
        void onDoneViewingRecipe();
        void onEditRecipeIngredients();
        void onAddRecipeIngredients();
        void onDeleteRecipeIngredients();
        void onScaleRecipeMenu();
        void onEditInstructions(String currentInstructions);
        void shopFor(Set<Ingredient> ingredients);
    }
}
