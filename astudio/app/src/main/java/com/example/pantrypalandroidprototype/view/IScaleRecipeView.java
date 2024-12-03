package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

public interface IScaleRecipeView {

    interface Listener {
        void onScaleRecipe(double scaleFactor);
        void onRecipeScaled(Recipe scaledRecipe);
        void onBackToRecipe();
    }
}