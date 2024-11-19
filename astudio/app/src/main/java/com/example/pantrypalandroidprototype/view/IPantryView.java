package com.example.pantrypalandroidprototype.view;

import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.List;
import java.util.Set;

public interface IPantryView {

    interface Listener {
        void onAddIngredientsMenu();
        void onDeleteIngredientsMenu();
        void onViewPantryMenu();
    }

}
