package com.example.pantrypalandroidprototype.persistence;

import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.Map;

/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
public interface IPersistenceFacade {

    /**
     * Saves the cookbook to the underlying persistence subsystem.
     *
     * @param cookbook The cookbook to save.
     */
    void saveCookbook(Cookbook cookbook);

    /**
     * Loads the cookbook from the underlying persistence subsystem.
     *
     * @return The loaded cookbook, or a new one if none exists.
     */
    Cookbook loadCookbook();

    /**
     * Saves the pantry to the underlying persistence subsystem.
     *
     * @param pantry The pantry to save.
     */
    void savePantry(Pantry pantry);

    /**
     * Loads the pantry from the underlying persistence subsystem.
     *
     * @return The loaded pantry, or a new one if none exists.
     */
    Pantry loadPantry();
    /**
     * Saves the grocery list to the underlying persistence subsystem.
     *
     * @param groceryList The grocery list to save.
     */
    void saveGroceryList(Map<Ingredient, Double> groceryList);
    /**
     * Loads the grocery list from the underlying persistence subsystem.
     *
     * @return The loaded grocery list, or a new one if none exists.
     */
    Map<Ingredient, Double> loadGroceryList();
}

