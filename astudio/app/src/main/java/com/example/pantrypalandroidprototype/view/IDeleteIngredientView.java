package com.example.pantrypalandroidprototype.view;

import android.view.View;
import androidx.annotation.NonNull;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import java.util.Set;
/**
 * Interface that defines the methods for a view that allows a user to delete items from the pantry.
 */
public interface IDeleteIngredientView {

    /**
     * Interface that classes interested in being notified of events happening
     * to the view should implement.
     */
    interface Listener {

        /**
         * This method is called when a specific ingredient is deleted from the pantry.
         *
         * @param name The name of the ingredient to be deleted.
         */
        void onDeleteIngredient(String name);

        /**
         * This method is called when the deletion process is complete.
         */
        void onDeletionDone();
    }

    /**
     * Updates the pantry display with the current state of the pantry.
     *
     * @param pantry The updated Pantry object that reflects the current state of the pantry.
     */
    void updatePantryDisplay(@NonNull final Pantry pantry);
}
