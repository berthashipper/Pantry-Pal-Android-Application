package com.example.pantrypalandroidprototype;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.junit.Rule;
import org.junit.Test;
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
     * Tests whether generating recipes works after adding the required ingredients
     * for a grilled cheese recipe.
     */
    @org.junit.Test
    public void testGenerateRecipesForGrilledCheese() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Add 3 slices of bread
        addIngredient("Bread", "3", "slices");

        // Add 4 slices of cheese
        addIngredient("Cheese", "4", "slices");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Generate Recipes
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        SystemClock.sleep(5000);

        // Verify that the Grilled Cheese recipe is displayed
        Espresso.onView(ViewMatchers.withText("Grilled Cheese Sandwich"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests whether the app correctly shows a no recipes message
     * when there are no matching recipes.
     */
    @org.junit.Test
    public void testNoMatchingRecipes() {
        // Navigate to Generate Recipes directly
        Espresso.onView(ViewMatchers.withId(R.id.generateRecipesMenuButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the no recipes message is shown
        Espresso.onView(ViewMatchers.withText("No matching recipes found."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Helper method to add an ingredient to the pantry.
     *
     * @param name  the name of the ingredient.
     * @param qty   the quantity of the ingredient.
     * @param unit  the unit of the ingredient.
     */
    private void addIngredient(String name, String qty, String unit) {
        typeText(R.id.itemNameText, name);
        typeText(R.id.itemQtyText, qty);
        typeText(R.id.itemUnitText, unit);

        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton))
                .perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(500);
    }

    /**
     * Helper method to type text into a text field.
     *
     * @param viewId the id of the text field to type into.
     * @param text   the text to be typed.
     */
    private void typeText(final int viewId, final String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard());
    }
}

