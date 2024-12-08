package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.databinding.FragmentSearchRecipeBinding;
import com.google.android.material.snackbar.Snackbar;

/**
 * {@code SearchRecipeFragment} is a {@link Fragment} that allows users to search for recipes.
 * It provides a search input field where users can enter a query to search for recipes.
 * The results are displayed or an error message is shown if no recipes are found.
 */
public class SearchRecipeFragment extends Fragment implements ISearchRecipeView {
    FragmentSearchRecipeBinding binding;
    Listener listener;


    /**
     * Creates a new instance of {@code SearchRecipeFragment} with the specified listener.
     *
     * @param listener The listener to handle search events.
     * @return A new instance of {@code SearchRecipeFragment}.
     */
    public static SearchRecipeFragment newInstance(Listener listener) {
        SearchRecipeFragment fragment = new SearchRecipeFragment();
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
        binding = FragmentSearchRecipeBinding.inflate(inflater, container, false);
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
        binding.backToCookbookButton.setOnClickListener(v -> onBackToCookbookClicked());
    }

    /**
     * Handles the click event for the search button. It triggers the search action if the query is valid.
     */
    public void onSearchButtonClicked() {
        String query = binding.searchQueryText.getText().toString().trim();

        if (listener != null && !query.isEmpty()) {
            listener.onSearchRecipe(query);
        } else {
            binding.errorText.setText("Please enter a valid search query.");
        }
    }


    /**
     * Handles the click event for the Back to Cookbook button. It triggers navigation back to the cookbook view.
     */
    public void onBackToCookbookClicked() {
        if (listener != null) {
            listener.onSearchDone();
        }
    }

    /**
     * Displays an error message indicating that no recipes were found.
     */
    public void showRecipeNotFoundError() {
        Snackbar.make(binding.getRoot(), "No recipes found", Snackbar.LENGTH_LONG).show();
    }
}
