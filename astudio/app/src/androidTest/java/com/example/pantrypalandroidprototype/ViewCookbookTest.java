package com.example.pantrypalandroidprototype;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
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
 * Instrumented test class for verifying the "View Cookbook" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class ViewCookbookTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether clicking "Add Recipe" functionality add to Cookbook and get updated.
     */
    @Test
    public void testAddRecipe() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

    /**
     * Tests "Search Recipe" functionality.
     */
    @Test
    public void testSearchRecipe() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

    /**
     * Tests click on Recipe blocks the details of the recipe is shown correctly.
     */
    @Test
    public void testRecipeDetails() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

    /**
     * Tests "BackToCookbook" functionality.
     */
    @Test
    public void testBackToCookbook() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

}
