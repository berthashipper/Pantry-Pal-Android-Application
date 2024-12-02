package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.databinding.FragmentDeleteItemsBinding;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

/**
 * The {@code DeleteIngredientFragment} class provides functionality for deleting ingredients from the user's pantry.
 * It implements the {@link IDeleteIngredientView} interface and handles user interactions within the delete ingredient UI.
 */
public class DeleteIngredientFragment extends Fragment implements IDeleteIngredientView {
    /**
     * View binding for accessing the UI elements of the fragment.
     */
    FragmentDeleteItemsBinding binding;
    /**
     * Listener for handling fragment interactions, such as deleting ingredients or completing the deletion process.
     */
    IDeleteIngredientView.Listener listener;

    /**
     * Creates a new instance of {@code DeleteIngredientFragment} with a specified listener.
     *
     * @param listener The listener to handle fragment interactions.
     * @return A new instance of {@code DeleteIngredientFragment}.
     */
    public static DeleteIngredientFragment newInstance(IDeleteIngredientView.Listener listener) {
        DeleteIngredientFragment fragment = new DeleteIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }


    /**
     * Called to create the fragment's view hierarchy.
     *
     * @param inflater  The {@link LayoutInflater} object for inflating views.
     * @param container The parent view group for the fragment's UI.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     * @return The root view of the fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeleteItemsBinding.inflate(inflater, container, false); // Inflate the correct layout binding
        View rootView = binding.getRoot();
        return rootView;
    }

    /**
     * Called immediately after the fragment's view has been created.
     *
     * @param view The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.deleteIngredientButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());
    }

    /**
     * Handles the delete button click event to remove an ingredient.
     * Validates the input and notifies the listener to perform the deletion.
     */
    public void onDeleteButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();

        if (listener != null && !name.isEmpty()) {
            listener.onDeleteIngredient(name);
        }
        clearInputs();
    }

    /**
     * Displays a Snackbar message indicating that the specified ingredient has been successfully deleted.
     *
     * @param name The name of the deleted ingredient.
     */
    public void showIngredientDeletedMessage(String name) {
        Snackbar.make(getView(), "Deleted ingredient: " + name, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays a Snackbar message indicating that the specified ingredient was not found in the pantry.
     *
     * @param name The name of the ingredient that was not found.
     */
    public void showIngredientNotFoundError(String name) {
        Snackbar.make(binding.getRoot(), name + " does not exist in your pantry", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Updates the pantry display with the latest information and notifies the user.
     *
     * @param pantry The updated pantry object containing the current list of ingredients.
     */
    @Override
    public void updatePantryDisplay(@NonNull final Pantry pantry) {
        Toast.makeText(getContext(), "Pantry updated with " + pantry.getNPantryItems() + " items.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Clears the input fields in the UI after an operation.
     */
    public void clearInputs() {
        binding.itemNameText.setText("");
    }

    /**
     * Handles the event when the Done button is clicked.
     * Notifies the listener that the deletion process is complete and returns to the pantry view.
     */
    public void onDoneButtonClicked() {
        if (listener != null) {
            listener.onDeletionDone();
        }
        Snackbar.make(getView(), "Returning to Pantry" , Snackbar.LENGTH_LONG).show();
    }
}
