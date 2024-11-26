package com.example.pantrypalandroidprototype.model;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Represents a Collection of completed recipes
 *
 */
public class Ledger implements java.io.Serializable {

    private final Collection<Recipe> recipes; // the recipes recorded on the ledger

    /**
     * Constructs an empty ledger.
     */
    public Ledger(){
        this.recipes = new LinkedList<>();  }


    /**
     * Adds a new recipe to the ledger.
     * @param done the recipe to be added to the ledger.
     */
    public void addRecipe(Recipe done){
        this.recipes.add(done);
    }

    /**
     * Creates and returns a textual representation of the ledger.
     *
     * @return a textual representation of the ledger
     */
    @NonNull
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Recipe recipe : this.recipes) {
            sb.append(recipe.toString());
            sb.append("-----------\n");
        }
        return sb.toString();
    }
}
