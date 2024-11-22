package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentEditItemsBinding;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

public class EditIngredientFragment extends Fragment implements IEditIngredientView {
    FragmentEditItemsBinding binding;
    Listener listener;
    Pantry pantry;

    public static EditIngredientFragment newInstance(Listener listener) {
        EditIngredientFragment fragment = new EditIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editButton.setOnClickListener(v -> onEditButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    public void onEditButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        double newQty;

        try {
            newQty = Double.parseDouble(binding.itemQuantityText.getText().toString().trim());
        } catch (NumberFormatException e) {
            Snackbar.make(binding.getRoot(), "Invalid quantity.", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (listener != null && !name.isEmpty()) {
            listener.onEditIngredient(name, newQty);
        }
        clearInputs();
    }

    public void showIngredientUpdateMessage(String name) {
        Snackbar.make(binding.getRoot(), "Updated quantity " + name, Snackbar.LENGTH_LONG).show();
    }

    public void showIngredientNotFoundError(String name) {
        Snackbar.make(binding.getRoot(), name + " does not exist in your pantry", Snackbar.LENGTH_LONG).show();
    }


    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditDone();
        }
        Snackbar.make(getView(), "Returning to Pantry", Snackbar.LENGTH_LONG).show();
    }

    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
    }

    public void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}