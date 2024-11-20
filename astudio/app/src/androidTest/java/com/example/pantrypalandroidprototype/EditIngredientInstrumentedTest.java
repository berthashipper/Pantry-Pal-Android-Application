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

@RunWith(AndroidJUnit4.class)
public class EditIngredientInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests editing an ingredient's details and verifying updates.
     */
    @Test
    public void testEditIngredientUpdatesRecyclerView() {
        // Input test data
        String initialName = "Flour";
        String initialQty = "2";
        String newName = "Whole Wheat Flour";
        String newQty = "3.5";

        // Enter initial ingredient name and quantity
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .perform(ViewActions.typeText(initialName), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.itemQuantityText))
                .perform(ViewActions.typeText(initialQty), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Enter updated ingredient name and quantity
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .perform(ViewActions.clearText(), ViewActions.typeText(newName), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.itemQuantityText))
                .perform(ViewActions.clearText(), ViewActions.typeText(newQty), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify that the RecyclerView is updated with the edited ingredient
        Espresso.onView(ViewMatchers.withText(newName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(newQty))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests validation error when editing an ingredient with empty fields.
     */
    @Test
    public void testValidationWhenFieldsAreEmpty() {
        // Attempt to edit with empty fields
        Espresso.onView(ViewMatchers.withId(R.id.editButton)).perform(ViewActions.click());

        // Verify validation error message is displayed
        Espresso.onView(ViewMatchers.withText("Invalid quantity."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests that clicking "Done" navigates back to the pantry view.
     */
    @Test
    public void testDoneButtonNavigatesToPantry() {
        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify that the pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
