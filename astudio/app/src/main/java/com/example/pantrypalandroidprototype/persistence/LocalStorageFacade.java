package com.example.pantrypalandroidprototype.persistence;

import android.content.Context;
import android.util.Log;

import com.example.pantrypalandroidprototype.model.Cookbook;
import com.example.pantrypalandroidprototype.model.Ledger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
public class LocalStorageFacade implements IPersistenceFacade {

    /**
     * The file name for storing the ledger data.
     */
    private static final String LEDGER_FNAME = "ledger.pos";

    /**
     * The application context used for accessing file storage.
     */
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
     * Saves the {@link Ledger} object to local storage.
     *
     * @param ledger The {@link Ledger} to be saved.
     */
    @Override
    public void saveLedger(final Ledger ledger) {
        final File outFile = new File(context.getFilesDir(), LEDGER_FNAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(ledger);
        } catch (IOException e){
            final String emsg = String.format("I/O exception while writing ledger: %s", e.getMessage());
            Log.e("NextGenPOS", emsg, e);
        }
    }


    /**
     * Loads the {@link Ledger} object from local storage.
     * <p>
     * If the ledger file does not exist, a new {@link Ledger} is returned.
     * </p>
     *
     * @return The loaded {@link Ledger}, or a new instance if the file does not exist or an error occurs.
     */
    @Override
    public Ledger loadLedger() {
        Ledger ledger = new Ledger();
        File inFile = new File(context.getFilesDir(), LEDGER_FNAME);
        if (!inFile.isFile()) return ledger;
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            ledger = (Ledger) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("Exception while reading ledger: %s", e.getMessage());
            Log.e("PantryPal", emsg, e);
        }
        return ledger;
    }


    /**
     * Saves the {@link Cookbook} object to local storage.
     *
     * @param cookbook The {@link Cookbook} to be saved.
     */
    @Override
    public void saveCookbook(Cookbook cookbook) {
        final File outFile = new File(context.getFilesDir(), "cookbook.pos");
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(cookbook);
        } catch (IOException e) {
            final String emsg = String.format("I/O exception while writing cookbook: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
    }


    /**
     * Loads the {@link Cookbook} object from local storage.
     * <p>
     * If the cookbook file does not exist or an error occurs, a new {@link Cookbook} instance is returned.
     * </p>
     *
     * @return The loaded {@link Cookbook}, or a new instance if the file does not exist or an error occurs.
     */
    @Override
    public Cookbook loadCookbook() {
        final File inFile = new File(context.getFilesDir(), "cookbook.pos");
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            if (inFile.exists()) {
                return (Cookbook) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("I/O exception while reading cookbook: %s", e.getMessage());
            Log.e("LocalStorageFacade", emsg);
        }
        return new Cookbook();
    }
}


