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
 * Instrumented test class for verifying the "Search Ingredients" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class SearchIngredientsInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This test verifies that the search functionality works correctly when a valid ingredient query is entered.
     * It adds ingredients to the pantry, navigates to the search screen, performs a search with a valid ingredient name,
     * and checks that the filtered ingredient list is displayed.
     */
    @org.junit.Test
    public void testSearchIngredient_validQuery() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Add ingredients to search for
        AddIngredientsInstrumentedTest.addPantryIngredient("Whole Wheat Bread", "30", "slices");
        AddIngredientsInstrumentedTest.addPantryIngredient("White Bread", "10", "slices");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Click the "Search Ingredients" button
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        // Type in a valid ingredient name
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "Bread");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the filtered ingredient list is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This test checks the behavior when an invalid (empty) search query is entered.
     * It clicks the search button without typing anything in the search field,
     * and then verifies that an error message ("Please enter a valid search query.") is displayed.
     */
    @org.junit.Test
    public void testSearchIngredient_emptyQuery() {

        // Click the "Search Ingredients" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Leave the search query empty
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Verify that the error message "Please enter a valid search query." is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please enter a valid search query.")));

        SystemClock.sleep(2000);
    }

    /**
     * This test verifies the behavior when no ingredients match the search query.
     * It enters a search query that does not match any existing ingredients and checks
     * that a snackbar message indicating "No ingredients found" is displayed.
     */
    @org.junit.Test
    public void testSearchIngredient_noMatches() {

        // Click the "Search Ingredients" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        // Type in a query that doesn't match any ingredient
        AddIngredientsInstrumentedTest.typeText(R.id.searchQueryText, "NonExistentIngredient");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that a snackbar message is displayed indicating no ingredients were found
        Espresso.onView(ViewMatchers.withText("No ingredients found"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}