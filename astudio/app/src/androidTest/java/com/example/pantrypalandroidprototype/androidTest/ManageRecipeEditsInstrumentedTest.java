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
     * Tests updating an ingredient in a recipe, ensuring the changes are reflected in the UI.
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

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Chicken Breast");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQuantityText, "600");

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
     * Tests deleting a non-existent ingredient in a recipe and checks
     * for snackbar feedback indicating failure.
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

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "NonExistentIngredient");

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
     * Tests adding a new ingredient to a recipe and verifies that the ingredient is shown in the UI.
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
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Chicken Leg");
        // Enter the Added quantity
        AddIngredientsInstrumentedTest.typeText(R.id.itemQuantityText, "200");
        //Enter the Unit
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "g");

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
     * Tests deleting an ingredient from a recipe and ensures the UI is updated accordingly.
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

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Chicken Breast");

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
     * Tests editing the instructions for a recipe and ensuring the updates are reflected in the UI.
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
        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, "Test Instruction");

        // Click the Done button to edit the instruction
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("Test Instruction"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }

    /**
     * Tests editing the cook time of a recipe and verifying the updated value is displayed.
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
     * Tests editing the yield of a recipe and verifying the updated value is displayed.
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
     * Tests adding a tag to a recipe and verifying updates.
     */
    @org.junit.Test
    public void testAddTagUpdatesRecyclerView() {
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

        // Click the "Add tag" button
        Espresso.onView(ViewMatchers.withId(R.id.addTagButton))
                .perform(ViewActions.click());

        // Enter the new tag
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(ViewActions.clearText(), ViewActions.typeText("vegetarian"));

        // Confirm the changes by clicking the Save button
        Espresso.onView(ViewMatchers.withText("Save"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the tag in the UI
        Espresso.onView(ViewMatchers.withText("VEGETARIAN"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Tests removing a tag from a recipe and verifying updates.
     */
    @org.junit.Test
    public void testRemoveTagUpdatesRecyclerView() {
        // Navigate to the cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        String testName = "Spaghetti Bolognese";

        // Select the recipe in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        // Click the "Add tag" button
        Espresso.onView(ViewMatchers.withId(R.id.tagsLayout))
                .perform(ViewActions.scrollCompletelyTo());
        Espresso.onView(ViewMatchers.withId(R.id.deleteTagButton))
                .perform(ViewActions.click());

        // Enter the new tag
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(ViewActions.clearText(), ViewActions.typeText("dairy_free"));

        // Confirm the changes by clicking the Save button
        Espresso.onView(ViewMatchers.withText("Save"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the tag no longer in the UI
        // figure out how to scroll all the way down
        Espresso.onView(ViewMatchers.withId(R.id.tagsLayout))
                .perform(ViewActions.scrollCompletelyTo());
        Espresso.onView(ViewMatchers.withText("DAIRY_FREE"))
                .check(ViewAssertions.doesNotExist());

        SystemClock.sleep(2000);
    }

    /**
     * Tests removing a nonexistent tag.
     */
    @org.junit.Test
    public void testRemoveNonExistentTag() {
        // Navigate to the cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(3000);

        String testName = "Chicken Curry";

        // Select the recipe in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        // Click the "Deleet tag" button
        Espresso.onView(ViewMatchers.withId(R.id.tagsLayout))
                .perform(ViewActions.scrollCompletelyTo());
        Espresso.onView(ViewMatchers.withId(R.id.deleteTagButton))
                .perform(ViewActions.click());

        // Enter the tag to delete
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(EditText.class.getName())))
                .perform(ViewActions.clearText(), ViewActions.typeText("vegetarian"));

        // Confirm the changes by clicking the Save button
        Espresso.onView(ViewMatchers.withText("Save"))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify the updated tags in the UI
        Espresso.onView(ViewMatchers.withText("Tag not found: VEGETARIAN"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }