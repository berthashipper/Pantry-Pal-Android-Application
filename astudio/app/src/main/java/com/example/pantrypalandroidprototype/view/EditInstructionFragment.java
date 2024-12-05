package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentEditInstructionBinding;
import com.example.pantrypalandroidprototype.model.RecipeBuilder;
import com.google.android.material.snackbar.Snackbar;

public class EditInstructionFragment extends Fragment implements IEditInstructionView {
    FragmentEditInstructionBinding binding;
    Listener listener;

    /**
     * Creates a new instance of the fragment with a listener.
     *
     * @param listener The listener to handle interactions from this fragment.
     * @return A new instance of the fragment.
     */
    public static EditInstructionFragment newInstance(Listener listener, String currentInstructions) {
        EditInstructionFragment fragment = new EditInstructionFragment();
        fragment.listener = listener;
        Bundle args = new Bundle();
        args.putString("currentInstructions", currentInstructions);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to create the view hierarchy of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditInstructionBinding.inflate(inflater, container, false);
        String currentInstructions = getArguments().getString("currentInstructions", "");
        binding.instructionEditText.setText(currentInstructions); // Pre-fill the input
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created.
     * Sets up button click listeners and other view-related logic.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.addButton.setOnClickListener(v -> onAddInstructionButtonClicked());
        //binding.editButton.setOnClickListener(v -> onEditInstructionButtonClicked());
        binding.doneButton.setOnClickListener(v -> onInstructionSubmitted());

        binding.backToRecipeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackToRecipe();
            }
        });
    }

    private void onInstructionSubmitted() {
        String instruction = binding.instructionEditText.getText().toString().trim();

        if (instruction.isEmpty()) {
            showError("Please enter a valid instruction");
            return;
        }

        if (listener != null) {
            listener.onEditInstruction(instruction);
        }

        showInstructionAddedMessage("Instruction updated");

        // Notify the listener to go back to the recipe detail view
        if (listener != null) {
            listener.onBackToRecipe();
        }
        clearInstructionField();
    }

    /**
     * Clears the input field for the instruction.
     */
    private void clearInstructionField() {
        binding.instructionEditText.setText("");
    }

    /**
     * Displays a Snackbar message indicating a successful addition of the instruction.
     *
     * @param message The instruction that was added.
     */
    private void showInstructionAddedMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
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
