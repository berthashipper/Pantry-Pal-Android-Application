package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.databinding.FragmentPantryBinding;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;

/**
 * The PantryFragment class represents a user interface fragment that displays and manages pantry-related operations.
 * It provides functionalities to add, delete, edit, view, search, and clear pantry ingredients.
 * This fragment communicates with its hosting activity or parent component through a {@link Listener}.
 */
public class PantryFragment extends Fragment implements IPantryView {
    /** Binding object to access the views in the fragment's layout. */
    FragmentPantryBinding binding;
    /** Listener for handling user interactions and passing events to the parent context. */
    Listener listener;
    /** The Pantry object that contains the list of ingredients managed by this fragment. */
    Pantry pantry;

    /**
     * Creates a new instance of the PantryFragment with a specified listener and pantry data.
     *
     * @param listener The listener that will handle interactions from this fragment.
     * @param pantry   The pantry object containing the current list of ingredients.
     * @return A new instance of PantryFragment.
     */
    public static PantryFragment newInstance(IPantryView.Listener listener, Pantry pantry) {
        PantryFragment fragment = new PantryFragment();
        fragment.listener = listener;
        fragment.pantry = pantry;
        return fragment;
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater  The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the inflated fragment layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPantryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        return rootView;
    }

    /**
     * Called immediately after {@link #onCreateView} has returned, and the view hierarchy is fully set up.
     *
     * @param view               The view returned by {@link #onCreateView}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientsButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.viewPantryButton.setOnClickListener(v -> onViewPantryMenu());
        binding.deleteIngredientsButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.editIngredientsButton.setOnClickListener(v -> onEditButtonClicked());
        binding.searchIngredientsButton.setOnClickListener(v -> onSearchIngredientsMenu());
        binding.clearPantryButton.setOnClickListener(v -> onClearPantryButtonClicked());

        this.binding.pantryContentsTextView.setText(this.pantry.toString());
    }

    /**
     * Handles the "Add Ingredients" button click event.
     * Notifies the listener to open the add ingredients menu.
     */
    public void onAddIngredientButtonClicked() {
        if (listener != null) {
            listener.onAddIngredientsMenu();
        }
    }

    /**
     * Handles the "View Pantry" button click event.
     * Notifies the listener to open the view pantry menu.
     */
    public void onViewPantryMenu() {
        if (listener != null) {
            listener.onViewPantryMenu();
        }
    }

    /**
     * Handles the "Delete Ingredients" button click event.
     * Notifies the listener to open the delete ingredients menu.
     */
    public void onDeleteButtonClicked() {
        if (listener != null) {
            listener.onDeleteIngredientsMenu();
        }
    }

    /**
     * Handles the "Edit Ingredients" button click event.
     * Notifies the listener to open the edit ingredients menu.
     */
    public void onEditButtonClicked() {
        if (listener != null) {
            listener.onEditIngredientsMenu();
        }
    }

    /**
     * Handles the "Search Ingredients" button click event.
     * Notifies the listener to open the search ingredients menu.
     */
    public void onSearchIngredientsMenu() {
        if (listener != null) {
            listener.onSearchIngredientsMenu();
        }
    }

    /**
     * Handles the "Clear Pantry" button click event.
     * Notifies the listener to clear all ingredients in the pantry.
     */
    public void onClearPantryButtonClicked() {
        // Create an AlertDialog to confirm clearing the pantry
        new AlertDialog.Builder(requireContext())
                .setTitle("Clear Pantry")
                .setMessage("Are you sure you want to clear all ingredients? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Notify listener to handle clearing the pantry if the user confirms
                    if (listener != null) {
                        listener.onClearPantry();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    /**
     * Displays a Snackbar message indicating that the pantry has been cleared.
     */
    public void showClearedMessage() {
        Snackbar.make(getView(), "Pantry cleared", Snackbar.LENGTH_LONG).show();
    }
}
