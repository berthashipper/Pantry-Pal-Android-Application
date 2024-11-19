package com.example.pantrypalandroidprototype;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
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
    @Test
    public void testDeleteIngredientUpdatesRecyclerView() {
        // Precondition: Add a test ingredient ("Sugar") to the pantry.
        addTestIngredient("Sugar", "1.0", "kg");

        // Navigate to the delete ingredient
//        Espresso.onView(ViewMatchers.withId(R.id.))
//                .perform(ViewActions.click());

        // Enter the ingredient name to delete
        ViewInteraction nameInput = Espresso.onView(ViewMatchers.withId(R.id.itemNameText));
        nameInput.perform(ViewActions.typeText("Sugar"), ViewActions.closeSoftKeyboard());

        // Click the "Delete" button
        Espresso.onView(ViewMatchers.withId(R.id.deleteIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify the ingredient is no longer displayed in the RecyclerView
        Espresso.onView(ViewMatchers.withText("Sugar"))
                .check(ViewAssertions.doesNotExist());
    }

    /**
     * Tests whether clicking "Done" navigates back to the Pantry view.
     */
    @Test
    public void testDoneButtonNavigatesToPantry() {


        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify the Pantry fragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Helper method to add a test ingredient to the pantry.
     *
     * @param name  The name of the ingredient.
     * @param qty   The quantity of the ingredient.
     * @param unit  The unit of measurement for the ingredient.
     */
    private void addTestIngredient(String name, String qty, String unit) {
        // Navigate to the add ingredient menu
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Enter the ingredient name
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard());

        // Enter the ingredient quantity
        Espresso.onView(ViewMatchers.withId(R.id.itemQtyText))
                .perform(ViewActions.typeText(qty), ViewActions.closeSoftKeyboard());

        // Enter the ingredient unit
        Espresso.onView(ViewMatchers.withId(R.id.itemUnitText))
                .perform(ViewActions.typeText(unit), ViewActions.closeSoftKeyboard());

        // Click the "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);
    }
}
