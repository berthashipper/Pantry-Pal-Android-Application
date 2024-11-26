package persistence;

import android.content.Context;
import android.util.Log;

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
public class LocalStorageFacade implements IPersistenceFacade{

    private static final String LEDGER_FNAME = "ledger.pos";
    private final Context context;

    /**
     * Constructor method.
     *
     * @param context the context in which the app is running.
     */
    public LocalStorageFacade(final Context context) { this.context = context; }

    /**
     * Saves ledger to underlying persistence subsystem.
     *
     * @param ledger the ledger object to be saved.
     */
    @Override
    public void saveLedger(final Ledger ledger) {
        final File outFile = new File(context.getFilesDir(), LEDGER_FNAME);
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ledger); // will create file if first time, overwrite if pre-existing
        } catch (IOException e){
            final String emsg = String.format("I/O exception while writing ledger: %s", e.getMessage());
            Log.e("NextGenPOS", emsg, e);
        }
    }

    /**
     * Loads ledger from underlying persistence subsystem.
     *
     * @return the ledger loaded from the persistence subsystem, or an empty one if none exists.
     */
    @Override
    public Ledger loadLedger() {
        Ledger ledger = new Ledger(); // use empty ledger as default

        File inFile = new File(context.getFilesDir(), LEDGER_FNAME);

        if (!inFile.isFile()) return ledger; // no point in continuing if file doesn't exist

        try {
            FileInputStream fileInputStream = new FileInputStream(inFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ledger = (Ledger) objectInputStream.readObject(); // returns Object so typecast needed
        } catch (IOException | ClassNotFoundException e) {
            final String emsg = String.format("Exception while reading ledger: %s", e.getMessage());
            Log.e("NextGenPOS", emsg, e);
        }

        return ledger;
    }
}

