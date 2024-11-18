package com.example.pantrypalandroidprototype.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import java.util.Set;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IPantryView.Listener {

    private IMainView mainView;
    private Pantry pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new MainView(this);
        setContentView(this.mainView.getRootView());

        pantry = new Pantry();

        // Pass the add ingredient data to the fragment
        this.mainView.displayFragment(AddIngredientFragment.newInstance(this));

        // Pass the pantry data to the fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags) {
        // Convert Set<Ingredient.dietary_tags> to Set<String>
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.name()); // Converts enum to String
        }

        Ingredient newIngredient = new Ingredient(name, qty, unit, tagStrings);
        pantry.add_ingredient(newIngredient);

        // Update pantry view
       // updatePantryDisplay();
    }

    @Override
    public void onItemsDone() {
        PantryFragment pantryFragment = PantryFragment.newInstance(this, this.pantry);
        this.mainView.displayFragment(pantryFragment);

        Toast.makeText(this, "Items Done, returning to Pantry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddIngredientsMenu(){
        AddIngredientFragment addIngredientFragment = AddIngredientFragment.newInstance(this);
        this.mainView.displayFragment(addIngredientFragment);

    }
}
