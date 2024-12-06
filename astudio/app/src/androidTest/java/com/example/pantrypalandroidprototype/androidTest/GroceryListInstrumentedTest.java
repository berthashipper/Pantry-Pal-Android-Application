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
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.view.GroceryListFragment;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class GroceryListInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Test if the grocery list displays added items correctly.
     */
    @Test
    public void testAddIngredientToGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        typeText(R.id.itemNameText, "Tomato");
        typeText(R.id.itemQtyText, "2.0");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Verify the ingredient appears in the grocery list
        SystemClock.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Tomato")),
                        ViewActions.click()
                ));

        // Verify the ingredient quantity is correct
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("2.0")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test clearing the grocery list.
     */
    @Test
    public void testClearGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        typeText(R.id.itemNameText, "Potato");
        typeText(R.id.itemQtyText, "1");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Clear the grocery list
        Espresso.onView(ViewMatchers.withId(R.id.clearShoppingListButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes")).perform(ViewActions.click());

        // Verify the grocery list does not contain "Potato"
        Espresso.onView(ViewMatchers.withText("Potato"))
                .check(ViewAssertions.doesNotExist());
    }

    /**
     * Test removing an individual ingredient.
     */
    @Test
    public void testRemoveIngredientFromGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        typeText(R.id.itemNameText, "Carrot");
        typeText(R.id.itemQtyText, "0.5");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        // Remove the ingredient
        Espresso.onView(ViewMatchers.withId(R.id.delete_icon))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes")).perform(ViewActions.click());

        // Verify the grocery list does not contain "Potato"
        Espresso.onView(ViewMatchers.withText("Carrot"))
                .check(ViewAssertions.doesNotExist());
    }

    /**
     * Tests the "Shop For" functionality in recipes adding to grocery list
     */
    @Test
    public void testShopForAddsToGroceryList() {
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Chocolate Cake")),
                        ViewActions.click()
                ));

        SystemClock.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.shopForButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Sugar")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("150.0")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Cocoa Powder")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Flour")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("200.0")))
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


