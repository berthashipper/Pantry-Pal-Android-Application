package com.example.pantrypalandroidprototype.view;

import android.view.View;

public interface ISearchRecipeView {

    interface Listener {
        void onSearchRecipe(String query);
        void onSearchDone();
    }
}
