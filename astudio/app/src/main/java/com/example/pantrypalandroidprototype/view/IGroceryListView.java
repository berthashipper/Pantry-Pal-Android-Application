package com.example.pantrypalandroidprototype.view;

import android.view.View;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

public interface IGroceryListView {
    void onEditIngredientGroceryList(Ingredient ingredient);
    interface Listener {
        void onAddIngredientsToGroceryListMenu();
        void onEditIngredientGroceryList(Ingredient ingredient);
        void onClearShoppingList();
        void onRemoveIngredient(Ingredient ingredient);
    }
}
