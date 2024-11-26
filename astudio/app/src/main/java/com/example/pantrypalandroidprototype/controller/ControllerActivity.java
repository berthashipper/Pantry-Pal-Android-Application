package com.example.pantrypalandroidprototype.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.GenerateRecipe;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Ledger;
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
import com.example.pantrypalandroidprototype.view.ISearchIngredientView;
import com.example.pantrypalandroidprototype.view.ISearchRecipeView;
import com.example.pantrypalandroidprototype.view.MainView;
import com.example.pantrypalandroidprototype.view.PantryFragment;
import com.example.pantrypalandroidprototype.view.RecipeDetailFragment;
import com.example.pantrypalandroidprototype.view.RecipeFragment;
import com.example.pantrypalandroidprototype.view.SearchIngredientFragment;
import com.example.pantrypalandroidprototype.view.SearchRecipeFragment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.pantrypalandroidprototype.persistence.IPersistenceFacade;
import com.example.pantrypalandroidprototype.persistence.LocalStorageFacade;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IPantryView.Listener,
        IDeleteIngredientView.Listener, IEditIngredientView.Listener,
        ICookbookView.Listener, IAddRecipeView.Listener, IRecipeDetailView.Listener,
        ISearchRecipeView.Listener, ISearchIngredientView.Listener {

    IMainView mainView;
    Pantry pantry;
    Set<Recipe> recipes = new HashSet<>();
    Ledger ledger;
    IPersistenceFacade persFacade; // proxy for persistence subsystem
    Cookbook cookbook;


    public static final int REQUEST_CODE_ADD_TO_COOKBOOK = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.persFacade = new LocalStorageFacade(this); // set up persistence proxy
        this.ledger = this.persFacade.loadLedger(); // load the ledger


        /*FirebaseFirestore db = FirebaseFirestore.getInstance();


        CollectionReference cref = db.collection("users");
        DocumentReference dref = cref.document("user1");

        Log.i("PantryPal", "calling get");
        Task<DocumentSnapshot> task = dref.get();

        task.addOnSuccessListener(new OnSuccessListener<>() {
            @Override
            public void onSuccess(DocumentSnapshot dsnap) {
                String username = (String) dsnap.get("username");
                Log.i("PantryPal", "read username = " + username);
            }
        });*/


        setContentView(R.layout.main);

        this.mainView = new MainView(this, this);

        setContentView(this.mainView.getRootView());

        pantry = new Pantry();
        cookbook = new Cookbook();

        mainView.setListener(this);

        /*this.mainView.displayFragment(AddIngredientFragment.newInstance(this));
        this.mainView.displayFragment(EditIngredientFragment.newInstance(this));
        this.mainView.displayFragment(DeleteIngredientFragment.newInstance(this));
        this.mainView.displayFragment(CookbookFragment.newInstance(this, cookbook));*/
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
    }

    @Override
    public void onItemsDone() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (currentFragment instanceof AddIngredientFragment) {
            AddIngredientFragment addIngredientFragment = (AddIngredientFragment) currentFragment;
            addIngredientFragment.showDoneMessage();
        }
        // Switch to the Pantry fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (currentFragment instanceof DeleteIngredientFragment) {
            DeleteIngredientFragment deleteIngredientFragment = (DeleteIngredientFragment) currentFragment;
            boolean isDeleted = pantry.delete_ingredient(name);
            if (isDeleted) {
                deleteIngredientFragment.showIngredientDeletedMessage(name);
            } else {
                deleteIngredientFragment.showIngredientNotFoundError(name);
            }
        }
        onViewPantryMenu(); // Return to the pantry view
    }

    @Override
    public void onDeletionDone() {
        // Pass the pantry data to the fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onDeleteIngredientsMenu() {
        DeleteIngredientFragment deleteIngredientFragment = DeleteIngredientFragment.newInstance(this);
        this.mainView.displayFragment(deleteIngredientFragment);
    }

    @Override
    public void onEditIngredient(String name, double newQty) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (currentFragment instanceof EditIngredientFragment) {
            EditIngredientFragment editIngredientFragment = (EditIngredientFragment) currentFragment;
            boolean isUpdated = pantry.edit_ingredient(name, newQty);

            if (isUpdated) {
                editIngredientFragment.showIngredientUpdateMessage(name);
            } else {
                editIngredientFragment.showIngredientNotFoundError(name);
            }
        }
        onViewPantryMenu(); // Return to pantry view
    }


    @Override
    public void onEditDone() {
        mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onEditIngredientsMenu() {
        EditIngredientFragment editIngredientFragment = EditIngredientFragment.newInstance(this);
        mainView.displayFragment(editIngredientFragment);
    }

    @Override
    public void onViewCookbookMenu() {
        mainView.setListener(this);
        if (cookbook == null) {
            cookbook = persFacade.loadCookbook(); // Load cookbook from persistence
        }
        this.mainView.displayFragment(CookbookFragment.newInstance(this, cookbook));
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        this.mainView.displayFragment(recipeDetailFragment);
    }

    @Override
    public void onGenerateRecipes() {
        Set<Recipe> matchedRecipes = generateMatchingRecipes();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (!matchedRecipes.isEmpty()) {
            // Pass matched recipes to a new fragment
            RecipeFragment recipeFragment = RecipeFragment.newInstance(new Cookbook(matchedRecipes));
            mainView.displayFragment(recipeFragment);
        } else {
            if (currentFragment instanceof RecipeFragment) {
                // Notify user via the current fragment
                ((RecipeFragment) currentFragment).showNoRecipesMessage();
            } else {
                // Navigate back to RecipeFragment and show the message
                RecipeFragment recipeFragment = RecipeFragment.newInstance(new Cookbook(matchedRecipes));
                mainView.displayFragment(recipeFragment);
                getSupportFragmentManager().executePendingTransactions();
                recipeFragment.showNoRecipesMessage();
            }
        }
    }

    public Set<Recipe> generateMatchingRecipes() {
        Log.d("ControllerActivity", "Recipes available: " + recipes.size());
        GenerateRecipe recipeGenerator = new GenerateRecipe(pantry, recipes);
        //GenerateRecipe recipeGenerator = new GenerateRecipe(pantry, cookbook.getAllRecipes());
        return recipeGenerator.generateMatchingRecipes();
    }

    //@Override
    public void onCookbookUpdated(Cookbook updatedCookbook) {
        this.cookbook = updatedCookbook;
        persFacade.saveCookbook(updatedCookbook);
        updateCookbookFragment();
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
        cookbook.addRecipe(recipe);
        //recipes.add(recipe);
        // Persist changes to the cookbook
        persFacade.saveCookbook(cookbook);

        // Update the view
        updateCookbookFragment();

        // Display the recipe in the RecipeDetailFragment
        //RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        //mainView.displayFragment(recipeDetailFragment);

        // Update the CookbookFragment to include the new recipe
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, CookbookFragment.newInstance(this, cookbook))
                .commit();

        //this.ledger.addRecipe(recipe); // save recipe onto ledger
        //this.persFacade.saveLedger(ledger); // save the ledger to persistent memory
    }


    public void updateCookbookFragment() {
        // Update CookbookFragment with the new list of recipes
        if (cookbook != null) {
            this.mainView.displayFragment(CookbookFragment.newInstance(this, cookbook));
        }
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
        mainView.displayFragment(CookbookFragment.newInstance(this, cookbook));
    }


    @Override
    public void onSearchRecipe(String query) {
        // Filter recipes based on the query
        Set<Recipe> filteredRecipes = cookbook.searchRecipes(query);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (filteredRecipes != null && !filteredRecipes.isEmpty()) {
            // Display filtered recipes
            RecipeFragment recipeFragment = RecipeFragment.newInstance(new Cookbook(filteredRecipes));
            mainView.displayFragment(recipeFragment);
        } else if (currentFragment instanceof SearchRecipeFragment) {
            // Use the current fragment to show the error
            ((SearchRecipeFragment) currentFragment).showRecipeNotFoundError();
        } else {
            // Navigate back to SearchRecipeFragment and show the error
            SearchRecipeFragment searchRecipeFragment = SearchRecipeFragment.newInstance(this);
            mainView.displayFragment(searchRecipeFragment);
            getSupportFragmentManager().executePendingTransactions();
            searchRecipeFragment.showRecipeNotFoundError();
        }
    }

    @Override
    public void onSearchDone() {
        // Navigate back to the Cookbook view or another fragment
        mainView.displayFragment(CookbookFragment.newInstance(this, cookbook));
    }

    @Override
    public void onSearchRecipesMenu() {
        SearchRecipeFragment searchRecipeFragment = SearchRecipeFragment.newInstance(this);
        mainView.displayFragment(searchRecipeFragment);
    }

    @Override
    public void onSearchIngredient(String query) {
        // Search for ingredients in the pantry
        List<Ingredient> foundIngredients = pantry.searchIngredient(query);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (!foundIngredients.isEmpty()) {
            if (currentFragment instanceof SearchIngredientFragment) {
                // Update the current fragment with found ingredients
                ((SearchIngredientFragment) currentFragment).displayFoundIngredients(foundIngredients);
            } else {
                // Navigate to SearchIngredientFragment and display results
                SearchIngredientFragment searchIngredientFragment = SearchIngredientFragment.newInstance(this);
                mainView.displayFragment(searchIngredientFragment);
                getSupportFragmentManager().executePendingTransactions();
                searchIngredientFragment.displayFoundIngredients(foundIngredients);
            }
        } else if (currentFragment instanceof SearchIngredientFragment) {
            // Use the current fragment to show the error
            ((SearchIngredientFragment) currentFragment).showIngredientNotFoundError();
        } else {
            // Navigate back to SearchIngredientFragment and show the error
            SearchIngredientFragment searchIngredientFragment = SearchIngredientFragment.newInstance(this);
            mainView.displayFragment(searchIngredientFragment);
            getSupportFragmentManager().executePendingTransactions();
            searchIngredientFragment.showIngredientNotFoundError();
        }
    }

    @Override
    public void onSearchIngredientDone() {
        // Navigate back to the PantryFragment view or another fragment
        mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onSearchIngredientsMenu() {
        SearchIngredientFragment searchIngredientFragment = SearchIngredientFragment.newInstance(this);
        mainView.displayFragment(searchIngredientFragment);
    }
}