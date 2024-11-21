package com.example.pantrypalandroidprototype.view;

public interface ISearchRecipeView {

    interface Listener {
        void onSearchRecipe(String query);
        void onSearchDone();
    }
}
