package com.example.pantrypalandroidprototype.view;

import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_ADD_TAG;
import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

/**
 * A dialog fragment for adding tags to a recipe.
 * This fragment displays an input field where users can enter a new tag, which is then added to the recipe.
 * It notifies a {@link TagActionListener} to handle the tag addition and update the UI.
 */
public class EditTagDialogFragment extends DialogFragment {
    static final String ARG_RECIPE = "recipe";
    Recipe recipe;
    private TagActionListener tagActionListener;

    /**
     * Creates a new instance of the fragment with the specified recipe.
     *
     * @param recipe The {@link Recipe} to which the tag will be added.
     * @return A new instance of {@code EditTagDialogFragment}.
     */
    public static EditTagDialogFragment newInstance(Recipe recipe) {
        EditTagDialogFragment fragment = new EditTagDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is attached to a context.
     * Ensures the context implements {@link TagActionListener}.
     *
     * @param context The context to which the fragment is attached.
     * @throws RuntimeException if the context does not implement {@code TagActionListener}.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TagActionListener) {
            tagActionListener = (TagActionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TagActionListener");
        }
    }

    /**
     * Creates the dialog for adding a new tag.
     *
     * @param savedInstanceState If non-null, this fragment is being re-created from a previous state.
     * @return The dialog for adding a new tag.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Tag");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newTag = input.getText().toString();
            if (!newTag.isEmpty()) {
                recipe.addTag(newTag);  // Add the new tag to the recipe
                if (tagActionListener != null) {
                    tagActionListener.onTagAdded(recipe, newTag);  // Notify listener to update the UI
                }
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }

    /**
     * Sets up a result listener to handle results from related fragments, such as an "Add Tag" operation.
     *
     * @param view               The fragment's root view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Register FragmentResultListener for the "Add Tag" dialog
        getParentFragmentManager().setFragmentResultListener("edit_request", this, (requestKey, result) -> {
            int requestCode = result.getInt("request_code");
            String newTag = result.getString("new_tag"); // Get the new tag added

            Log.d(TAG, "Received result: requestCode=" + requestCode + ", newTag=" + newTag);

            if (requestCode == REQUEST_ADD_TAG) {
                if (newTag != null && !newTag.isEmpty()) {
                    recipe.addTag(newTag); // Update the recipe's tags
                    Snackbar.make(getView(), "Tag '" + newTag + "' added", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Invalid tag input: " + newTag);
                    Snackbar.make(getView(), "Invalid tag input", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Listener interface for handling tag-related actions.
     */
    public interface TagActionListener {
        /**
         * Called when a new tag is added to a recipe.
         *
         * @param recipe The {@link Recipe} to which the tag was added.
         * @param newTag The newly added tag.
         */
        void onTagAdded(Recipe recipe, String newTag);
    }
}
