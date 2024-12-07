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
 * Instrumented test class for verifying the "Delete Ingredients" functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DeleteIngredientsInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests that deleting an ingredient from the pantry updates the RecyclerView correctly.
     * This test first adds an ingredient (Sugar) to the pantry, then deletes it and verifies that the ingredient
     * no longer appears in the RecyclerView. It also ensures the pantry view is displayed after the deletion.
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

        AddIngredientsInstrumentedTest.addPantryIngredient("Sugar","1.5","cups");
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

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
}
