package com.example.pantrypalandroidprototype.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.IAddIngredientView;
import com.example.pantrypalandroidprototype.view.IMainView;
import com.example.pantrypalandroidprototype.view.IPantryView;
import com.example.pantrypalandroidprototype.view.MainView;
import com.example.pantrypalandroidprototype.view.PantryFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IAddIngredientView {

    private IMainView mainView; // Keep track of the main UI object
    private Pantry pantry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize main view and display the AddIngredientFragment
        this.mainView = new MainView(this, this);
        setContentView(this.mainView.getRootView());

        pantry = new Pantry();

        // Only replace if fragment is not already present
        if (getSupportFragmentManager().findFragmentByTag(AddIngredientFragment.class.getSimpleName()) == null) {
            AddIngredientFragment addIngredientFragment = AddIngredientFragment.newInstance((AddIngredientFragment.Listener) this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, addIngredientFragment, AddIngredientFragment.class.getSimpleName()) // Tag added here
                    .commit();
        }
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
        // Once the user clicks "Done", we pass the updated pantry to the PantryFragment
        PantryFragment pantryFragment = new PantryFragment();

        // Pass the pantry to the PantryFragment
        Bundle bundle = new Bundle();
        pantryFragment.setArguments(bundle);

        // Replace the current fragment with the PantryFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, pantryFragment, PantryFragment.class.getSimpleName()) // Tag added here
                .commit();

        Toast.makeText(this, "Items Done, returning to Pantry", Toast.LENGTH_SHORT).show();
        updatePantryDisplay(pantry);
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
        return this.mainView.getRootView();
    }
}
