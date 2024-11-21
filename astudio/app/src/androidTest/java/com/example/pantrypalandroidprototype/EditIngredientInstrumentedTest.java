package com.example.pantrypalandroidprototype;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
    @Test //passed
    public void testEditIngredientUpdatesRecyclerView() {
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

        // Select dietary tags (e.g., Vegan and Gluten-Free)
        Espresso.onView(ViewMatchers.withId(R.id.veganCheckbox)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.glutenFreeCheckbox)).perform(ViewActions.click());


        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Click "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());
        //if the ingredient is saved
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(withText("🛒 Pantry Contents:\n\n• Sugar\n   Quantity: 1.5 kg\n   Tags: GLUTEN_FREE, VEGAN\n\n")));

        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        //Enter the ingredient to Edit
        typeText(R.id.itemNameText, initialName);
        typeText(R.id.itemQuantityText, newQty);

        //Click the edit to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

//        // Click the "Done" button
//        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        //Check the ingredient is updated
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(withText("🛒 Pantry Contents:\n\n• Sugar\n   Quantity: 3.5 kg\n   Tags: GLUTEN_FREE, VEGAN\n\n")));

    }

//    /**
//     * Tests validation error when editing an ingredient with empty fields.
//     */
//    @Test
//    public void testValidationWhenFieldsAreEmpty() {
//        // Attempt to edit with empty fields
//        Espresso.onView(ViewMatchers.withId(R.id.editButton)).perform(ViewActions.click());
//
//        // Verify validation error message is displayed
//        Espresso.onView(ViewMatchers.withText("Invalid quantity."))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//    }

    /**
     * Tests that clicking "Done" navigates back to the pantry view.
     */
    @Test //passed
    public void testDoneButtonNavigatesToPantry() {

        // Navigate to edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify that the pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
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
