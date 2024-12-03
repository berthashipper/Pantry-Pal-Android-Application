package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentDeleteRecipeIngredientBinding;
import com.google.android.material.snackbar.Snackbar;

public class DeleteRecipeIngredientFragment extends Fragment implements IDeleteRecipeIngredientView {

    private FragmentDeleteRecipeIngredientBinding binding;
    private Listener listener;

    /**
     * Creates a new instance of the fragment with a listener.
     *
     * @param listener The listener to handle interactions from this fragment.
     * @return A new instance of the fragment.
     */
    public static DeleteRecipeIngredientFragment newInstance(Listener listener) {
        DeleteRecipeIngredientFragment fragment = new DeleteRecipeIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create the view hierarchy of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeleteRecipeIngredientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created.
     * Sets up button click listeners and other view-related logic.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.deleteButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handles the delete button click event.
     * Validates input and notifies the listener to remove the ingredient.
     */
    private void onDeleteButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();

        if (name.isEmpty()) {
            showError("Please enter the ingredient name.");
            return;
        }

        if (listener != null) {
            listener.onDeleteRecipeIngredient(name);
            showIngredientDeletedMessage(name);
            clearInputs();
        }
    }

    /**
     * Handles the Done button click event.
     * Notifies the listener that the deleting process is complete.
     */
    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onDeleteRecipeDone();
        }
        Snackbar.make(binding.getRoot(), "Returning to Recipe Details", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Clears the input field for the ingredient name.
     */
    private void clearInputs() {
        binding.itemNameText.setText("");
    }

    /**
     * Displays a Snackbar message indicating a successful deletion for the ingredient.
     *
     * @param name The name of the deleted ingredient.
     */
    private void showIngredientDeletedMessage(String name) {
        Snackbar.make(binding.getRoot(), "Successfully deleted " + name, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays a Snackbar message with an error.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}
