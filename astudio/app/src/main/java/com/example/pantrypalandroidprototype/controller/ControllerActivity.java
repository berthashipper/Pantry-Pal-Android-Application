package com.example.pantrypalandroidprototype.controller;

import android.os.Bundle;

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

/**
 * The controller class for our application.
 */
public class ControllerActivity extends AppCompatActivity implements IAddIngredientView.Listener, IPantryView.Listener {

    private IMainView mainView; // Keep track of the main UI object
    private Pantry pantry;

    /**
     * This method is called by the Android framework whenever the activity is created or recreated.
     */
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

    /* IAddIngredientView.Listener implementation start */
    /**
     * Called when an ingredient is added to the pantry.
     *
     * @param name name of the ingredient to add
     * @param qty  quantity of the ingredient
     * @param unit unit of the ingredient
     * @param tags dietary tags associated with the ingredient
     */
    @Override
    public void onAddIngredient(final String name, final double qty, final String unit, @NonNull final Set<Ingredient.dietary_tags> tags) {
        // Create new Ingredient object and add it to the pantry
        Ingredient newIngredient = new Ingredient(name, qty, unit, tags);
        pantry.add_ingredient(newIngredient);

        // Update the pantry display (show how many items are in the pantry)
        updatePantryDisplay(pantry);
    }

    /* IPantryView.Listener implementation start */
    /**
     * Updates the pantry display with the current pantry data.
     *
     * @param pantry the updated pantry
     */
    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        // Get the list of ingredients from the pantry
        List<Ingredient> ingredients = new ArrayList<>(pantry.ingredientList.values());

        // Update the RecyclerView adapter with the list of pantry items
        this.mainView.getPantryAdapter().updatePantryItems(ingredients);
    }
    /* IPantryView.Listener implementation end */
}
