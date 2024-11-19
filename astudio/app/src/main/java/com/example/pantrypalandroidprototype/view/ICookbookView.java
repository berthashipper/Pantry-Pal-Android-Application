package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.util.Set;

public interface ICookbookView {

    interface Listener {
        void onViewCookbookMenu();
        void onRecipeClick(Recipe recipe);
    }
}
