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

    private static final String LEDGER_FNAME = "ledger.pos";
    private final Context context;

    public LocalStorageFacade(final Context context) {
        this.context = context;
    }

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


