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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying navigation back to the Cookbook view.
 */
@RunWith(AndroidJUnit4.class)
public class BackToCookbookTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether clicking the "done" button navigates back to the Cookbook view from different screens.
     */
    @Test
    public void testDoneButtonsNavigateToCookbook() {
        // Navigate to Cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Navigate to Search Recipe screen
        Espresso.onView(ViewMatchers.withId(R.id.searchRecipesButton))
                .perform(ViewActions.click());

        // Navigate back to the Cookbook view using the done button
        Espresso.onView(ViewMatchers.withId(R.id.backToCookbookButton))
                .perform(ViewActions.click());

        // Verify that the Cookbook view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        // Navigate to Add Recipe screen
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        // Navigate back to the Cookbook view using the done button
        Espresso.onView(ViewMatchers.withId(R.id.backToCookbookIcon))
                .perform(ViewActions.click());

        // Verify that the Cookbook view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
