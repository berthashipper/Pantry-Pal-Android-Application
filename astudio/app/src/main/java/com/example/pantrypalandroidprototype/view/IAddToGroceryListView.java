package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.Set;

public interface IAddToGroceryListView {
    interface Listener {
        void onAddIngredientToGroceryList(String name, double qty);
        void onItemsDone();
    }
}