package com.example.pantrypalandroidprototype.persistence;

import android.content.Context;
import android.util.Log;

import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Ledger;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
public class LocalStorageFacade implements IPersistenceFacade {

    static final String COOKBOOK_FNAME = "cookbook";
    static final String PANTRY_FNAME = "pantry";
    static final String GROCERY_LIST_FNAME = "grocery_list";
    final Context context;

    /**
     * Constructs a {@code LocalStorageFacade} with the specified context.
     *
     * @param context The application context used for file operations.
     */
    public LocalStorageFacade(final Context context) {
        this.context = context;
    }

    /**
     * Saves the cookbook to persistent storage.
     *
     * @param cookbook The cookbook object to save.
     */
    @Override
    public void saveCookbook(Cookbook cookbook) {
        final File outFile = new File(context.getFilesDir(), COOKBOOK_FNAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(cookbook);
        } catch (IOException e) {
            final String emsg = String.format("I/O exception while writing cookbook: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
    }

    /**
     * Loads the cookbook from persistent storage.
     *
     * @return The loaded cookbook, or a new instance if none exists.
     */
    @Override
    public Cookbook loadCookbook() {
        final File inFile = new File(context.getFilesDir(), COOKBOOK_FNAME);
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            if (inFile.exists()) {
                return (Cookbook) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("I/O exception while reading cookbook: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
        return new Cookbook();  // Return a default empty cookbook if not found
    }

    /**
     * Saves the pantry to persistent storage.
     *
     * @param pantry The pantry object to save.
     */
    @Override
    public void savePantry(Pantry pantry) {
        final File outFile = new File(context.getFilesDir(), PANTRY_FNAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(pantry);
        } catch (IOException e) {
            final String emsg = String.format("I/O exception while writing pantry: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
    }

    /**
     * Loads the pantry from persistent storage.
     *
     * @return The loaded pantry, or a new instance if none exists.
     */
    @Override
    public Pantry loadPantry() {
        final File inFile = new File(context.getFilesDir(), PANTRY_FNAME);
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            if (inFile.exists()) {
                return (Pantry) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("I/O exception while reading pantry: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
        return new Pantry();  // Return a default empty pantry if not found
    }

    /**
     * Saves the grocery list to persistent storage.
     *
     * @param groceryList The grocery list to save.
     */
    @Override
    public void saveGroceryList(Map<Ingredient, Double> groceryList) {
        final File outFile = new File(context.getFilesDir(), GROCERY_LIST_FNAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(groceryList);
        } catch (IOException e) {
            final String emsg = String.format("I/O exception while writing grocery list: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
    }

    /**
     * Loads the grocery list from persistent storage.
     *
     * @return The loaded grocery list, or a new empty list if none exists.
     */
    @Override
    public Map<Ingredient, Double> loadGroceryList() {
        final File inFile = new File(context.getFilesDir(), GROCERY_LIST_FNAME);
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            if (inFile.exists()) {
                return (Map<Ingredient, Double>) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("I/O exception while reading grocery list: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
        return new HashMap<>();  // Return a new empty map if not found
    }
}