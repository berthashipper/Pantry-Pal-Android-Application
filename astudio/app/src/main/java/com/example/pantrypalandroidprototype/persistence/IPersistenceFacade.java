package com.example.pantrypalandroidprototype.persistence;

import com.example.pantrypalandroidprototype.model.Ledger;

/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
public interface IPersistenceFacade {

    /**
     * Saves ledger to underlying persistence subsystem.
     *
     * @param ledger the ledger object to be saved.
     */
    void saveLedger(final Ledger ledger);

    /**
     * Loads ledger from underlying persistence subsystem.
     *
     * @return the ledger loaded from the persistence subsystem, or an empty one if none exists.
     */
    Ledger loadLedger();
}

