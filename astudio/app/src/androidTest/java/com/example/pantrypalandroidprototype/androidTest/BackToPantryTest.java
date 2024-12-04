package com.example.pantrypalandroidprototype.androidTest;
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
 * Instrumented test class for verifying the "ViewPantry" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class BackToPantryTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether clicking "ViewButton" navigates back to the Pantry view.
     */
    @org.junit.Test
    public void testDoneButtonsNavigateToPantry() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Navigate to pantry screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        // Navigate to pantry screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Navigate to pantry screen
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
