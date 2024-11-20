package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

public interface IAddRecipeView {

    interface Listener {
        void onRecipeCreated(Recipe recipe);
    }

}
