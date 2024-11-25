package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.util.Set;

public interface ICookbookView {

    void onRecipeCreated(Recipe recipe);

    void onSearchRecipesMenu();

    void onCookbookRecipesLoaded(Set<Recipe> recipes);

    interface Listener {
        void onViewCookbookMenu();
        void onRecipeClick(Recipe recipe);
        void onCookbookRecipesLoaded(Set<Recipe> recipes);
        void onNavigateToAddRecipe();
        void onSearchRecipesMenu();
        void onRecipeCreated(Recipe recipe);
    }
}