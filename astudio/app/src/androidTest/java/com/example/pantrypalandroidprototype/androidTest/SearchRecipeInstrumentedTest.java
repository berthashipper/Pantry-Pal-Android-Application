package com.example.pantrypalandroidprototype.androidTest;

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
 * A class designed to test the SearchRecipeFragment's search functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchRecipeInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This test verifies that the search functionality works correctly when a valid recipe query is entered.
     * It ensures that the search results are displayed properly when a recipe like "Salad" is searched.
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
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "Salad");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that the filtered recipe list is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This test verifies the behavior when an invalid search query (an empty query) is entered.
     * It ensures that an error message "Please enter a valid search query." is displayed when the search query is empty.
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
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that the error message "Please enter a valid search query." is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please enter a valid search query.")));
    }

    /**
     * This test verifies the behavior when no recipes match the entered search query.
     * It ensures that a "No recipes found" snackbar message is displayed when no recipes match the query.
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
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "NonExistentRecipe");

        // Step 4: Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Step 5: Verify that a snackbar message is displayed indicating no recipes were found
        Espresso.onView(ViewMatchers.withText("No recipes found"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

