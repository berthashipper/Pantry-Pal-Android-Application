package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentEditItemsBinding;
import com.google.android.material.snackbar.Snackbar;

public class EditIngredientFragment extends Fragment implements IEditIngredientView {
    FragmentEditItemsBinding binding;
    Listener listener;

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

    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditDone();
        }
    }

    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQuantityText.setText("");
    }
}
