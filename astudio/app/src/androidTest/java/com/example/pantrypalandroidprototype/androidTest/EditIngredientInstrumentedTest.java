package com.example.pantrypalandroidprototype.androidTest;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditIngredientInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests editing an ingredient's details and verifying the updated information in the pantry view.
     * It navigates to the Edit Ingredients screen, updates the quantity of an ingredient, and verifies
     * the updated quantity is reflected in the pantry view.
     */
    @org.junit.Test
    public void testEditIngredientUpdatesRecyclerView() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.edit_icon))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        Espresso.onView(ViewMatchers.withId(R.id.itemQuantityText))
                .perform(ViewActions.clearText());

        // Enter the edited quantity
        AddIngredientsInstrumentedTest.typeText(R.id.itemQuantityText, "3.5");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Sugar")),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

        // Verify the ingredient quantity is updated in the pantry
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("3.5 cups")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests validation behavior when attempting to edit an ingredient with empty fields.
     * It ensures that when the user clears the quantity field and tries to save, a validation
     * error message is displayed.
     */
    @org.junit.Test
    public void testValidationWhenFieldsAreEmpty() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.edit_icon))
                .perform(ViewActions.click());

        // Ensure fields are empty
        Espresso.onView(ViewMatchers.withId(R.id.itemQuantityText))
                .perform(ViewActions.clearText());

        // Attempt to edit with empty fields
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Verify validation error message is displayed
        Espresso.onView(ViewMatchers.withText("Invalid quantity."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests that clicking the "Done" button on the Edit Ingredients screen navigates back
     * to the pantry view, ensuring the user can return to the pantry after making changes.
     * This method also verifies that the pantry view is displayed correctly after the action.
     */
    @org.junit.Test
    public void testDoneButtonNavigatesToPantry() {
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

        SystemClock.sleep(3000);

        Espresso.onView(ViewMatchers.withId(R.id.edit_icon))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pantry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
