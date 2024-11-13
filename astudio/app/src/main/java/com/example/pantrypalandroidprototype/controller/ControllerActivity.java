package com.example.pantrypalandroidprototype.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.IAddIngredientView;
import com.example.pantrypalandroidprototype.view.IMainView;
import com.example.pantrypalandroidprototype.view.IPantryView;
import com.example.pantrypalandroidprototype.view.MainView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IAddIngredientView {

    private IMainView mainView; // Keep track of the main UI object
    private Pantry pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create main screen template
        this.mainView = new MainView(this, this);
        setContentView(this.mainView.getRootView()); // Must be called from controller

        // Initialize the pantry
        pantry = new Pantry();

        // Display the AddIngredientFragment for the user to add items
        AddIngredientFragment addIngredientFragment = AddIngredientFragment.newInstance((AddIngredientFragment.Listener) this);
        this.mainView.displayFragment(addIngredientFragment); // Display the AddIngredientFragment
    }

    @Override
    public void onAddIngredient(final String name, final double qty, final String unit, @NonNull final Set<Ingredient.dietary_tags> tags) {
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.toString()); // Converts each dietary_tag to its string representation
        }

        // Create new Ingredient object and add it to the pantry
        Ingredient newIngredient = new Ingredient(name, qty, unit, tagStrings);
        pantry.add_ingredient(newIngredient);

        // Update the pantry display (show how many items are in the pantry)
        updatePantryDisplay(pantry);
    }

    @Override
    public void onItemsDone() {
        // Add logic here for handling "items done" event
        Toast.makeText(this, "Items Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        // Get the list of ingredients from the pantry
        List<Ingredient> ingredients = new ArrayList<>(pantry.ingredientList.values());

        // Update the RecyclerView adapter with the list of pantry items
        this.mainView.getPantryAdapter().updatePantryItems(ingredients);
    }

    @Override
    public View getRootView() {
        // You can return the root view of your activity here if needed.
        return this.mainView.getRootView();
    }
}
