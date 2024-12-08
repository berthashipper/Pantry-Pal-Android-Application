package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentFilterRecipeBinding;
import com.google.android.material.snackbar.Snackbar;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.List;

public class FilterRecipeFragment extends Fragment implements IFilterRecipeView {
    FragmentFilterRecipeBinding binding;
    Listener listener;

    /**
     * Creates a new instance of {@code FilterRecipeFragment} with the specified listener.
     *
     * @param listener The listener to handle filter events.
     * @return A new instance of {@code FilterRecipeFragment}.
     */
    public static FilterRecipeFragment newInstance(Listener listener) {
        FilterRecipeFragment fragment = new FilterRecipeFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to inflate the fragment's layout. It sets up the view binding for the fragment.
     *
     * @param inflater           The LayoutInflater used to inflate the view.
     * @param container          The parent view group that the fragment's UI will be attached to.
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created. This method sets up the listeners for filter and reset buttons.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState A bundle containing the fragment's previously saved state, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.applyFiltersButton.setOnClickListener(v -> onApplyFiltersClicked());
    }

    /**
     * Populates the dietary preference spinner with a list of tags.
     *
     * @param tags The list of dietary tags to display.
     */
    public void populateTags(List<String> tags) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                tags
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dietaryPreferenceSpinner.setAdapter(adapter);
    }

    /**
     * Handles the click event for the Apply Filters button. It triggers the filtering action.
     */
    public void onApplyFiltersClicked() {
        String selectedTag = binding.dietaryPreferenceSpinner.getSelectedItem().toString();
        if (listener != null) {
            listener.onFilterRecipes(selectedTag);
        }
    }

    /**
     * Displays a message indicating that no matching recipes were found.
     */
    public void showNoRecipesFoundError() {
        Toast.makeText(requireContext(), "No matching recipes found", Toast.LENGTH_LONG).show();
    }
}