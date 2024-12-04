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
     * Tests editing an ingredient's details and verifying updates.
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
        typeText(R.id.itemQuantityText, "3.5");

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
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("3.5 kg")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests validation error when editing an ingredient with empty fields.
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
     * Tests that clicking "Done" navigates back to the pantry view.
     */
    @org.junit.Test
    public void testDoneButtonNavigatesToPantry() {
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());
g
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Input test data
        String initialName = "Sugar";
        String initialQty = "1.5";
        String initialUnit = "kg";
        String newQty = "3.5";

        typeText(R.id.itemNameText, initialName);
        typeText(R.id.itemQtyText, initialQty);
        typeText(R.id.itemUnitText, initialUnit);

        // Select dietary tags
        Espresso.onView(ViewMatchers.withId(R.id.veganCheckbox)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.glutenFreeCheckbox)).perform(ViewActions.click());

        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Navigate back to the pantry
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Navigate to Edit Ingredients screen
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


    /**
     * Helper method to type text into a text field.
     *
     * @param viewId the id of the text field to type into.
     * @param text   the text to be typed.
     */
    private void typeText(final int viewId, final String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard());
    }
}
