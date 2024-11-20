package com.example.pantrypalandroidprototype.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.GenerateRecipe;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.CookbookFragment;
import com.example.pantrypalandroidprototype.view.DeleteIngredientFragment;
import com.example.pantrypalandroidprototype.view.EditIngredientFragment;
import com.example.pantrypalandroidprototype.view.IAddIngredientView;
import com.example.pantrypalandroidprototype.view.ICookbookView;
import com.example.pantrypalandroidprototype.view.IDeleteIngredientView;
import com.example.pantrypalandroidprototype.view.IEditIngredientView;
import com.example.pantrypalandroidprototype.view.IMainView;
import com.example.pantrypalandroidprototype.view.IPantryView;
import com.example.pantrypalandroidprototype.view.MainView;
import com.example.pantrypalandroidprototype.view.PantryFragment;
import com.example.pantrypalandroidprototype.view.RecipeDetailFragment;
import com.example.pantrypalandroidprototype.view.RecipeFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IPantryView.Listener,
        IDeleteIngredientView.Listener, IEditIngredientView.Listener, ICookbookView.Listener {

    IMainView mainView;
    Pantry pantry;
    Set<Recipe> recipes = new HashSet<>();

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.mainView = new MainView(this, this);

        setContentView(this.mainView.getRootView());

        pantry = new Pantry();

        mainView.setListener(this);


        this.mainView.displayFragment(AddIngredientFragment.newInstance(this));
        this.mainView.displayFragment(DeleteIngredientFragment.newInstance(this));
        this.mainView.displayFragment(CookbookFragment.newInstance(this));
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
        // Pass the pantry data to the fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));

        Toast.makeText(this, "Done adding ingredients, returning to Pantry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddIngredientsMenu(){
        AddIngredientFragment addIngredientFragment = AddIngredientFragment.newInstance(this);
        this.mainView.displayFragment(addIngredientFragment);

    }

    @Override
    public void onViewPantryMenu(){
        mainView.setListener(this);
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onDeleteIngredient(String name) {
        boolean isDeleted = pantry.delete_ingredient(name);
        if (isDeleted) {
            Toast.makeText(this, "Deleted ingredient: " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No ingredient found with name: " + name, Toast.LENGTH_SHORT).show();
        }
        onViewPantryMenu(); // Return to the pantry view
    }

    @Override
    public void onDeletionDone() {
        // Pass the pantry data to the fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));

        Toast.makeText(this, "Done deleting ingredients, returning to Pantry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteIngredientsMenu() {
        DeleteIngredientFragment deleteIngredientFragment = DeleteIngredientFragment.newInstance(this);
        this.mainView.displayFragment(deleteIngredientFragment);
    }

    @Override
    public void onEditIngredient(String name, double newQty) {
        boolean isUpdated = pantry.edit_ingredient(name, newQty); // Assume `editIngredient` exists in `Pantry`
        if (isUpdated) {
            Toast.makeText(this, "Updated ingredient: " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No ingredient found with name: " + name, Toast.LENGTH_SHORT).show();
        }
        onViewPantryMenu(); // Return to pantry view
    }

    @Override
    public void onEditDone() {
        mainView.displayFragment(PantryFragment.newInstance(this, pantry));
        Toast.makeText(this, "Done editing ingredients, returning to Pantry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditIngredientsMenu() {
        EditIngredientFragment editIngredientFragment = EditIngredientFragment.newInstance(this);
        mainView.displayFragment(editIngredientFragment);
    }

    public void onGenerateRecipeClicked(View view) {
        // Assuming the user has a list of recipes to check against the pantry
        Set<Recipe> allRecipes = getAllRecipes();

        // Create the GenerateRecipe instance
        GenerateRecipe generateRecipe = new GenerateRecipe(pantry, allRecipes);

        // Call the method to generate matching recipes
        generateRecipe.generateMatchingRecipes();
    }

    @Override
    public void onViewCookbookMenu() {
        mainView.setListener(this);
        this.mainView.displayFragment(CookbookFragment.newInstance(this));
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        this.mainView.displayFragment(recipeDetailFragment);
    }

    private Set<Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public void onGenerateRecipes() {
        Set<Recipe> matchedRecipes = generateMatchingRecipes();
        if (!matchedRecipes.isEmpty()) {
            // Pass matched recipes to a new fragment or adapter
            RecipeFragment recipeFragment = RecipeFragment.newInstance(new ArrayList<>(matchedRecipes));
            mainView.displayFragment(recipeFragment);
        } else {
            Toast.makeText(this, "No matching recipes found.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get matched recipes
    public Set<Recipe> generateMatchingRecipes() {
        Log.d("ControllerActivity", "Recipes available: " + recipes.size());
        GenerateRecipe recipeGenerator = new GenerateRecipe(pantry, recipes);
        return recipeGenerator.generateMatchingRecipes();
    }

    @Override
    public void onCookbookRecipesLoaded(Set<Recipe> recipes) {
        this.recipes = recipes; // Store the loaded recipes
    }
}

