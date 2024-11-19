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
 * Instrumented test class for verifying the "Add Ingredients" functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddIngredientsInstrumentedTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether adding an ingredient updates the RecyclerView correctly.
     */
    @Test
    public void testAddIngredientUpdatesRecyclerView() {
        // Input test data
        String testName = "Sugar";
        String testQty = "1.5";
        String testUnit = "kg";

        // Enter ingredient name
        ViewInteraction nameInput = Espresso.onView(ViewMatchers.withId(R.id.itemNameText));
        nameInput.perform(ViewActions.typeText(testName), ViewActions.closeSoftKeyboard());

        // Enter ingredient quantity
        ViewInteraction qtyInput = Espresso.onView(ViewMatchers.withId(R.id.itemQtyText));
        qtyInput.perform(ViewActions.typeText(testQty), ViewActions.closeSoftKeyboard());

        // Enter ingredient unit
        ViewInteraction unitInput = Espresso.onView(ViewMatchers.withId(R.id.itemUnitText));
        unitInput.perform(ViewActions.typeText(testUnit), ViewActions.closeSoftKeyboard());

        // Select dietary tags (e.g., Vegan and Gluten-Free)
        Espresso.onView(ViewMatchers.withId(R.id.veganCheckbox)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.glutenFreeCheckbox)).perform(ViewActions.click());

        // Click "Add" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify that the RecyclerView is updated with the new ingredient
        Espresso.onView(ViewMatchers.withText(testName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(testQty + " " + testUnit))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests whether clicking "Done" navigates back to the Pantry view.
     */
    @Test
    public void testDoneButtonNavigatesToPantry() {
        // Click "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Verify the Pantry fragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests validation when required fields are empty.
     */
    @Test
    public void testValidationWhenFieldsAreEmpty() {
        // Click "Add" button without entering data
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Verify that a validation error message is displayed
        Espresso.onView(ViewMatchers.withText("Please fill in all fields"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
