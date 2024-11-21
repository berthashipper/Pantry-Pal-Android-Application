package com.example.pantrypalandroidprototype;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.model.Ingredient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

/**
 * Instrumented test class for verifying the "View Cookbook" functionality.
 */
@RunWith(AndroidJUnit4.class)
public class ViewCookbookTest {

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

        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());


        // Input test data
        String testName = "Toast";
        String testDescipt = "A simple and comforting Toast.";
        String testCooktime = "3";
        String testSsize = "1";
        String ingName1 = "Bread";
        String ingQty1 = "2";
        String IngUnit1 = "slices";
        String ingName2 = "Butter";
        String ingQty2 = "20";
        String IngUnit2 = "grams";
        String testInstruction = "Use Toaster for 3 mins.";

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
        SystemClock.sleep(1000);

        //Add second ingredient
        typeText(R.id.ingredientNameEditText, ingName2);
        typeText(R.id.ingredientQuantityEditText, ingQty2);
        typeText(R.id.ingredientUnitEditText, IngUnit2);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        typeText(R.id.instructionEditText, testInstruction);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addInstructionButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton)).perform(ViewActions.click());

        // Wait for UI updates
        SystemClock.sleep(1000);

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


    }

    /**
     * Tests "Search Recipe" functionality.
     */
    @Test
    public void testSearchRecipe() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

    /**
     * Tests click on Recipe blocks the details of the recipe is shown correctly.
     */
    @Test
    public void testRecipeDetails() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
    }

    /**
     * Tests "BackToCookbook" functionality.
     */
    @Test
    public void testBackToCookbook() {
        // Navigate to cookbook screen
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());
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
