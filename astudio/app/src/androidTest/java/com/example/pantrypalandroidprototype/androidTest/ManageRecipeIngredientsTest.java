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
     * Tests editing an ingredient's details in a recipe and verifying the update in the RecyclerView.
     * It navigates to the recipe, selects an ingredient, edits its name and quantity, and confirms the update.
     * After completing the update, it verifies that the new details are displayed in the RecyclerView.
     */
    @org.junit.Test
    public void testEditRecipeIngredientUpdatesRecyclerView() {
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

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Garlic");
        // Enter the edited quantity
        AddIngredientsInstrumentedTest.typeText(R.id.itemQuantityText, "3");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.editButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("3.0 cloves of Garlic"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        SystemClock.sleep(2000);
    }

    /**
     * Tests adding a new ingredient to a recipe and verifying the update in the RecyclerView.
     * It navigates to the recipe, adds a new ingredient with a name, quantity, and unit, then verifies that the new ingredient appears in the RecyclerView.
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
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Chicken Leg");
        // Enter the Added quantity
        AddIngredientsInstrumentedTest.typeText(R.id.itemQuantityText, "2");
        //Enter the Unit
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "pieces");

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Click the Edit button to confirm
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withText("2.0 pieces of Chicken Leg"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        SystemClock.sleep(2000);
    }


    /**
     * Tests deleting an ingredient from a recipe and verifying the update in the RecyclerView.
     * It navigates to the recipe, deletes an ingredient, and verifies that the ingredient
     * is no longer present in the RecyclerView.
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
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Chicken Breast");

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
}


