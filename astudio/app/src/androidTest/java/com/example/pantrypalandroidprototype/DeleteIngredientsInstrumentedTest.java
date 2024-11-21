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
 * Instrumented test class for verifying the "Delete Ingredients" functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DeleteIngredientsInstrumentedTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether deleting an ingredient updates the RecyclerView correctly.
     */
    @Test//passed
    public void testDeleteIngredientUpdatesRecyclerView() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Sugar";
        String testQty = "1.5";
        String testUnit = "kg";

        typeText(R.id.itemNameText, testName);
        typeText(R.id.itemQtyText, testQty);
        typeText(R.id.itemUnitText, testUnit);

        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());


        // Navigate to the pantry
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());


        // Navigate to the delete ingredient
        Espresso.onView(ViewMatchers.withId(R.id.deleteIngredientsButton))
                .perform(ViewActions.click());

        // Enter the ingredient name to delete
        ViewInteraction nameInput = Espresso.onView(ViewMatchers.withId(R.id.itemNameText));
        nameInput.perform(ViewActions.typeText("Sugar"), ViewActions.closeSoftKeyboard());

        // Click the "Delete" button
        Espresso.onView(ViewMatchers.withId(R.id.deleteIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verify the ingredient is no longer displayed in the RecyclerView
        Espresso.onView(ViewMatchers.withText("Sugar"))
                .check(ViewAssertions.doesNotExist());
    }

    /**
     * Tests whether clicking "Done" navigates back to the Pantry view.
     */
    @Test //passed
    public void testDoneButtonNavigatesToPantry() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.deleteIngredientsButton))
                .perform(ViewActions.click());

        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify the Pantry fragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
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
