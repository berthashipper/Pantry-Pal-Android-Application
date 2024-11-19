package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentDeleteItemsBinding; // Correct binding import
import com.example.pantrypalandroidprototype.model.Pantry;

public class DeleteIngredientFragment extends Fragment implements IDeleteIngredientView {

    // Use the correct binding class for the Delete Ingredient Fragment
    FragmentDeleteItemsBinding binding;
    IDeleteIngredientView.Listener listener;

    Pantry pantry;

    public static DeleteIngredientFragment newInstance(IDeleteIngredientView.Listener listener) {
        DeleteIngredientFragment fragment = new DeleteIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeleteItemsBinding.inflate(inflater, container, false); // Inflate the correct layout binding
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the delete button click listener
        binding.deleteIngredientButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handle the delete button click to delete an ingredient.
     */
    public void onDeleteButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();

        if (listener != null && !name.isEmpty()) {
            listener.onDeleteIngredient(name);
        }
        clearInputs();
    }

    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }

    public void clearInputs() {
        binding.itemNameText.setText("");
    }

    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onDeletionDone();
        }
    }
}
