package com.example.pantrypalandroidprototype.view;
import androidx.fragment.app.FragmentResultListener;

import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_ADD_TAG;
import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_DELETE_TAG;
import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_EDIT_COOK_TIME;
import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_EDIT_SERVING_SIZE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.pantrypalandroidprototype.model.Recipe;

public class EditDialogFragment extends DialogFragment {
    static final String ARG_REQUEST_CODE = "request_code";
    static final String ARG_RECIPE = "recipe";
    int requestCode;
    Recipe recipe;

    public static EditDialogFragment newInstance(int requestCode, Recipe recipe) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_REQUEST_CODE, requestCode);
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            requestCode = getArguments().getInt(ARG_REQUEST_CODE);
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
        }

        Log.d("EditDialogFragment", "onCreateDialog: requestCode=" + requestCode + ", recipe=" + (recipe != null ? recipe.getRecipeName() : "null"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(requestCode == REQUEST_EDIT_COOK_TIME ? "Edit Cook Time" :
                (requestCode == REQUEST_EDIT_SERVING_SIZE ? "Edit Serving Size" :
                        (requestCode == REQUEST_ADD_TAG ? "Add Tag" : "Enter Tag to Delete")));

        final EditText input = new EditText(getActivity());

        // Adjust input type based on requestCode
        if (requestCode == REQUEST_ADD_TAG) {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Enter a tag");
        } else if (requestCode == REQUEST_EDIT_COOK_TIME) {
            long cookTimeMinutes = recipe.getCookTime().toMinutes();
            input.setText(String.valueOf(cookTimeMinutes));
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (requestCode == REQUEST_EDIT_SERVING_SIZE) {
            input.setText(String.valueOf(recipe.getServingSize()));
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (requestCode == REQUEST_DELETE_TAG) {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Enter a tag");
        }

        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = input.getText().toString();
            if (!newValue.isEmpty()) {
                Bundle result = new Bundle();
                result.putInt("request_code", requestCode);
                result.putString("new_value", newValue);
                Log.d("EditDialogFragment", "Save clicked: requestCode=" + requestCode + ", newValue=" + newValue);

                requireActivity().getSupportFragmentManager()
                        .setFragmentResult("edit_request", result);
            } else {
                Log.d("EditDialogFragment", "Save clicked but input is empty");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}