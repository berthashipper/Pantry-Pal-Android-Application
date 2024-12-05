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

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

public class EditTagDialogFragment extends DialogFragment {
    static final String ARG_RECIPE = "recipe";
    Recipe recipe;
    private TagActionListener tagActionListener;

    public static EditTagDialogFragment newInstance(Recipe recipe) {
        EditTagDialogFragment fragment = new EditTagDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TagActionListener) {
            tagActionListener = (TagActionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TagActionListener");
        }
    }

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
                recipe.addTag(Ingredient.dietary_tags.valueOf(newTag));  // Add the new tag to the recipe
                if (tagActionListener != null) {
                    tagActionListener.onTagAdded(recipe, newTag);  // Notify listener to update the UI
                }
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Register FragmentResultListener for the "Add Tag" dialog
        getParentFragmentManager().setFragmentResultListener("edit_request", this, (requestKey, result) -> {
            int requestCode = result.getInt("request_code");
            String newTag = result.getString("new_tag"); // Get the new tag added

            Log.d(TAG, "Received result: requestCode=" + requestCode + ", newTag=" + newTag);

            if (requestCode == REQUEST_ADD_TAG) {
                // Handle adding the tag to the recipe (e.g., add the tag to the recipe object)
                if (newTag != null && !newTag.isEmpty()) {
                    recipe.addTag(Ingredient.dietary_tags.valueOf(newTag)); // Update the recipe's tags
                    Snackbar.make(getView(), "Tag '" + newTag + "' added", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Invalid tag input: " + newTag);
                    Snackbar.make(getView(), "Invalid tag input", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface TagActionListener {
        void onTagAdded(Recipe recipe, String newTag);
    }
}
