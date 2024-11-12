package com.example.pantrypalandroidprototype.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.PantryAdapter;

import java.util.List;

/**
 * An interface for the application screen template.
 */
public interface IMainView {
    /**
     * Retrieve the graphical widget (android view) at the root of the screen hierarchy/
     * @return the screen's root android view (widget)
     */
    View getRootView();

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument.
     *
     * @param fragment The fragment to be displayed.
     */
    void displayFragment(@NonNull final Fragment fragment);

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument,
     * and adds the transaction to the back stack, under the name specified as an argument (iff non-null).
     *
     * @param fragment The fragment to be displayed.
     * @param transName the name this transaction can be referred by.
     */
    void displayFragment(@NonNull final Fragment fragment, final String transName);

    PantryAdapter getPantryAdapter();
    /**
     * Displays the pantry items in the RecyclerView.
     */
    void displayPantry(@NonNull List<Ingredient> pantryItems);
}
