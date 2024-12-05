package com.example.pantrypalandroidprototype.androidTest;

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
public class ManageRecipeIngredientsTest {

        @Rule
        public ActivityScenarioRule<ControllerActivity> activityRule =
                new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests updating an ingredient successfully.
     */
    @org.junit.Test
    public void testUpdateRecipeIngredientSuccessfully() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.edit_recipe_ingredient))
                .perform(ViewActions.click());

        typeText(R.id.itemNameText, "Chicken Breast");
        typeText(R.id.itemQuantityText, "600");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("600.0 g of Chicken Breast"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests deleting a non-existent ingredient and checks for snackbar feedback.
     */
    @org.junit.Test
    public void testDeleteNonExistentRecipeIngredient() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.delete_recipe_ingredient))
                .perform(ViewActions.click());

        typeText(R.id.itemNameText, "NonExistentIngredient");

        // Click the Delete button
        Espresso.onView(ViewMatchers.withId(R.id.deleteButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the snackbar appears with the correct message
        Espresso.onView(ViewMatchers.withText("NonExistentIngredient not found in recipe"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());
    }


    /**

    /**
     * Tests adding an ingredient's details and verifying updates.
     */
    @org.junit.Test
    public void testAddRecipeIngredientUpdatesRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.add_recipe_ingredient))
                .perform(ViewActions.click());

        //Enter the new Ingredient Name
        typeText(R.id.itemNameText, "Chicken Leg");
        // Enter the Added quantity
        typeText(R.id.itemQuantityText, "200");
        //Enter the Unit
        typeText(R.id.itemUnitText, "g");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("200.0 g of Chicken Leg"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        SystemClock.sleep(2000);
    }


    /**
     * Tests deleting an ingredient's details and verifying updates.
     */
    @org.junit.Test
    public void testDeleteRecipeIngredientUpdatesRecyclerView() {
        // Navigate to Edit Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.delete_recipe_ingredient))
                .perform(ViewActions.click());

        //Enter the new Ingredient Name
        typeText(R.id.itemNameText, "Chicken Breast");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.deleteButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);
        Espresso.onView(ViewMatchers.withText("600.0 g of Chicken Breast"))
                .check(ViewAssertions.doesNotExist());
        SystemClock.sleep(2000);
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