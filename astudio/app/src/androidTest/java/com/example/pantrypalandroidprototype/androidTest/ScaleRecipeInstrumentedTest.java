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

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScaleRecipeInstrumentedTest {

    // Rule to launch the activity containing the ScaleRecipeFragment
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This tests the functionality of scaling a recipe.
     */
    @org.junit.Test
    public void testScaleRecipe_validScalingFactor() {

        // Step 1: Navigate to the recipe details screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Select a recipe
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        // Step 3: Click the "Scale Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.scale_button))
                .perform(ViewActions.click());

        // Step 4: Enter a valid scaling factor (e.g., 2)
        this.typeText(R.id.scaleFactor, "2");

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        // Step 6: Verify that the scaled recipe is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Step 7: Verify that a scaled ingredient is correctly updated (e.g., "2 cups flour")
        Espresso.onView(ViewMatchers.withText("4.0 slices of Bread"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the behavior when the scaling factor input is left empty.
     */
    @org.junit.Test
    public void testScaleRecipe_emptyScalingFactor() {

        // Step 1: Navigate to the recipe details screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Select a recipe
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        // Step 3: Click the "Scale Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.scale_button))
                .perform(ViewActions.click());

        // Step 4: Leave the scaling factor input empty
        this.typeText(R.id.scaleFactor, "");

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 6: Verify that an error message is displayed
        Espresso.onView(ViewMatchers.withText("Invalid scale factor"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the behavior when the scaling factor is set to 0.
     */
    @org.junit.Test
    public void testScaleRecipe_zeroScalingFactor() {

        // Step 1: Navigate to the recipe details screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Select a recipe
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        // Step 3: Click the "Scale Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.scale_button))
                .perform(ViewActions.click());

        // Step 4: Enter a scaling factor of 0
        this.typeText(R.id.scaleFactor, "0");

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 6: Verify that an error message or an appropriate response is displayed
        Espresso.onView(ViewMatchers.withText("Scale factor must be greater than 0"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the functionality of scaling a recipe from generate recipes.
     */
    @org.junit.Test
    public void testScaleRecipe_generatedRecipe() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Add 3 slices of bread
        GenerateRecipesInstrumentedTest.addIngredient("Bread", "3", "slices");

        // Add 4 slices of cheese
        GenerateRecipesInstrumentedTest.addIngredient("Cheese", "4", "slices");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Generate Recipes
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        // Step 2: Select a recipe
        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        // Step 3: Click the "Scale Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.scale_button))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 4: Enter a valid scaling factor (e.g., 2)
        this.typeText(R.id.scaleFactor, "2");

        SystemClock.sleep(1000);

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 6: Verify that the scaled recipe is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Step 7: Verify that a scaled ingredient is correctly updated (e.g., "2 cups flour")
        Espresso.onView(ViewMatchers.withText("4.0 slices of Bread"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    /**
     * Helper method to type text into a text field.
     *
     * @param viewId the id of the text field to type into.
     * @param text the text to be typed.
     */
    private void typeText(final int viewId, final String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text));
        Espresso.closeSoftKeyboard();
    }
}