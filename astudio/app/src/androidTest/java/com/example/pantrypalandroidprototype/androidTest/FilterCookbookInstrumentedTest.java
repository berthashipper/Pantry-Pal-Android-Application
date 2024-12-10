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
 * A class designed to test the FilterCookbook functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FilterCookbookInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This test verifies that filtering the cookbook by a valid category
     * displays the correct filtered recipes.
     */
    @org.junit.Test
    public void testFilterCookbook_validFilter() {

        // Step 1: Click the "View Cookbook" button
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Click the "Filter" button
        Espresso.onView(ViewMatchers.withId(R.id.filterRecipesButton))
                .perform(ViewActions.click());

        // Step 3: Select a valid category from the dropdown menu
        Espresso.onView(ViewMatchers.withId(R.id.dietaryPreferenceSpinner))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("DESSERT"))
                .perform(ViewActions.click());

        // Step 4: Click the "Apply Filter" button
        Espresso.onView(ViewMatchers.withId(R.id.applyFiltersButton))
                .perform(ViewActions.click());

        // Step 5: Verify that the filtered recipe list is displayed with "Desserts"
        Espresso.onView(ViewMatchers.withText("Chocolate Cake"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This test verifies that attempting to filter with no input triggers
     * appropriate error message.
     */
    @org.junit.Test
    public void testFilterCookbook_emptyFilter() {

        // Step 1: Click the "View Cookbook" button
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Click the "Filter" button
        Espresso.onView(ViewMatchers.withId(R.id.filterRecipesButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Step 3: Click the "Apply Filter" button
        Espresso.onView(ViewMatchers.withId(R.id.applyFiltersButton))
                .perform(ViewActions.click());
        
        // Step 4: Verify the fallback message (if no selection was made)
        Espresso.onView(ViewMatchers.withText("Please select a tag"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(3000);
    }
}
