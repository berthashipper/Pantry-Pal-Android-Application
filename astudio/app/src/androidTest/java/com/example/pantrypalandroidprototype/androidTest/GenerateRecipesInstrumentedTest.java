package com.example.pantrypalandroidprototype.androidTest;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying the "generateRecipe" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class GenerateRecipesInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests the recipe generation process for a Grilled Cheese Sandwich after
     * adding the required ingredients to the pantry. The test ensures that the
     * ingredients in the pantry match exactly, and verifies if the Grilled Cheese
     * recipe appears in the generated recipe list.
     */
    @org.junit.Test
    public void testGenerateRecipesForGrilledCheese() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Add 3 slices of bread
        AddIngredientsInstrumentedTest.addPantryIngredient("Bread", "3", "slices");
        // Add 4 slices of cheese
        AddIngredientsInstrumentedTest.addPantryIngredient("Cheese", "4", "slices");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Generate Recipes
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);


        // Verify that the Grilled Cheese recipe is displayed
        Espresso.onView(ViewMatchers.withText("Grilled Cheese Sandwich"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }


    /**
     * Tests the recipe generation process when ingredients are added that are
     * similar but not an exact match to the required ingredients for a recipe.
     * The test checks if the app still generates the appropriate recipe (e.g.,
     * Spaghetti Bolognese) even when the ingredients slightly differ.
     */
    @org.junit.Test
    public void testGenerateRecipesForComplexIngredients() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        AddIngredientsInstrumentedTest.addPantryIngredient("garlic", "3", "cloves");
        AddIngredientsInstrumentedTest.addPantryIngredient("beef", "2", "pounds");
        AddIngredientsInstrumentedTest.addPantryIngredient("spaghetti", "4", "cups");
        AddIngredientsInstrumentedTest.addPantryIngredient("oil", "2", "bottles");
        AddIngredientsInstrumentedTest.addPantryIngredient("onion", "3", " ");
        AddIngredientsInstrumentedTest.addPantryIngredient("sauce", "10", "cups");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Generate Recipes
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the Spaghetti Bolognese recipe is displayed even
        // though ingredients don't match exactly — this is good!
        Espresso.onView(ViewMatchers.withText("Spaghetti Bolognese"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Spaghetti Bolognese")),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);
    }

    /**
     * Verifies that the app shows the appropriate message when no matching recipes
     * are found due to the pantry being empty or ingredients not matching any
     * recipes in the database.
     */
    @org.junit.Test
    public void testNoMatchingRecipes() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Generate Recipes directly
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the no recipes message is shown
        Espresso.onView(ViewMatchers.withText("No matching recipes found."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

