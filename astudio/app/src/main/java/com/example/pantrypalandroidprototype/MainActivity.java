package com.example.pantrypalandroidprototype;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.PantryFragment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddIngredientFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PantryFragment pantryFragment = new PantryFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, pantryFragment)
                    .commit();
        }
    }

    @Override
    public void onAddIngredients(List<Ingredient> ingredients) {
        // Get the PantryFragment and update its pantry with the new ingredients
        PantryFragment pantryFragment = (PantryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (pantryFragment != null) {
            pantryFragment.updatePantry(ingredients);
        }
    }

    // Implement the onAddIngredient method from the Listener interface
    @Override
    public void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.toString());
        }

        Ingredient newIngredient = new Ingredient(name, qty, unit, tagStrings);

        PantryFragment pantryFragment = (PantryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        if (pantryFragment != null) {
            pantryFragment.addIngredientToPantry(newIngredient);
        }
    }

    // Implement the onItemsDone method from the Listener interface
    @Override
    public void onItemsDone() {
        // Handle when items are done (for example, return to the pantry view)
        PantryFragment pantryFragment = (PantryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (pantryFragment != null) {
        }
    }
}
