package com.example.pantrypalandroidprototype.view;

import android.view.View;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

public interface IGroceryListView {

    interface Listener {
        void onClearShoppingList();
        void onCheckout();
        void onRemoveIngredient(Ingredient ingredient);
        //void onViewShoppingListMenu(View view);
    }
}
