package com.example.pantrypalandroidprototype.androidTest;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.controller.ControllerActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying the "View Cookbook" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class CookbookInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether the "Add Recipe" functionality adds a new recipe to the cookbook
     * and that it is properly updated and displayed in the RecyclerView.
     * This test verifies the complete recipe addition process, from entering details
     * such as name, description, ingredients, and instructions to the final
     * display of the new recipe in the cookbook.
     */
    @org.junit.Test
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

        AddIngredientsInstrumentedTest.typeText(R.id.recipeNameEditText, testName);
        AddIngredientsInstrumentedTest.typeText(R.id.descriptionEditText, testDescipt);
        AddIngredientsInstrumentedTest.typeText(R.id.cookTimeEditText, testCooktime);
        AddIngredientsInstrumentedTest.typeText(R.id.servingSizeEditText, testSsize);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit1);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo()); // Ensure it's in view
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())); // Ensure visibility

        // Add second ingredient
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName2);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty2);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit2);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        //Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Add third ingredient
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName3);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty3);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit3);

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
        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, testInstruction1);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, testInstruction2);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, testInstruction3);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        // Click done button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(2000);

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

        SystemClock.sleep(2000);

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

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
     * Tests the functionality of navigating to a recipe details screen and
     * verifying that the details are correctly displayed. It also tests the
     * "BackToCookbook" button, ensuring that the user can navigate back to the
     * cookbook screen from the recipe details screen.
     */
    @org.junit.Test
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

        SystemClock.sleep(2000);

        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify back to cookbook
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.scrollTo(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich"))
                ));

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich"))
                ));
    }


    /**
     * Tests whether the "Delete Recipe" functionality deletes recipe from the cookbook.
     */
    @org.junit.Test
    public void testDeleteRecipe() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Navigate to add recipe screen
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Test To Delete Recipe";
        String testDescipt = "";
        String testCooktime = "";
        String testSsize = "";
        String ingName1 = "Test";
        String ingQty1 = "1";
        String IngUnit1 = "";

        String testInstruction1 = "Test";

        AddIngredientsInstrumentedTest.typeText(R.id.recipeNameEditText, testName);
        AddIngredientsInstrumentedTest.typeText(R.id.descriptionEditText, testDescipt);
        AddIngredientsInstrumentedTest.typeText(R.id.cookTimeEditText, testCooktime);
        AddIngredientsInstrumentedTest.typeText(R.id.servingSizeEditText, testSsize);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit1);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo()); // Ensure it's in view
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())); // Ensure visibility

        // Ensure that the "Add Instruction" button is fully visible, even if it's out of view.
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .perform(ViewActions.scrollTo());  // Scroll down to the container that holds the button

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo());
        // Add instructions
        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, testInstruction1);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);

        // Click done button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(2000);

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

        SystemClock.sleep(2000);

        // Locate the delete icon within the recipe's item and click it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        new DeleteIconClickAction(R.id.delete_icon)
                ));

        // Verify the recipe no longer exists in the RecyclerView
        Espresso.onView(ViewMatchers.withText(testName))
                .check(ViewAssertions.doesNotExist());

        SystemClock.sleep(2000);
    }

    /**
     * Tests whether a recipe added to the cookbook is still visible after
     * navigating away and returning to the cookbook screen. This ensures
     * that data persists across navigation actions.
     */
    @org.junit.Test
    public void testAddRecipeAndNavigateAway() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        // Navigate to add recipe screen
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Toast";
        String testDescipt = "A simple toast.";
        String testCooktime = "3";
        String testSsize = "1";
        String ingName1 = "Bread";
        String ingQty1 = "2";
        String IngUnit1 = "slices";

        String testInstruction1 = "Toast bread for 3 mins.";

        AddIngredientsInstrumentedTest.typeText(R.id.recipeNameEditText, testName);
        AddIngredientsInstrumentedTest.typeText(R.id.descriptionEditText, testDescipt);
        AddIngredientsInstrumentedTest.typeText(R.id.cookTimeEditText, testCooktime);
        AddIngredientsInstrumentedTest.typeText(R.id.servingSizeEditText, testSsize);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit1);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo()); // Ensure it's in view
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())); // Ensure visibility


        SystemClock.sleep(2000);

        // Ensure that the "Add Instruction" button is fully visible, even if it's out of view.
        Espresso.onView(ViewMatchers.withId(R.id.fragmentContainerView))
                .perform(ViewActions.scrollTo());  // Scroll down to the container that holds the button

        // Now scroll to and click the "Add Instruction" button
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(2000);

        // Add instructions
        AddIngredientsInstrumentedTest.typeText(R.id.instructionEditText, testInstruction1);
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());
        SystemClock.sleep(2000);


        // Click done button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        SystemClock.sleep(2000);

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

        SystemClock.sleep(2000);

        // Scroll to the item and perform a click on it
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

        // Verify that the correct recipe details screen is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recipe_name))
                .check(ViewAssertions.matches(ViewMatchers.withText(testName))); // Ensure the name matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_description)) // ID of description TextView
                .check(ViewAssertions.matches(ViewMatchers.withText(testDescipt))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_cook_time)) // ID of cook time TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Cook Time: 3 minutes"))); // Ensure the description matches

        Espresso.onView(ViewMatchers.withId(R.id.recipe_serving_size)) // ID of serving size TextView
                .check(ViewAssertions.matches(ViewMatchers.withText("Serves: 1"))); // Ensure the serving size matches

        // Navigate back to the pantry screen
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Navigate back to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Verify that the correct recipe details screen is still displayed after navigation
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.scrollTo(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName))
                ));

        // Ensure that "Toast" is still in the RecyclerView after navigating back
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName))
                ));
    }

    /**
     * Custom ViewAction to click a specific delete_icon within the RecyclerView cookbook.
     */
    private static class DeleteIconClickAction implements ViewAction {
        private final int viewId;

        public DeleteIconClickAction(int viewId) {
            this.viewId = viewId;
        }

        @Override
        public Matcher<View> getConstraints() {
            return ViewMatchers.isDisplayed(); // Ensure the view is visible
        }

        @Override
        public String getDescription() {
            return "Click on a specific child view with ID " + viewId;
        }

        @Override
        public void perform(UiController uiController, View view) {
            View childView = view.findViewById(viewId);
            if (childView != null) {
                childView.performClick();
            } else {
                throw new PerformException.Builder()
                        .withCause(new IllegalStateException("View with ID " + viewId + " not found"))
                        .build();
            }
        }
    }
}