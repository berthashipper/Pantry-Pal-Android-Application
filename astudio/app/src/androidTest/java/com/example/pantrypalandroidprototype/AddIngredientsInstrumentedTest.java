package com.example.pantrypalandroidprototype;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

/**
 * Instrumented test class for verifying the "Add Ingredients" functionality.
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
    @Test //passed
    public void testAddIngredient() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Sugar";
        String testQty = "1.5";
        String testUnit = "kg";

        typeText(R.id.itemNameText, testName);
        typeText(R.id.itemQtyText, testQty);
        typeText(R.id.itemUnitText, testUnit);

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

    }

    /**
     * Tests whether clicking "Done" navigates back to the Pantry view.
     */
    @Test //passed
    public void testDoneButtonNavigatesToPantry() {
        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        // Click "Done" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Verify that the Pantry view is displayed
        Espresso.onView(ViewMatchers.withId(R.id.pantryContentsTextView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

//    /**
//     * Tests validation when required fields are empty.
//     */
//
//    @Test
//    public void testValidationWhenFieldsAreEmpty() {
//        // Navigate to Add Ingredients screen
//        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
//                .perform(ViewActions.click());
//
//        // Click "Add" button without entering any data
//        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());
//
//        IdlingPolicies.setIdlingResourceTimeout(5, TimeUnit.SECONDS);
//
//        // Verify that a validation error message is displayed
//        Espresso.onView(ViewMatchers.withId(R.id.))
//                .check(ViewAssertions.matches(withText(R.string.error_empty_ingredient_fields)));
//
//    }

//    /**
//     * Tests validation when quantity input is invalid.
//     */
//    @Test
//    public void testValidationForInvalidQuantity() {
//        // Navigate to Add Ingredients screen
//        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
//                .perform(ViewActions.click());
//
//        // Input invalid quantity
//        typeText(R.id.itemNameText, "Flour");
//        typeText(R.id.itemQtyText, "invalid");
//        typeText(R.id.itemUnitText, "kg");
//
//        // Click "Add" button
//        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());
//
//        // Verify that a validation error message is displayed
//        Espresso.onView(ViewMatchers.withText("Invalid quantity"))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//    }

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
