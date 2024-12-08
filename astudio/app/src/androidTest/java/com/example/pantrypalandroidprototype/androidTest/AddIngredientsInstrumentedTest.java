package com.example.pantrypalandroidprototype.androidTest;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying the "Add Ingredients" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class AddIngredientsInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether adding an ingredient updates the RecyclerView correctly.
     * This test clears the pantry, navigates to the Add Ingredients screen,
     * adds a new ingredient with valid data, and verifies that the ingredient
     * is correctly displayed in the Pantry view with the correct quantity and unit.
     */
    @org.junit.Test
    public void testAddThenClearIngredient() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        addPantryIngredient("Sugar","1.5","cups");
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the ingredient has been added
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Sugar")),
                        ViewActions.click()
                ));

        // Verify the ingredient quantity is correct
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("1.5 cups")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);

        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);
    }

    /**
     * Tests whether adding an ingredient that already exists in the pantry
     * notifies the user correctly, and doesn't mess with the pre-existing value in the pantry.
     */
    @org.junit.Test
    public void testAddPreExistingPantryIngredient() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        addPantryIngredient("Flour","6","cups");
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the ingredient has been added
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Flour")),
                        ViewActions.click()
                ));

        // Verify the ingredient quantity is correct
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("6.0 cups")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        addPantryIngredient("Flour","300","cups");

        Espresso.onView(ViewMatchers.withText("Flour already exists in the pantry."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        // Verify the ingredient has been added
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Flour")),
                        ViewActions.click()
                ));

        // Verify the ingredient quantity is correct
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("6.0 cups")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);
    }

    /**
     * Tests whether clicking the "Done" button navigates back to the Pantry view.
     * This test ensures that when the "Done" button is clicked, the user is redirected
     * back to the Pantry screen where the list of ingredients is displayed.
     */
    @org.junit.Test
    public void testDoneButtonNavigatesToPantry() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Click "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests the validation behavior when required fields are empty.
     * This test attempts to submit the add ingredient form without filling in any of the required fields.
     * It checks if the app displays the appropriate error message prompting the user
     * to fill in all required fields.
     */
    @org.junit.Test
    public void testValidationWhenFieldsAreEmpty() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Click "Add" button without entering any data
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Verify that a validation error message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please fill in all fields.")));
    }

    /**
     * Helper method to add an ingredient to the pantry.
     *
     * @param name  the name of the ingredient.
     * @param qty   the quantity of the ingredient.
     * @param unit  the unit of the ingredient.
     */
    public static void addPantryIngredient(String name, String qty, String unit) {
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, name);
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, qty);
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, unit);

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);
    }

    /**
     * Helper method to type text into a text field.
     *
     * @param viewId the id of the text field to type into.
     * @param text   the text to be typed.
     */
     public static void typeText(final int viewId, final String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard());
    }
}