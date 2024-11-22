package com.example.pantrypalandroidprototype.androidTest;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
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

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying the "View Cookbook" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class CookbookInstrumentedTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether clicking "Add Recipe" functionality add to Cookbook and get updated.
     */
    @org.junit.Test // passed
    public void testAddRecipe() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Navigate to add recipe screen
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Cinnamon Toast";
        String testDescipt = "A simple and comforting breakfast toast.";
        String testCooktime = "3";
        String testSsize = "1";
        String ingName1 = "Bread";
        String ingQty1 = "2";
        String IngUnit1 = "slices";
        String ingName2 = "Butter";
        String ingQty2 = "2";
        String IngUnit2 = "teaspoons";
        String ingName3 = "Cinnamon";
        String ingQty3 = "2";
        String IngUnit3 = "teaspoons";

        String testInstruction1 = "Toast bread for 3 mins.";
        String testInstruction2 = "Put butter on toast and sprinkle cinnamon on top.";
        String testInstruction3 = "Eat while warm, and enjoy!";

        typeText(R.id.recipeNameEditText, testName);
        typeText(R.id.descriptionEditText, testDescipt);
        typeText(R.id.cookTimeEditText, testCooktime);
        typeText(R.id.servingSizeEditText, testSsize);
        typeText(R.id.ingredientNameEditText, ingName1);
        typeText(R.id.ingredientQuantityEditText, ingQty1);
        typeText(R.id.ingredientUnitEditText, IngUnit1);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo()); // Ensure it's in view
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())); // Ensure visibility

        // Add second ingredient
        typeText(R.id.ingredientNameEditText, ingName2);
        typeText(R.id.ingredientQuantityEditText, ingQty2);
        typeText(R.id.ingredientUnitEditText, IngUnit2);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        //Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Add third ingredient
        typeText(R.id.ingredientNameEditText, ingName3);
        typeText(R.id.ingredientQuantityEditText, ingQty3);
        typeText(R.id.ingredientUnitEditText, IngUnit3);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Ensure that the "Add Instruction" button is fully visible, even if it's out of view.
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .perform(ViewActions.scrollTo());  // Scroll down to the container that holds the button

        // Now scroll to and click the "Add Instruction" button
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(2000);

        // Add instructions
        typeText(R.id.instructionEditText, testInstruction1);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        typeText(R.id.instructionEditText, testInstruction2);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        typeText(R.id.instructionEditText, testInstruction3);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        // Click done button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(4000);

        // Verify that the added recipe is displayed in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes)) // Target RecyclerView
                .perform(RecyclerViewActions.scrollTo( // Scroll to the item
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)) // Match child view
                ));

        // Check if the RecyclerView item with the expected recipe name is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes)) // Target RecyclerView again
                .check(ViewAssertions.matches( // Verify condition
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)) // Ensure the text exists
                ));

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        SystemClock.sleep(4000);

        // Verify that the correct recipe details screen is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recipe_name)) // ID of TextView on the details screen
                .check(ViewAssertions.matches(ViewMatchers.withText(testName))); // Ensure the name matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_description)) // ID of description TextView
                .check(ViewAssertions.matches(ViewMatchers.withText(testDescipt))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_cook_time)) // ID of cook time TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Cook Time: 3 minutes"))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_serving_size)) // ID of serving size TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Serves: 1"))); // Ensure the serving size matches
    }

    /**
     * Tests click on Recipe blocks the details of the recipe is shown correctly.
     * Tests "BackToCookbook" functionality.
     */
    @org.junit.Test //passed
    public void testBackToCookbook() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        SystemClock.sleep(1000);

        // Verify that the correct recipe details screen is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recipe_name)) // ID of TextView on the details screen
                .check(ViewAssertions.matches(ViewMatchers.withText("Grilled Cheese Sandwich"))); // Ensure the name matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_description)) // ID of description TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("A simple and comforting grilled cheese sandwich."))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_cook_time)) // ID of cook time TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Cook Time: 10 minutes"))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_serving_size)) // ID of serving size TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Serves: 1"))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_instructions)) // ID of ingredients
                .check(ViewAssertions.matches(ViewMatchers.withText("Butter the bread and place cheese between slices." + "\n" + "Grill the sandwich until golden brown on both sides."))); // Ensure the description matches

        SystemClock.sleep(2000);

        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        // Verify that is back to the viewCookbook fragment
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.scrollTo(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich"))
                ));
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
