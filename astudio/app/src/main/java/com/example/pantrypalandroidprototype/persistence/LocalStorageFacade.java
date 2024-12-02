package com.example.pantrypalandroidprototype.persistence;

import android.content.Context;
import android.util.Log;

import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ledger;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
// LocalStorageFacade.java

public class LocalStorageFacade implements IPersistenceFacade {

    private static final String COOKBOOK_FNAME = "cookbook.pos";
    private static final String PANTRY_FNAME = "pantry.pos";

    private final Context context;

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
        return new Cookbook();  // Return a default empty cookbook if loading fails
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
        return new Pantry();  // Return a default empty pantry if loading fails
    }
}