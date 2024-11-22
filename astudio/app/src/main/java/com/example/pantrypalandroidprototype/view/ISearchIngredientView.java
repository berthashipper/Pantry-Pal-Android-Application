package com.example.pantrypalandroidprototype.view;

public interface ISearchIngredientView {

    interface Listener {
        void onSearchIngredient(String query);
        void onSearchIngredientDone();
    }
}
