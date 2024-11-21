package com.example.pantrypalandroidprototype.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.GenerateRecipe;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.AddRecipeFragment;
import com.example.pantrypalandroidprototype.view.CookbookFragment;
import com.example.pantrypalandroidprototype.view.DeleteIngredientFragment;
import com.example.pantrypalandroidprototype.view.EditIngredientFragment;
import com.example.pantrypalandroidprototype.view.IAddIngredientView;
import com.example.pantrypalandroidprototype.view.IAddRecipeView;
import com.example.pantrypalandroidprototype.view.ICookbookView;
import com.example.pantrypalandroidprototype.view.IDeleteIngredientView;
import com.example.pantrypalandroidprototype.view.IEditIngredientView;
import com.example.pantrypalandroidprototype.view.IMainView;
import com.example.pantrypalandroidprototype.view.IPantryView;
import com.example.pantrypalandroidprototype.view.IRecipeDetailView;
import com.example.pantrypalandroidprototype.view.ISearchRecipeView;
import com.example.pantrypalandroidprototype.view.MainView;
import com.example.pantrypalandroidprototype.view.PantryFragment;
import com.example.pantrypalandroidprototype.view.RecipeDetailFragment;
import com.example.pantrypalandroidprototype.view.RecipeFragment;
import com.example.pantrypalandroidprototype.view.SearchRecipeFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IPantryView.Listener,
        IDeleteIngredientView.Listener, IEditIngredientView.Listener,
        ICookbookView.Listener, IAddRecipeView.Listener, IRecipeDetailView.Listener, ISearchRecipeView.Listener {

    IMainView mainView;
    Pantry pantry;
    Set<Recipe> recipes = new HashSet<>();

    public static final int REQUEST_CODE_ADD_TO_COOKBOOK = 1;

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
        this.mainView.displayFragment(CookbookFragment.newInstance(this, CookbookFragment.getAllRecipes()));
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

    @Override
    public void onViewCookbookMenu() {
        mainView.setListener(this);
        this.mainView.displayFragment(CookbookFragment.newInstance(this, CookbookFragment.getAllRecipes()));
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        this.mainView.displayFragment(recipeDetailFragment);
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

    @Override
    public void onNavigateToAddRecipe() {
        // Navigate to AddRecipeFragment
        AddRecipeFragment addRecipeFragment = AddRecipeFragment.newInstance((AddRecipeFragment.Listener) this);
        mainView.displayFragment(addRecipeFragment);
    }

    @Override
    public void onRecipeCreated(Recipe recipe) {
        // Add the recipe to the set of recipes
        recipes.add(recipe);

        // Display the recipe in the RecipeDetailFragment
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        mainView.displayFragment(recipeDetailFragment);

        // Update the CookbookFragment to include the new recipe
        updateCookbookFragment();
    }


    public void updateCookbookFragment() {
        // Update CookbookFragment with the new list of recipes
        this.mainView.displayFragment(CookbookFragment.newInstance(this, new HashSet<>(recipes)));
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_TO_COOKBOOK && resultCode == RESULT_OK && data != null) {
            Recipe recipe = (Recipe) data.getSerializableExtra("recipe");
            if (recipe != null) {
                onRecipeCreated(recipe);
            }
        }
    }

    @Override
    public void onDoneViewingRecipe() {
        // After viewing the recipe details, return to the CookbookFragment
        mainView.displayFragment(CookbookFragment.newInstance(this, new HashSet<>(recipes)));
    }

    @Override
    public void onSearchRecipe(String query) {
        // Filter recipes based on the query
        Set<Recipe> filteredRecipes = recipes.stream()
                .filter(recipe -> recipe.getRecipeName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toSet());

        if (!filteredRecipes.isEmpty()) {
            // Display filtered recipes
            RecipeFragment recipeFragment = RecipeFragment.newInstance(new ArrayList<>(filteredRecipes));
            mainView.displayFragment(recipeFragment);
        } else {
            Toast.makeText(this, "No recipes found matching: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSearchDone() {
        // Navigate back to the Cookbook view or another fragment
        mainView.displayFragment(CookbookFragment.newInstance(this, new HashSet<>(recipes)));
    }

    @Override
    public void onSearchRecipesMenu() {
        SearchRecipeFragment searchRecipeFragment = SearchRecipeFragment.newInstance(this);
        mainView.displayFragment(searchRecipeFragment);
    }
}