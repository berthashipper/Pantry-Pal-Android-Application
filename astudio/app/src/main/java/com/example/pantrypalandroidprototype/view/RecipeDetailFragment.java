package com.example.pantrypalandroidprototype.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.databinding.FragmentRecipeDetailBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;

public class RecipeDetailFragment extends Fragment implements IRecipeDetailView, EditTagDialogFragment.TagActionListener {
    static final String TAG = RecipeDetailFragment.class.getSimpleName();
    static final String ARG_RECIPE = "recipe";
    Recipe recipe;
    Listener listener;
    EditTagDialogFragment.TagActionListener tagActionListener;
    FragmentRecipeDetailBinding binding;
    ControllerActivity controller;
    static final int REQUEST_EDIT_COOK_TIME = 1;
    static final int REQUEST_EDIT_SERVING_SIZE = 2;
    static final int REQUEST_ADD_TAG = 3;
    static final int REQUEST_DELETE_TAG = 4;


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ControllerActivity) {
            controller = (ControllerActivity) context;
            listener = (Listener) context;
            // Set tagActionListener to the parent activity or fragment
            if (context instanceof EditTagDialogFragment.TagActionListener) {
                tagActionListener = (EditTagDialogFragment.TagActionListener) context;
            } else {
                Log.e(TAG, "Parent activity does not implement TagActionListener");
            }
        } else {
            throw new RuntimeException(context.toString() + " must be an instance of ControllerActivity");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the binding object
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);

        binding.recipeName.setText(recipe.getRecipeName());
        binding.recipeDescription.setText(recipe.getRecipeDescription());
        Log.d(TAG, "Recipe details set: " + recipe.getRecipeName() + ", " + recipe.getRecipeDescription());
        binding.recipeCookTime.setText("Cook Time: " + formatCookTime(recipe.cookTime));
        binding.recipeServingSize.setText("Serves: " + recipe.servingSize);

        // Add ingredients to the layout
        for (Ingredient ingredient : recipe.getIngredients()) {
            Log.d(TAG, "Adding ingredient: " + ingredient.getName());
            TextView ingredientView = new TextView(getContext());
            ingredientView.setText(ingredient.getQuantity() + " " + ingredient.getUnit() + " of " + ingredient.getName());
            binding.ingredientsLayout.addView(ingredientView);
        }

        // Add dietary tags to the layout dynamically
        if (recipe.getTags() != null && !recipe.getTags().isEmpty()) {
            Log.d(TAG, "Recipe tags found: " + recipe.getTags());
            for (Ingredient.dietary_tags tag : recipe.getTags()) {
                String tagString = tag.name();
                Log.d(TAG, "Processing tag: " + tagString);

                // Create a new TextView for each tag
                TextView tagView = new TextView(getContext());
                tagView.setText(tagString);  // Display the tag name
                tagView.setTextSize(16);  // Set font size for readability
                tagView.setTextColor(Color.WHITE);  // Set text color to white
                tagView.setPadding(20, 20, 20, 20);  // Add padding for spacing inside the tag
                tagView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tagView.setBackgroundResource(R.drawable.circular_tag_background);  // Apply circular background

                // Add margin between tags
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
                params.setMargins(8, 0, 8, 0); // Set margin to add spacing between tags
                tagView.setLayoutParams(params);

                tagView.setOnClickListener(v -> {
                    showDeleteTagConfirmationDialog(tag);  // Show confirmation dialog before deletion
                });

                // Add the tag view to the tags layout
                binding.tagsLayout.addView(tagView);
            }
        } else {
            Log.d(TAG, "No tags found for recipe: " + recipe.getRecipeName());
            TextView noTagsView = new TextView(getContext());
            noTagsView.setText("No tags available");
            noTagsView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            binding.tagsLayout.addView(noTagsView);
        }


        // Set the recipe instructions
        binding.recipeInstructions.setText(recipe.instructions);

        //Set up the "Edit" button to navigate to AddRecipeIngredientFragment
        binding.editRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                Log.d(TAG, "Listener triggered");
                listener.onEditRecipeIngredients();
            }
        });

        //Set up the "Add" button to navigate to AddRecipeIngredientFragment
        binding.addRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddRecipeIngredients();
            }
        });

        // Set up the "Delete" button to navigate back to DeleteRecipeIngredientFragment
        binding.deleteRecipeIngredient.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteRecipeIngredients();  // Notify listener when Done is pressed
            }
        });

        // Set up the "Edit" button to navigate back to EditInstructionFragment
        binding.editInstruction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditInstructions(recipe.getInstructions()); // Pass current instructions
            }
        });

        // Set up the "Done" button to navigate back to CookbookFragment
        binding.doneButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDoneViewingRecipe();  // Notify listener when Done is pressed
            }
        });

        // Set up the "Scale" button to navigate to ScaleRecipeFragment
        binding.scaleButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onScaleRecipeMenu();  // Notify listener when Scale is pressed
            }
        });

        // Edit Cook Time
        binding.editCookTime.setOnClickListener(v -> {
            Log.d(TAG, "Edit Cook Time button clicked");
            showEditDialog(REQUEST_EDIT_COOK_TIME);
        });

        // Edit Serving Size
        binding.editServingSize.setOnClickListener(v -> {
            Log.d(TAG, "Edit Serving Size button clicked");
            showEditDialog(REQUEST_EDIT_SERVING_SIZE);
        });

        // Set up the "Add Tag" button
        binding.addTagButton.setOnClickListener(v -> {
            Log.d(TAG, "Add Tag button clicked");
            showEditDialog(REQUEST_ADD_TAG);
        });

        // Set up the "Delete Tag" button
        binding.deleteTagButton.setOnClickListener(v -> {
            showEditDialog(REQUEST_DELETE_TAG);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Register FragmentResultListener
        getParentFragmentManager().setFragmentResultListener("edit_request", this, (requestKey, result) -> {
            int requestCode = result.getInt("request_code");
            String newValue = result.getString("new_value");

            Log.d(TAG, "Received result: requestCode=" + requestCode + ", newValue=" + newValue);

            if (requestCode == REQUEST_EDIT_COOK_TIME) {
                try {
                    long newCookTimeMinutes = Long.parseLong(newValue);
                    Duration newCookTime = Duration.ofMinutes(newCookTimeMinutes);
                    controller.setCookTime(newCookTime, recipe);
                    binding.recipeCookTime.setText(newCookTimeMinutes + " mins");
                    Snackbar.make(getView(), "Cook time updated to " + newCookTimeMinutes + " mins", Snackbar.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid input for cook time: " + newValue, e);
                    Snackbar.make(getView(), "Invalid input for cook time", Snackbar.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_EDIT_SERVING_SIZE) {
                controller.setServingSize(Integer.parseInt(newValue), recipe);
                binding.recipeServingSize.setText(newValue + " servings");
                Snackbar.make(getView(), "Yield updated to " + newValue, Snackbar.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_ADD_TAG) {
                // Add the new tag to the recipe
                try {
                    Ingredient.dietary_tags newTag = Ingredient.dietary_tags.valueOf(newValue.toUpperCase());
                    controller.addTagToRecipe(recipe, newTag);  // Call controller to add the tag and persist
                    recipe.addTag(newTag);
                    Snackbar.make(getView(), "Tag added: " + newTag, Snackbar.LENGTH_SHORT).show();
                    updateTagsUI();
                } catch (IllegalArgumentException e) {
                    Snackbar.make(getView(), "Invalid tag entered", Snackbar.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_DELETE_TAG) {
                // Handle tag deletion from the dialog result
                Ingredient.dietary_tags tagToDelete = Ingredient.dietary_tags.valueOf(newValue.toUpperCase());
                deleteTag(tagToDelete);  // Delete the tag from the recipe
            }
        });
    }

    public String formatCookTime(Duration cookTime) {
        if (cookTime != null) {
            long minutes = cookTime.toMinutes(); // Convert Duration to minutes
            return minutes + " minutes";
        }
        return "Not specified";
    }

    public void showEditDialog(int requestCode) {
        Log.d(TAG, "Showing EditDialog with requestCode=" + requestCode);
        EditDialogFragment dialog = EditDialogFragment.newInstance(requestCode, recipe);
        dialog.show(getParentFragmentManager(), "EditDialog");
    }

    private void showDeleteTagConfirmationDialog(Ingredient.dietary_tags tag) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Tag")
                .setMessage("Are you sure you want to delete the tag '" + tag.name() + "'?")
                .setPositiveButton("Yes", (dialog, which) -> deleteTag(tag))  // Confirm deletion
                .setNegativeButton("No", null)  // Cancel deletion
                .show();
    }

    private void deleteTag(Ingredient.dietary_tags tag) {
        Log.d(TAG, "Attempting to delete tag: " + tag.name());

        // Remove the tag from the recipe object
        boolean tagRemoved = recipe.removeTag(tag);
        Log.d(TAG, "Tag removed from recipe: " + tag.name() + ", Success: " + tagRemoved);

        if (tagRemoved) {
            // Update the UI to reflect the removal
            updateTagsUI(); // This method updates the UI after a tag is removed
            Snackbar.make(getView(), "Tag '" + tag.name() + "' deleted", Snackbar.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Failed to remove tag: " + tag.name());
            Snackbar.make(getView(), "Failed to delete tag", Snackbar.LENGTH_SHORT).show();
        }
    }


    // Method to update the tags UI dynamically
    private void updateTagsUI() {
        binding.tagsLayout.removeAllViews();

        if (recipe.getTags() != null && !recipe.getTags().isEmpty()) {
            Log.d(TAG, "Re-adding tags to the UI: " + recipe.getTags());
            for (Ingredient.dietary_tags tag : recipe.getTags()) {
                TextView tagView = new TextView(getContext());
                tagView.setText(tag.name());
                Log.d(TAG, "Adding tag to UI: " + tag.name());
                tagView.setTextSize(16);
                tagView.setTextColor(Color.WHITE);
                tagView.setPadding(20, 10, 20, 10);
                tagView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tagView.setBackgroundResource(R.drawable.circular_tag_background);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
                params.setMargins(8, 0, 8, 0);
                tagView.setLayoutParams(params);

                // Set onClickListener to handle tag deletion
                tagView.setOnClickListener(v -> {
                    showDeleteTagConfirmationDialog(tag);  // Show confirmation dialog before deletion
                });

                // Add tag view to the layout
                binding.tagsLayout.addView(tagView);
            }
        } else {
            TextView noTagsView = new TextView(getContext());
            noTagsView.setText("No tags available");
            noTagsView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            binding.tagsLayout.addView(noTagsView);
        }
    }

    @Override
    public void onDialogEditDone(int requestCode, String newValue) {
        Log.d(TAG, "Dialog edit done for request code: " + requestCode + ", new value: " + newValue);
        if (requestCode == REQUEST_EDIT_COOK_TIME) {
            try {
                // Parse the input as an integer (representing minutes)
                long newCookTimeMinutes = Long.parseLong(newValue);
                Log.d(TAG, "New cook time (minutes): " + newCookTimeMinutes);
                Duration newCookTime = Duration.ofMinutes(newCookTimeMinutes);

                // Update the cook time via controller
                controller.setCookTime(newCookTime, recipe);

                // Update the UI
                binding.recipeCookTime.setText(newCookTimeMinutes + " mins");
                Snackbar.make(getView(), "Cook time updated to " + newCookTimeMinutes + " mins", Snackbar.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid input for cook time: " + newValue, e);
                // Handle invalid input (non-numeric)
                Snackbar.make(getView(), "Invalid input for cook time", Snackbar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_EDIT_SERVING_SIZE) {
            Log.d(TAG, "Updating serving size to: " + newValue);
            controller.setServingSize(Integer.parseInt(newValue), recipe);
            binding.recipeServingSize.setText(newValue + " servings");
            Snackbar.make(getView(), "Yield updated to " + newValue, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onBackToRecipe() {
        controller.onBackToRecipe(recipe);  // Delegate to controller
    }

    @Override
    public void onTagAdded(Recipe recipe, String newTag) {
        // Clear the existing tags and add the updated ones
        binding.tagsLayout.removeAllViews();

        // Add the updated tags to the layout
        if (recipe.getTags() != null && !recipe.getTags().isEmpty()) {
            for (Ingredient.dietary_tags tag : recipe.getTags()) {
                TextView tagView = new TextView(getContext());
                tagView.setText(tag.name());
                tagView.setTextSize(16);
                tagView.setTextColor(Color.WHITE);
                tagView.setPadding(20, 20, 20, 20);
                tagView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tagView.setBackgroundResource(R.drawable.circular_tag_background);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
                params.setMargins(8, 8, 8, 8);
                tagView.setLayoutParams(params);

                binding.tagsLayout.addView(tagView);
            }
        } else {
            TextView noTagsView = new TextView(getContext());
            noTagsView.setText("No tags available");
            noTagsView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            binding.tagsLayout.addView(noTagsView);
        }
    }
}