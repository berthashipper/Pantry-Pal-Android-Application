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
        void onDeleteIngredient(String name);
        void onDeletionDone();
    }

    /**
     * Updates the pantry display with the current pantry contents.
     *
     * @param pantry The pantry object containing the ingredients.
     */
    void updatePantryDisplay(@NonNull final Pantry pantry);

    /**
     * Returns the top-level graphical element.
     *
     * @return The root view.
     */
}
