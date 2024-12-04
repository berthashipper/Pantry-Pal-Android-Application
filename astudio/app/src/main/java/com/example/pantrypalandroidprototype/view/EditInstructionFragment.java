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

    private FragmentEditInstructionBinding binding;
    private Listener listener;

    /**
     * Creates a new instance of the fragment with a listener.
     *
     * @param listener The listener to handle interactions from this fragment.
     * @return A new instance of the fragment.
     */
    public static EditInstructionFragment newInstance(Listener listener) {
        EditInstructionFragment fragment = new EditInstructionFragment();
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create the view hierarchy of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditInstructionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created.
     * Sets up button click listeners and other view-related logic.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addButton.setOnClickListener(v -> onAddInstructionButtonClicked());
        binding.editButton.setOnClickListener(v -> onEditInstructionButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    private void onAddInstructionButtonClicked() {
        String instruction = binding.instructionEditText.getText().toString().trim();

        if (instruction.isEmpty()) {
            showError("Please enter a valid instruction");
            return;
        }

        // Add instruction to the recipe model
        if (listener != null) {
            listener.onAddInstruction(instruction);
        }

        // Notify user and clear the input field
        showInstructionAddedMessage(instruction);
        clearInstructionField();
    }


    /**
     * Handles the Add Instruction button click event.
     * Validates input and notifies the listener to add the instruction.
     */
    private void onEditInstructionButtonClicked() {
        String instruction = binding.instructionEditText.getText().toString().trim();

        if (instruction.isEmpty()) {
            showError("Please enter a valid instruction");
            return;
        }

        // Add instruction to the recipe model
        if (listener != null) {
            listener.onEditInstruction(instruction);
        }

        // Notify user and clear the input field
        showInstructionAddedMessage(instruction);
        clearInstructionField();
    }


    /**
     * Handles the Done button click event.
     * Notifies the listener that the editing process is complete.
     */
    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onEditInstructionDone();
        }
        Snackbar.make(binding.getRoot(), "Returning to Recipe Details", Snackbar.LENGTH_LONG).show();
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
     * @param instruction The instruction that was added.
     */
    private void showInstructionAddedMessage(String instruction) {
        Snackbar.make(binding.getRoot(), "Instruction added: " + instruction, Snackbar.LENGTH_LONG).show();
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
