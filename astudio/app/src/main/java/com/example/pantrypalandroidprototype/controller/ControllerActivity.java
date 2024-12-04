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
import com.example.pantrypalandroidprototype.model.Pantry;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.AddRecipeFragment;
import com.example.pantrypalandroidprototype.view.AddRecipeIngredientFragment;
import com.example.pantrypalandroidprototype.view.AddToGroceryListFragment;
import com.example.pantrypalandroidprototype.view.CookbookFragment;
import com.example.pantrypalandroidprototype.view.DeleteIngredientFragment;
import com.example.pantrypalandroidprototype.view.DeleteRecipeIngredientFragment;
import com.example.pantrypalandroidprototype.view.EditIngredientFragment;
import com.example.pantrypalandroidprototype.view.EditInstructionFragment;
import com.example.pantrypalandroidprototype.view.EditRecipeIngredientFragment;
import com.example.pantrypalandroidprototype.view.GroceryListFragment;
import com.example.pantrypalandroidprototype.view.IAddIngredientView;
import com.example.pantrypalandroidprototype.view.IAddRecipeIngredientView;
import com.example.pantrypalandroidprototype.view.IAddRecipeView;
import com.example.pantrypalandroidprototype.view.IAddToGroceryListView;
import com.example.pantrypalandroidprototype.view.ICookbookView;
import com.example.pantrypalandroidprototype.view.IDeleteIngredientView;
import com.example.pantrypalandroidprototype.view.IDeleteRecipeIngredientView;
import com.example.pantrypalandroidprototype.view.IEditIngredientView;
import com.example.pantrypalandroidprototype.view.IEditInstructionView;
import com.example.pantrypalandroidprototype.view.IEditRecipeIngredientView;
import com.example.pantrypalandroidprototype.view.IGroceryListView;
import com.example.pantrypalandroidprototype.view.IMainView;
import com.example.pantrypalandroidprototype.view.IPantryView;
import com.example.pantrypalandroidprototype.view.IRecipeDetailView;
import com.example.pantrypalandroidprototype.view.IScaleRecipeView;
import com.example.pantrypalandroidprototype.view.ISearchIngredientView;
import com.example.pantrypalandroidprototype.view.ISearchRecipeView;
import com.example.pantrypalandroidprototype.view.MainView;
import com.example.pantrypalandroidprototype.view.PantryFragment;
import com.example.pantrypalandroidprototype.view.RecipeDetailFragment;
import com.example.pantrypalandroidprototype.view.RecipeFragment;
import com.example.pantrypalandroidprototype.view.ScaleRecipeFragment;
import com.example.pantrypalandroidprototype.view.SearchIngredientFragment;
import com.example.pantrypalandroidprototype.view.SearchRecipeFragment;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.pantrypalandroidprototype.persistence.IPersistenceFacade;
import com.example.pantrypalandroidprototype.persistence.LocalStorageFacade;
import com.google.android.material.snackbar.Snackbar;

public class ControllerActivity extends AppCompatActivity
        implements IAddIngredientView.Listener, IPantryView.Listener,
        IDeleteIngredientView.Listener, IEditIngredientView.Listener,
        ICookbookView.Listener, IAddRecipeView.Listener, IRecipeDetailView.Listener,
        ISearchRecipeView.Listener, ISearchIngredientView.Listener, IScaleRecipeView.Listener,
        IEditRecipeIngredientView.Listener, IAddRecipeIngredientView.Listener,
        IDeleteRecipeIngredientView.Listener, IEditInstructionView.Listener,
        IGroceryListView.Listener, IAddToGroceryListView.Listener {

    IMainView mainView;
    Pantry pantry;
    Cookbook cookbook;
    Recipe currentRecipe;
    Map<Ingredient, Double> groceryList;
    IPersistenceFacade persFacade;


    public static final int REQUEST_CODE_ADD_TO_COOKBOOK = 1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save cookbook and pantry before the activity is destroyed
        persFacade.saveCookbook(cookbook);
        persFacade.savePantry(pantry);

        // Optionally save other data like the current recipe
        if (currentRecipe != null) {
            outState.putSerializable("currentRecipe", currentRecipe);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("currentRecipe")) {
            currentRecipe = (Recipe) savedInstanceState.getSerializable("currentRecipe");
            Log.d("ControllerActivity", "Restored currentRecipe: " + currentRecipe.getRecipeName());
        }

        // Set up persistence facade
        this.persFacade = new LocalStorageFacade(this);
        // Load cookbook, pantry, and grocery list from local storage
        this.cookbook = this.persFacade.loadCookbook();
        this.pantry = this.persFacade.loadPantry();
        this.groceryList = this.persFacade.loadGroceryList();

        setContentView(R.layout.main);
        this.mainView = new MainView(this, this);

        setContentView(this.mainView.getRootView());

        // Initialize files if empty
        if (this.pantry == null) {
            this.pantry = new Pantry();
        }
        if (this.cookbook == null) {
            this.cookbook = new Cookbook();
        }
        if (this.groceryList == null) {
            this.groceryList = new HashMap<>();
        }

        mainView.setListener(this);

        // Display pantry fragment to start app
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
        // Save updated pantry to local storage
        persFacade.savePantry(pantry);
    }

    @Override
    public void onItemsDone() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (currentFragment instanceof AddIngredientFragment) {
            AddIngredientFragment addIngredientFragment = (AddIngredientFragment) currentFragment;
            addIngredientFragment.showDoneMessage();
            // Switch to the Pantry fragment
            this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
        }
        if (currentFragment instanceof AddToGroceryListFragment) {
            AddToGroceryListFragment addToGroceryListFragment = (AddToGroceryListFragment) currentFragment;
            addToGroceryListFragment.showDoneMessage();
            // Switch to the Pantry fragment
            this.mainView.displayFragment(GroceryListFragment.newInstance(this, groceryList));
        }
    }

    @Override
    public void onAddIngredientsMenu() {
        AddIngredientFragment addIngredientFragment = AddIngredientFragment.newInstance(this);
        this.mainView.displayFragment(addIngredientFragment);

    }

    @Override
    public void onViewPantryMenu() {
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

        persFacade.savePantry(pantry);
        onViewPantryMenu(); // Return to the pantry view
    }

    @Override
    public void onDeletionDone() {
        // Pass the pantry data to the fragment
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
        persFacade.savePantry(pantry);
    }

    @Override
    public void onDeleteIngredientsMenu() {
        DeleteIngredientFragment deleteIngredientFragment = DeleteIngredientFragment.newInstance(this);
        this.mainView.displayFragment(deleteIngredientFragment);
        persFacade.savePantry(pantry);
    }

    @Override
    public void onClearPantry() {
        // Clear all ingredients from the pantry
        pantry.clear();
        // Save updated pantry to local storage
        persFacade.savePantry(pantry);

        // Get the current fragment and check if it is an instance of PantryFragment
        PantryFragment pantryFragment = (PantryFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        // If the fragment is found, call showClearedMessage
        if (pantryFragment != null) {
            pantryFragment.showClearedMessage();
        }

        onViewPantryMenu(); // Return to the pantry view
    }

    @Override
    public void onEditIngredientMenu(Ingredient ingredient) {
        EditIngredientFragment editIngredientFragment = EditIngredientFragment.newInstance(this, ingredient);
        mainView.displayFragment(editIngredientFragment);
    }

    @Override
    public void onDeleteIngredientMenu(Ingredient ingredient) {
        pantry.delete_ingredient(String.valueOf(ingredient));
        persFacade.savePantry(pantry);
        this.mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onEditIngredient(EditIngredientFragment editIngredientFragment, String name, double newQty) {
        boolean isUpdated = pantry.edit_ingredient(name, newQty);
        if (isUpdated) {
            editIngredientFragment.showIngredientUpdateMessage(name);
        } else {
            editIngredientFragment.showIngredientNotFoundError(name);
        }
        // Save updated pantry to local storage
        persFacade.savePantry(pantry);
        // Return to pantry view
        onViewPantryMenu();
    }

    @Override
    public void onEditDone() {
        mainView.displayFragment(PantryFragment.newInstance(this, pantry));
    }

    @Override
    public void onEditIngredientsMenu() {
        //EditIngredientFragment editIngredientFragment = EditIngredientFragment.newInstance(this,);
        //mainView.displayFragment(editIngredientFragment);
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
        this.currentRecipe = recipe;
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        this.mainView.displayFragment(recipeDetailFragment);
    }

    @Override
    public void onGenerateRecipes() {
        Set<Recipe> matchedRecipes = generateMatchingRecipes();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (!matchedRecipes.isEmpty()) {
            // Select the first matched recipe to display
            Recipe selectedRecipe = matchedRecipes.iterator().next();
            this.currentRecipe = selectedRecipe;

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
        Log.d("ControllerActivity", "Recipes available in cookbook: " + cookbook.recipeList.size());
        GenerateRecipe recipeGenerator = new GenerateRecipe(pantry, cookbook);
        return recipeGenerator.generateMatchingRecipes();
    }

    @Override
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
        // Persist changes to the cookbook
        persFacade.saveCookbook(cookbook);
        // Update the view
        updateCookbookFragment();
        // Update the CookbookFragment to include the new recipe
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, CookbookFragment.newInstance(this, cookbook))
                .commit();
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
    public void onScaleRecipeMenu() {
        ScaleRecipeFragment fragment = ScaleRecipeFragment.newInstance(currentRecipe, this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit();
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
        // Navigate back to the Cookbook view
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

    @Override
    public void onScaleRecipe(double scaleFactor) {
        ScaleRecipeFragment scaleRecipeFragment = ScaleRecipeFragment.newInstance(currentRecipe,this);
        mainView.displayFragment(scaleRecipeFragment);
    }

    @Override
    public void onRecipeScaled(Recipe scaledRecipe) {
        this.currentRecipe = scaledRecipe; // Update the current recipe
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(scaledRecipe);
        mainView.displayFragment(recipeDetailFragment);
    }

    @Override
    public void onBackToRecipe() {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(currentRecipe);
        mainView.displayFragment(recipeDetailFragment); // Navigate back to recipe
    }

    public void setCookTime(Duration cookTime, Recipe recipe) {
        recipe.setCookTime(cookTime);
        persFacade.saveCookbook(cookbook);  // Save updated cookbook
        mainView.displayFragment(RecipeDetailFragment.newInstance(recipe));  // Refresh view
    }

    public void setServingSize(int servingSize, Recipe recipe) {
        recipe.setServingSize(servingSize);
        persFacade.saveCookbook(cookbook);  // Save updated cookbook
        mainView.displayFragment(RecipeDetailFragment.newInstance(recipe));  // Refresh view
    }

    public void onBackToRecipe(Recipe recipe) {
        mainView.displayFragment(RecipeDetailFragment.newInstance(recipe));  // Navigate back to recipe view
    }

    @Override
    public void onEditRecipeDone() {
        persFacade.saveCookbook(cookbook);
        mainView.displayFragment(RecipeDetailFragment.newInstance(currentRecipe));
    }

    @Override
    public void onEditRecipeIngredient(String name, double newQty) {
        // Update the recipe object with the new ingredient quantity
        for (Ingredient ingredient : currentRecipe.getIngredients()) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                ingredient.updateQuantity(newQty);  // Update quantity in the model
                break;
            }
        }
        persFacade.saveCookbook(cookbook);
        // Notify the user
        Snackbar.make(findViewById(R.id.fragmentContainerView), name + " updated!", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onEditRecipeIngredients() {
        EditRecipeIngredientFragment editRecipeIngredientFragment = EditRecipeIngredientFragment.newInstance(this);
        this.mainView.displayFragment(editRecipeIngredientFragment);
    }

    @Override
    public void onAddRecipeIngredients() {
        AddRecipeIngredientFragment addRecipeIngredientFragment = AddRecipeIngredientFragment.newInstance(this);
        this.mainView.displayFragment(addRecipeIngredientFragment);
    }

    @Override
    public void onAddRecipeIngredient(String name, double newQty, String unit, Set<Ingredient.dietary_tags> tags) {
        boolean ingredientFound = false;

        // Check if the ingredient already exists in the current recipe's ingredient list
        for (Ingredient ingredient : currentRecipe.getIngredients()) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                // Update the quantity and unit of the existing ingredient
                ingredient.updateQuantity(newQty);
                ingredientFound = true;
                break;
            }
        }
    // Convert Set<Ingredient.dietary_tags> to Set<String>
        Set<String> tagStrings = new HashSet<>();
        for (Ingredient.dietary_tags tag : tags) {
            tagStrings.add(tag.name()); // Converts enum to String
        }

        // If the ingredient does not exist, add a new one
        if (!ingredientFound) {
            Ingredient newIngredient = new Ingredient(name, newQty, unit, tagStrings);
            currentRecipe.ingredientsInRecipe.add(newIngredient);
        }

        persFacade.saveCookbook(cookbook);
        // Notify the user that the ingredient has been added or updated
        Snackbar.make(findViewById(R.id.fragmentContainerView), name + " added to recipe.", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onAddRecipeDone() {
        mainView.displayFragment(RecipeDetailFragment.newInstance(currentRecipe));
    }

    @Override
    public void onNavigateToCookbook() {
        mainView.displayFragment(CookbookFragment.newInstance(this,cookbook));
    }

    @Override
    public void onDeleteRecipeIngredients() {
        DeleteRecipeIngredientFragment deleteRecipeIngredientFragment = DeleteRecipeIngredientFragment.newInstance(this);
        this.mainView.displayFragment(deleteRecipeIngredientFragment);
    }

    @Override
    public void onDeleteRecipeIngredient(String name) {
// Update the recipe object by removing the specified ingredient
        boolean ingredientFound = false;
        Iterator<Ingredient> iterator = currentRecipe.getIngredients().iterator();

        while (iterator.hasNext()) {
            Ingredient ingredient = iterator.next();
            if (ingredient.getName().equalsIgnoreCase(name)) {
                iterator.remove();  // Remove the ingredient from the list
                ingredientFound = true;
                break;
            }
        }

        // Notify the user of the deletion result
        if (ingredientFound) {
            Snackbar.make(findViewById(R.id.fragmentContainerView), name + " deleted from recipe.", Snackbar.LENGTH_LONG).show();
            persFacade.saveCookbook(cookbook);
        } else {
            Snackbar.make(findViewById(R.id.fragmentContainerView), name + " not found in recipe", Snackbar.LENGTH_LONG).show();
            persFacade.saveCookbook(cookbook);
        }

    }
    @Override
    public void onDeleteRecipeDone() {
        mainView.displayFragment(RecipeDetailFragment.newInstance(currentRecipe));
        Snackbar.make(findViewById(R.id.fragmentContainerView), "Returned to Recipe Details", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onEditInstructions() {
        EditInstructionFragment editInstructionFragment = EditInstructionFragment.newInstance(this);
        this.mainView.displayFragment(editInstructionFragment);
    }

    @Override
    public void onEditInstruction(String instruction) {
            currentRecipe.instructions = instruction;
    }

    @Override
    public void onAddInstruction(String instruction) {
        // Retrieve existing instructions from the model
        String existingInstructions = currentRecipe.getInstructions();

        // If existing instructions are not empty, add a newline or bullet point for separation
        if (!existingInstructions.isEmpty()) {
            existingInstructions += "\n"; // Use "\n" or "\n• " for bullet points
        }

        // Add the new instruction to the existing ones
        existingInstructions += instruction;

        // Update the recipe model with the new combined instructions
        currentRecipe.instructions = existingInstructions;
    }

    @Override
    public void onEditInstructionDone() {
        // Return to the recipe detail view or any other relevant fragment
        mainView.displayFragment(RecipeDetailFragment.newInstance(currentRecipe));
        Snackbar.make(findViewById(R.id.fragmentContainerView), "Returned to Recipe Details", Snackbar.LENGTH_LONG).show();
    }
    @Override
    public void onAddIngredientToGroceryList(String name, double qty) {
        Ingredient newIngredient = new Ingredient(name, qty, "", new HashSet<>());
        groceryList.put(newIngredient, qty);

        // Save the updated list to pantry
        pantry.setGroceryList(groceryList); // Add this to reflect changes in the pantry

        persFacade.saveGroceryList(groceryList);

        // Log the update for debugging
        Log.d("Controller", "Grocery list after addition: " + groceryList);
    }

    @Override
    public void onAddIngredientsToGroceryListMenu() {
        AddToGroceryListFragment addToGroceryListFragment = AddToGroceryListFragment.newInstance(this);
        this.mainView.displayFragment(addToGroceryListFragment);
        persFacade.saveGroceryList(groceryList);
    }
    @Override
    public void onViewGroceryListMenu() {
        if (groceryList == null) {
            groceryList = pantry.getGroceryList();
        }
        // Log the grocery list to verify integrity
        Log.d("Controller", "Displaying GroceryListFragment with groceryList: " + groceryList);

        mainView.displayFragment(GroceryListFragment.newInstance(this, groceryList));
    }

    // Helper method to convert Pantry to Map<Ingredient, Double>
    public Map<Ingredient, Double> convertPantryToGroceryList(Pantry pantry) {
        Map<Ingredient, Double> groceryListMap = new HashMap<>();
        for (Ingredient ingredient : pantry.getAllIngredients()) {
            groceryListMap.put(ingredient, ingredient.getQuantity());
        }
        return groceryListMap;
    }

    @Override
    public void onClearShoppingList() {
        groceryList.clear();
        persFacade.saveGroceryList(groceryList);

        // Get the current fragment and check if it is an instance of PantryFragment
        GroceryListFragment groceryListFragment = (GroceryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        // If the fragment is found, call showClearedMessage
        if (groceryListFragment != null) {
            groceryListFragment.showClearedMessage();
        }

        onViewGroceryListMenu(); // Return to the grocery list view
    }

    @Override
    public void onRemoveIngredient(Ingredient ingredient) {
        groceryList.remove(ingredient);  // Remove ingredient from the list
        persFacade.saveGroceryList(groceryList); // Save the updated grocery list

        // Get the current fragment and check if it is an instance of PantryFragment
        GroceryListFragment groceryListFragment = (GroceryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        // If the fragment is found, call showDeletedMessage
        if (groceryListFragment != null) {
            groceryListFragment.showDeletedMessage(ingredient);
        }
    }
}