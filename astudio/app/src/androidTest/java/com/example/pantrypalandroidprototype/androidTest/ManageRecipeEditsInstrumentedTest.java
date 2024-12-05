package com.example.pantrypalandroidprototype.androidTest;

import android.os.SystemClock;
import android.widget.EditText;

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
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ManageRecipeEditsInstrumentedTest {

        @Rule
        public ActivityScenarioRule<ControllerActivity> activityRule =
                new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests editing an ingredient successfully.
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

        // Verify the Snackbar appears with the correct message
        Espresso.onView(ViewMatchers.withText("NonExistentIngredient not found in recipe"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());
    }


    /**

    /**
     * Tests adding a recipe's ingredient and verifying updates.
     */
    @org.junit.Test
    public void testAddRecipeIngredientUpdatesRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

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

        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("200.0 g of Chicken Leg"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }


    /**
     * Tests deleting a recipe's ingredient and verifying updates.
     */
    @org.junit.Test
    public void testDeleteRecipeIngredientUpdatesRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Chicken Curry";

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.delete_recipe_ingredient))
                .perform(ViewActions.click());

        typeText(R.id.itemNameText, "Chicken Breast");

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
     * Tests editing a recipe's instructions and verifying updates.
     */
    @org.junit.Test
    public void testEditInstructionsUpdatesRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);
        String testName = "Beef Tacos";

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.edit_instruction))
                .perform(ViewActions.click());

        // Enter the edited Instruction
        Espresso.onView(ViewMatchers.withId(R.id.instructionEditText))
                .perform(ViewActions.clearText());
        typeText(R.id.instructionEditText, "Test Instruction");

        // Click the Done button to edit the instruction
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("Test Instruction"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }

    /**
     * Tests editing a recipe's cook time and verifying updates.
     */
    @org.junit.Test
    public void testEditCookTimeUpdatesRecyclerView() {
        // Navigate to the cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        String testName = "Pancakes";

        // Select the recipe in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        // Click the "Edit Cook Time" button
        Espresso.onView(ViewMatchers.withId(R.id.edit_cook_time))
                .perform(ViewActions.click());

        // Enter the new cook time in the EditText
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(ViewActions.clearText(), ViewActions.typeText("20"));

        // Confirm the changes by clicking the Save button
        Espresso.onView(ViewMatchers.withText("Save"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the updated cook time in the UI
        Espresso.onView(ViewMatchers.withText("Cook Time: 20 minutes"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests editing a recipe's cook time and verifying updates.
     */
    @org.junit.Test
    public void testEditYieldUpdatesRecyclerView() {
        // Navigate to the cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        String testName = "Pancakes";

        // Select the recipe in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        // Click the "Edit Serving Size" button
        Espresso.onView(ViewMatchers.withId(R.id.edit_serving_size))
                .perform(ViewActions.click());

        // Enter the new cook time in the EditText
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(ViewActions.clearText(), ViewActions.typeText("1"));

        // Confirm the changes by clicking the Save button
        Espresso.onView(ViewMatchers.withText("Save"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the updated cook time in the UI
        Espresso.onView(ViewMatchers.withText("Serves: 1"))
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