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

@RunWith(AndroidJUnit4.class)
public class ScaleRecipeInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This tests the functionality of scaling a recipe with a valid scaling factor.
     * The recipe's ingredients and serving size should be scaled correctly based on the factor.
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

        // Step 4: Enter a valid scaling factor
        AddIngredientsInstrumentedTest.typeText(R.id.scaleFactor, "2");

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        // Step 6: Verify that the scaled recipe is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Step 7: Verify that a scaled ingredient is correctly updated
        Espresso.onView(ViewMatchers.withText("4.0 slices of Bread"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verify that a scaled serving size is correctly updated
        Espresso.onView(ViewMatchers.withText("Serves: 2"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the behavior when the scaling factor input is left empty.
     * An error message should be displayed indicating an invalid scale factor.
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
        AddIngredientsInstrumentedTest.typeText(R.id.scaleFactor, "");

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
     * An error message should be displayed indicating that the scale factor must be greater than 0.
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
        AddIngredientsInstrumentedTest.typeText(R.id.scaleFactor, "0");

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 6: Verify that an error message or an appropriate response is displayed
        Espresso.onView(ViewMatchers.withText("Scale factor must be greater than 0"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the functionality of scaling a recipe from the "Generate Recipes" screen.
     * The scaled recipe should reflect the ingredients and serving size based on the scaling factor.
     */
    @org.junit.Test
    public void testScaleRecipe_generatedRecipe() {
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

        // Step 4: Enter a valid scaling factor
        AddIngredientsInstrumentedTest.typeText(R.id.scaleFactor, "2");

        SystemClock.sleep(1000);

        // Step 5: Click the "Apply Scaling" button
        Espresso.onView(ViewMatchers.withId(R.id.scaleButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 6: Verify that the scaled recipe is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Step 7: Verify that a scaled ingredient is correctly updated
        Espresso.onView(ViewMatchers.withText("4.0 slices of Bread"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verify that a scaled serving size is correctly updated
        Espresso.onView(ViewMatchers.withText("Serves: 2"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the functionality of navigating back to the recipe details screen from the ScaleRecipeFragment.
     * Upon navigation, the original recipe details should be visible again.
     */
    @org.junit.Test
    public void testBackToRecipeNavigation() {
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

        // Step 4: Click the "Back to Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Cook Time: 10 minutes"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}