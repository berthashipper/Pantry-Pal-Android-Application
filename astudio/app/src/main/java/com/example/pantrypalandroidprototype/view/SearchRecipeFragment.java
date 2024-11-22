package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.databinding.FragmentSearchRecipeBinding;
import com.google.android.material.snackbar.Snackbar;

public class SearchRecipeFragment extends Fragment implements ISearchRecipeView {
    FragmentSearchRecipeBinding binding;
    Listener listener;

    public static SearchRecipeFragment newInstance(Listener listener) {
        SearchRecipeFragment fragment = new SearchRecipeFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchButton.setOnClickListener(v -> onSearchButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    public void onSearchButtonClicked() {
        String query = binding.searchQueryText.getText().toString().trim();

        if (listener != null && !query.isEmpty()) {
            listener.onSearchRecipe(query);
        } else {
            binding.errorText.setText("Please enter a valid search query.");
        }
    }

    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onSearchDone();
        }
    }

    public void showRecipeNotFoundError() {
        Snackbar.make(getView(), "No recipes found", Snackbar.LENGTH_LONG).show();
    }
}
