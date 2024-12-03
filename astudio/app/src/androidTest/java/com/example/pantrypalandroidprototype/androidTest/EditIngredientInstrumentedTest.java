package com.example.pantrypalandroidprototype.androidTest;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());

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

        SystemClock.sleep(3000);

        // Navigate back to the pantry
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Verify the ingredient has been added
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(withText("🛒 Pantry Contents:\n\n• Sugar\n   Quantity: 1.5 kg\n   Tags: GLUTEN_FREE, VEGAN\n\n")));

        SystemClock.sleep(3000);

        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Enter the ingredient to Edit
        typeText(R.id.itemNameText, initialName);
        typeText(R.id.itemQuantityText, newQty);

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        // Verify the ingredient is updated in the pantry
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(withText("🛒 Pantry Contents:\n\n• Sugar\n   Quantity: 3.5 kg\n   Tags: GLUTEN_FREE, VEGAN\n\n")));
    }

    /**
     * Tests validation error when editing an ingredient with empty fields.
     */
    @org.junit.Test
    public void testValidationWhenFieldsAreEmpty() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Ensure fields are empty
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .perform(ViewActions.clearText());
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
     * Tests validation error when the name field is empty.
     */
    @org.junit.Test
    public void testValidationWhenNameIsEmpty() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Ensure the name field is empty
        Espresso.onView(ViewMatchers.withId(R.id.itemNameText))
                .perform(ViewActions.clearText());
        // Enter a valid quantity
        typeText(R.id.itemQuantityText, "2.0");

        // Attempt to edit with an empty name field
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Verify validation error message is displayed
        Espresso.onView(ViewMatchers.withText("Item name cannot be empty."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests that clicking "Done" navigates back to the pantry view.
     */
    @org.junit.Test
    public void testDoneButtonNavigatesToPantry() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Click the "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Verify that the pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @org.junit.Test
    public void testNonexistentIngredientError() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.editIngredientsButton))
                .perform(ViewActions.click());

        // Enter a nonexistent ingredient to edit
        String nonexistentIngredient = "Nonexistent Ingredient";
        String newQty = "2.0";

        typeText(R.id.itemNameText, nonexistentIngredient);
        typeText(R.id.itemQuantityText, newQty);

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Verify the error message for nonexistent ingredient is displayed
        Espresso.onView(ViewMatchers.withText("Nonexistent Ingredient does not exist in your pantry"))
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
