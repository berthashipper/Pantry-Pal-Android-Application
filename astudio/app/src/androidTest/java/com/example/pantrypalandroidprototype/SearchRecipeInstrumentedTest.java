package com.example.pantrypalandroidprototype;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.hamcrest.Matcher;
import org.junit.runner.RunWith;

/**
 * A class designed to test the SearchRecipeFragment's search functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchRecipeInstrumentedTest {

    // Rule to launch the activity containing the SearchRecipeFragment
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This tests whether the search functionality works correctly when a valid query is entered.
     */
    @org.junit.Test
    public void testSearchRecipe_validQuery() {

        // Step 1: Click the "View Cookbook" button
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Click the "Add Recipe" button
        Espresso.onView(ViewMatchers.withId(R.id.searchRecipesButton))
                .perform(ViewActions.click());

        // Step 3: Type in a valid recipe name
        this.typeText(R.id.searchQueryText, "Salad");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that the filtered recipe list is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the behavior when an invalid query (empty query) is entered.
     */
    @org.junit.Test
    public void testSearchRecipe_emptyQuery() {

        // Step 1: Click the "View Cookbook" button
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Click the "Add Recipe" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchRecipesButton))
                .perform(ViewActions.click());

        // Step 3: Leave the search query empty
        this.typeText(R.id.searchQueryText, "");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that the error message "Please enter a valid search query." is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please enter a valid search query.")));
    }

    /**
     * This tests the behavior when no recipes match the search query.
     */
    @org.junit.Test
    public void testSearchRecipe_noMatches() {

        // Step 1: Click the "View Cookbook" button
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Step 2: Click the "Add Recipe" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchRecipesButton))
                .perform(ViewActions.click());

        // Step 3: Type in a query that doesn't match any recipe
        this.typeText(R.id.searchQueryText, "NonExistentRecipe");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that a toast message is displayed indicating no recipes were found
        Espresso.onView(ViewMatchers.withText("No recipes found"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Helper method to type text into a text field.
     *
     * @param viewId the id of the text field to type into.
     * @param text the text to be typed.
     */
    private void typeText(final int viewId, final String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text));
        Espresso.closeSoftKeyboard(); // close the keyboard to prevent it from occluding other objects
    }
}

