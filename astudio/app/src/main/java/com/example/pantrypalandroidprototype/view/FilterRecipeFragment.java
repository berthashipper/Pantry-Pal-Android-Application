package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentFilterRecipeBinding;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    public static FilterRecipeFragment newInstance(Listener listener, List<String> tags) {
        FilterRecipeFragment fragment = new FilterRecipeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("tags", new ArrayList<>(tags));
        fragment.setArguments(args);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.applyFiltersButton.setOnClickListener(v -> onApplyFiltersClicked());
        binding.backToCookbookIcon.setOnClickListener(v -> onBackToCookbookIconClicked());

        // Handle population of tags after the fragment's view is created
        if (getArguments() != null) {
            List<String> tags = getArguments().getStringArrayList("tags");
            if (tags != null) {
                populateTags(tags);
            }
        }
    }

    /**
     * Populates the dietary preference spinner with a sorted list of tags.
     *
     * @param tags The list of dietary tags to display.
     */
    public void populateTags(List<String> tags) {
        if (binding == null) {
            Log.e("FilterRecipeFragment", "Binding is null, cannot populate spinner.");
            return;
        }
        // Sort the tags
        Collections.sort(tags);

        List<String> modifiedTags = new ArrayList<>();
        modifiedTags.add("Choose tag"); // Default option
        modifiedTags.addAll(tags);

        // Adapter initialization
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                modifiedTags
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.dietaryPreferenceSpinner.setAdapter(adapter);

        // Reset spinner selection safely
        binding.dietaryPreferenceSpinner.setSelection(0);

        binding.dietaryPreferenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    // Default selection logic to prevent filtering when no valid tag is selected
                    Log.d("FilterRecipeFragment", "Spinner selected default 'Choose tag'");
                } else {
                    Log.d("FilterRecipeFragment", "Spinner selection: " + parentView.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("FilterRecipeFragment", "Spinner nothing selected.");
            }
        });
    }

    /**
     * Handles the click event for the Apply Filters button. It triggers the filtering action.
     */
    public void onApplyFiltersClicked() {
        if (binding == null || binding.dietaryPreferenceSpinner.getSelectedItem() == null) {
            Log.e("FilterRecipeFragment", "Spinner state invalid or null");
            return;
        }

        String selectedTag = binding.dietaryPreferenceSpinner.getSelectedItem().toString();
        if (listener != null) {
            listener.onFilterRecipes(selectedTag);
        }
    }

    /**
     * Handles navigation back to the cookbook view.
     * Notifies the listener or performs a navigation action to return to the cookbook.
     */
    public void onBackToCookbookIconClicked() {
        // Notify the listener or navigate back to the cookbook
        if (listener != null) {
            listener.onNavigateToCookbook();
        }
    }

    /**
     * Displays a message indicating that no matching recipes were found.
     */
    public void showNoRecipesFoundError() {
        if (binding != null && binding.getRoot() != null) {
            Snackbar.make(binding.getRoot(), "Please select a tag", Snackbar.LENGTH_SHORT).show();
        } else {
            Log.e("FilterRecipeFragment", "Binding is null, unable to show error.");
        }
    }
}