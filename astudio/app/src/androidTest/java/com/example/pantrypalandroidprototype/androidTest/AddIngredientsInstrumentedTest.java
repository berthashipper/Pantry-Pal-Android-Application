package com.example.pantrypalandroidprototype.androidTest;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying the "Add Ingredients" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class AddIngredientsInstrumentedTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether adding an ingredient updates the RecyclerView correctly.
     */
    @org.junit.Test
    public void testAddThenClearIngredient() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Sugar";
        String testQty = "1.5";
        String testUnit = "kg";

        typeText(R.id.itemNameText, testName);
        typeText(R.id.itemQtyText, testName);
        Espresso.onView(ViewMatchers.withId(R.id.itemQtyText))
                .check(ViewAssertions.matches(ViewMatchers.withText(""))); //Checks if only take in numbers for input
        typeText(R.id.itemQtyText, testQty);
        Espresso.onView(ViewMatchers.withId(R.id.itemQtyText))
                .check(ViewAssertions.matches(ViewMatchers.withText("1.5")));
        typeText(R.id.itemUnitText, testUnit);

        // Select dietary tags
        Espresso.onView(ViewMatchers.withId(R.id.veganCheckbox)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.glutenFreeCheckbox)).perform(ViewActions.click());

        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(5000);

        // Click "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Verify the result is correct
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(withText("🛒 Pantry Contents:\n\n• Sugar\n   Quantity: 1.5 kg\n   Tags: GLUTEN_FREE, VEGAN\n\n")));

        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
    }

    /**
     * Tests whether clicking "Done" navigates back to the Pantry view.
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
     * Tests validation when required fields are empty.
     */

    @org.junit.Test
    public void testValidationWhenFieldsAreEmpty() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Click "Add" button without entering any data
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(5000);

        // Verify that a validation error message is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please fill in all fields.")));
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