package com.example.pantrypalandroidprototype.androidTest;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
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
    @org.junit.Test
    public void testDeleteIngredientUpdatesRecyclerView() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

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
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Navigate back to the pantry
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Locate the delete icon within the specific item in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.delete_icon))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verify the ingredient is no longer displayed in the RecyclerView
        Espresso.onView(ViewMatchers.withText("Sugar"))
                .check(ViewAssertions.doesNotExist());
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
