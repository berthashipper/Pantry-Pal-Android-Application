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

public class SearchIngredientFragment extends Fragment implements ISearchIngredientView{
    FragmentSearchIngredientBinding binding;
    Listener listener;

    public static SearchIngredientFragment newInstance(Listener listener) {
        SearchIngredientFragment fragment = new SearchIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchIngredientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchButton.setOnClickListener(v -> onSearchButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    private void onSearchButtonClicked() {
        String query = binding.searchQueryText.getText().toString().trim();

        if (listener != null && !query.isEmpty()) {
            listener.onSearchIngredient(query);
        } else {
            binding.errorText.setText("Please enter a valid search query.");
        }
    }

    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onSearchIngredientDone();
        }
    }

    public void showIngredientNotFoundError() {
        Snackbar.make(getView(), "No ingredients found", Snackbar.LENGTH_LONG).show();
    }

    public void displayFoundIngredients(List<Ingredient> ingredients) {
        // Display the found ingredients in a list or text view
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            result.append(ingredient.toString()).append("\n");
        }
        binding.searchResultsTextView.setText(result.toString());
    }
}
