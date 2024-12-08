package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentSearchIngredientBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * {@code SearchIngredientFragment} is a {@link Fragment} that allows users to search for ingredients in the pantry.
 * It provides a search bar for entering ingredient queries and displays the results or an error message if no ingredients are found.
 */
public class SearchIngredientFragment extends Fragment implements ISearchIngredientView{
    FragmentSearchIngredientBinding binding;
    Listener listener;

    /**
     * Creates a new instance of {@code SearchIngredientFragment} with the specified listener.
     *
     * @param listener The listener to handle search events.
     * @return A new instance of {@code SearchIngredientFragment}.
     */
    public static SearchIngredientFragment newInstance(Listener listener) {
        SearchIngredientFragment fragment = new SearchIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }


    /**
     * Called to inflate the fragment's layout. It sets up the view binding for the fragment.
     *
     * @param inflater The LayoutInflater used to inflate the view.
     * @param container The parent view group that the fragment's UI will be attached to.
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchIngredientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created. This method sets up the listeners for the search and done buttons.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchButton.setOnClickListener(v -> onSearchButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handles the click event for the search button. It triggers the search action if the query is valid.
     */
    public void onSearchButtonClicked() {
        String query = binding.searchQueryText.getText().toString().trim();

        if (listener != null && !query.isEmpty()) {
            listener.onSearchIngredient(query);
        } else {
            binding.errorText.setText("Please enter a valid search query.");
        }
    }

    /**
     * Handles the click event for the done button. It signals that the search process is complete.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onSearchIngredientDone();
        }
    }

    /**
     * Displays an error message indicating that no ingredients were found.
     */
    public void showIngredientNotFoundError() {
        Snackbar.make(binding.getRoot(), "No ingredients found", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays the list of ingredients that were found based on the search query.
     *
     * @param ingredients A list of {@link Ingredient} objects that match the search query.
     */
    public void displayFoundIngredients(List<Ingredient> ingredients) {
        // Display the found ingredients in a list or text view
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            result.append(ingredient.toString()).append("\n");
        }
        binding.searchResultsTextView.setText(result.toString());
    }
}
