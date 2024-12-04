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

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchIngredientsInstrumentedTest {

    // Rule to launch the activity containing the AddRecipeFragment
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * This tests whether the search functionality works correctly when a valid ingredient is entered.
     */
    @org.junit.Test
    public void testSearchIngredient_validQuery() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Add ingredients to search for
        GenerateRecipesInstrumentedTest.addIngredient("Whole Wheat Bread", "30", "slices");
        GenerateRecipesInstrumentedTest.addIngredient("White Bread", "10", "slices");

        // Navigate back to the Pantry view
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Click the "Search Ingredients" button
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        // Type in a valid ingredient name
        this.typeText(R.id.searchQueryText, "Bread");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the filtered ingredient list is displayed
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * This tests the behavior when an invalid query (empty query) is entered.
     */
    @org.junit.Test
    public void testSearchIngredient_emptyQuery() {

        // Click the "Search Ingredients" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Leave the search query empty
        this.typeText(R.id.searchQueryText, "");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        // Verify that the error message "Please enter a valid search query." is displayed
        Espresso.onView(ViewMatchers.withId(R.id.errorText))
                .check(ViewAssertions.matches(ViewMatchers.withText("Please enter a valid search query.")));

        SystemClock.sleep(2000);
    }

    /**
     * This tests the behavior when no ingredients match the search query.
     */
    @org.junit.Test
    public void testSearchIngredient_noMatches() {

        // Click the "Search Ingredients" button to navigate to the search screen
        Espresso.onView(ViewMatchers.withId(R.id.searchIngredientsButton))
                .perform(ViewActions.click());

        // Type in a query that doesn't match any ingredient
        this.typeText(R.id.searchQueryText, "NonExistentIngredient");

        // Click the search button
        Espresso.onView(ViewMatchers.withId(R.id.searchButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that a snackbar message is displayed indicating no ingredients were found
        Espresso.onView(ViewMatchers.withText("No ingredients found"))
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
        Espresso.closeSoftKeyboard();
    }
}