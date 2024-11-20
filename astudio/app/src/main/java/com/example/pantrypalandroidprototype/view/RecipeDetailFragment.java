package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Recipe;

import java.time.Duration;

public class RecipeDetailFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private Recipe recipe;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Find the TextViews for the UI components
        TextView recipeName = view.findViewById(R.id.recipe_name);
        TextView recipeDescription = view.findViewById(R.id.recipe_description);
        TextView recipeCookTime = view.findViewById(R.id.recipe_cook_time);
        TextView recipeServingSize = view.findViewById(R.id.recipe_serving_size);
        LinearLayout ingredientsLayout = view.findViewById(R.id.ingredients_layout);
        TextView recipeInstructions = view.findViewById(R.id.recipe_instructions);

        // Set the recipe details in the corresponding views
        recipeName.setText(recipe.recipeName);
        recipeDescription.setText(recipe.recipeDescription);
        recipeCookTime.setText("Cook Time: " + formatCookTime(recipe.cookTime));
        recipeServingSize.setText("Serves: " + recipe.servingSize);

        // Dynamically add ingredients to the layout
        for (Ingredient ingredient : recipe.getIngredients()) {
            TextView ingredientView = new TextView(getContext());
            ingredientView.setText(ingredient.getQuantity() + " " + ingredient.getUnit() + " of " + ingredient.getName());
            ingredientsLayout.addView(ingredientView);
        }

        // Set the recipe instructions
        recipeInstructions.setText(recipe.instructions);

        return view;
    }

    private String formatCookTime(Duration cookTime) {
        if (cookTime != null) {
            long minutes = cookTime.toMinutes();
            return minutes + " minutes";
        }
        return "Not specified";
    }
}

