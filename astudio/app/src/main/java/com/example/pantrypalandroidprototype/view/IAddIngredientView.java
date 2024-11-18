package com.example.pantrypalandroidprototype.view;

import android.view.View;

import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.Set;

public interface IAddIngredientView {

    interface Listener {
        void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);

        void onItemsDone();
    }
}