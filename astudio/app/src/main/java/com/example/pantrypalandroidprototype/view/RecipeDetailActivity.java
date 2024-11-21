package com.example.pantrypalandroidprototype.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeName, recipeDescription, recipeInstructions, recipeCookTime, recipeServingSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeName = findViewById(R.id.recipe_name);
        recipeDescription = findViewById(R.id.recipe_description);
        recipeInstructions = findViewById(R.id.recipe_instructions);
        recipeCookTime = findViewById(R.id.recipe_cook_time);
        recipeServingSize = findViewById(R.id.recipe_serving_size);
        Button addToCookbookButton = findViewById(R.id.add_to_cookbook_button);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        if (recipe == null) {
            Log.e("RecipeDetailActivity", "Recipe is null!");
            return;
        }

        // Set the recipe details in the TextViews
        recipeName.setText(recipe.recipeName);
        recipeDescription.setText(recipe.recipeDescription);
        recipeInstructions.setText(String.join("\n", recipe.instructions));
        recipeCookTime.setText("Cook Time: " + recipe.cookTime.toMinutes() + " minutes");
        recipeServingSize.setText("Serving Size: " + recipe.servingSize);

        // Handle Add to Cookbook button click
        addToCookbookButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("recipe", recipe);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

}
