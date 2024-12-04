package com.example.pantrypalandroidprototype.view;
import androidx.fragment.app.FragmentResultListener;

import static com.example.pantrypalandroidprototype.view.RecipeDetailFragment.REQUEST_EDIT_COOK_TIME;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(requestCode == REQUEST_EDIT_COOK_TIME ? "Edit Cook Time" : "Edit Serving Size");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Set input based on the type of edit
        if (requestCode == REQUEST_EDIT_COOK_TIME) {
            long cookTimeMinutes = recipe.getCookTime().toMinutes();
            input.setText(String.valueOf(cookTimeMinutes));
        } else {
            input.setText(String.valueOf(recipe.getServingSize()));
        }

        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = input.getText().toString();
            if (!newValue.isEmpty()) {
                Bundle result = new Bundle();
                result.putInt("request_code", requestCode);
                result.putString("new_value", newValue);

                // Send the result back to the parent fragment
                requireActivity().getSupportFragmentManager()
                        .setFragmentResult("edit_request", result);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}
