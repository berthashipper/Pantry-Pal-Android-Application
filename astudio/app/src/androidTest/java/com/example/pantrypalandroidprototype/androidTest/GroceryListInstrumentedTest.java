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
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GroceryListInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Test if the grocery list displays added items correctly.
     * This test navigates to the grocery list, adds an ingredient (bread),
     * checks if the ingredient is displayed with the correct quantity and unit,
     * and verifies the correct ingredient details in the grocery list.
     */
    @Test
    public void testAddIngredientToGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Bread");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "2.0");
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "slices");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Verify the ingredient appears in the grocery list
        SystemClock.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Bread")),
                        ViewActions.click()
                ));

        // Verify the ingredient quantity is correct
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("2.0")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("slices")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test if adding an item to the grocery list that was already on the list
     * displays the added item correctly with the updated quantity.
     */
    @Test
    public void testAddPreExistingIngredientToGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.clearShoppingListButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Banana");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "2");
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Banana");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "200");
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());
        SystemClock.sleep(2000);
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());
        SystemClock.sleep(1000);

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        // Verify the ingredient quantity is correct
        SystemClock.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Banana")),
                        ViewActions.click()
                ));
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("200")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test clearing the grocery list.
     * This test navigates to the grocery list, adds an ingredient (potato),
     * clears the grocery list, and verifies that the ingredient is no longer in the list.
     */
    @Test
    public void testClearGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Potato");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "1");
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
     * Test removing an individual ingredient from the grocery list.
     * This test adds an ingredient (carrot), removes it from the list, and verifies it is no longer in the list.
     */
    @Test
    public void testRemoveIngredientFromGroceryList() {
        // Navigate to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        // Add a test ingredient
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton)).perform(ViewActions.click());
        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Carrot");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "0.5");
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
     * Test the "Shop For" functionality in recipes by adding ingredients to the grocery list.
     * This test adds ingredients from a recipe (Grilled Cheese) to the grocery list
     * and verifies that the ingredients are displayed in the list with the correct quantities.
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
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Grilled Cheese Sandwich")),
                        ViewActions.click()
                ));

        SystemClock.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.shopForButton))
                .perform(ViewActions.click());

        SystemClock.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Cheese")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Bread")),
                        ViewActions.click()
                ));
    }

    /**
     * Test the "Shop For" functionality when an ingredient is already in the pantry.
     * This test adds an ingredient (Flour) to the pantry, then shops for ingredients from a recipe
     * (Chocolate Cake) and verifies that the pre-existing ingredient (Flour) is updated
     * to the correct quantity in the grocery list.
     */
    @Test
    public void testShopForAddsToGroceryListPreExistingPantryIngredient() {
        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());
        // Clear pantry from any persisted ingredients
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        // Navigate to Add Ingredients screen
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        AddIngredientsInstrumentedTest.addPantryIngredient("Flour","0.75","cup");

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.clearShoppingListButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Chocolate Cake")),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.shopForButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Flour")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("0.25")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }

    /**
     * Test the "Shop For" functionality when the ingredient is already in the grocery list.
     * This test adds an ingredient (Flour) to the grocery list, then shops for ingredients from a recipe
     * (Chocolate Cake) and verifies that the pre-existing ingredient (Flour) is still updated in the grocery list.
     */
    @Test
    public void testShopForAddsToGroceryListPreExistingGroceryListIngredient() {
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.clearShoppingListButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        AddIngredientsInstrumentedTest.typeText(R.id.itemNameText, "Flour");
        AddIngredientsInstrumentedTest.typeText(R.id.itemQtyText, "20");
        AddIngredientsInstrumentedTest.typeText(R.id.itemUnitText, "cups");
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // Go back to Grocery List screen
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Chocolate Cake")),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.shopForButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Flour")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("21")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }

    /**
     * Test the "Shop For" functionality on a user-uploaded recipe.
     * This test adds an ingredient (Tofu) to the pantry, then adds a recipe that calls for 200 Tofu.
     * Then shops for the ingredients in the recipe and verifies that the correct quantity is added to grocery list.
     */
    @Test
    public void testShopForOnUploadedRecipe() {
        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.clearPantryButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addIngredientsButton))
                .perform(ViewActions.click());

        AddIngredientsInstrumentedTest.addPantryIngredient("Tofu","1","block");

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewGroceryListButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.clearShoppingListButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Yes"))
                .perform(ViewActions.click());

        SystemClock.sleep(5000);

        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        // Input test data
        String testName = "Tofu Test";
        String testDescipt = "Lots of tofu";
        String testCooktime = "100";
        String testSsize = "200";
        String ingName1 = "Tofu";
        String ingQty1 = "200";
        String IngUnit1 = "blocks";

        AddIngredientsInstrumentedTest.typeText(R.id.recipeNameEditText, testName);
        AddIngredientsInstrumentedTest.typeText(R.id.descriptionEditText, testDescipt);
        AddIngredientsInstrumentedTest.typeText(R.id.cookTimeEditText, testCooktime);
        AddIngredientsInstrumentedTest.typeText(R.id.servingSizeEditText, testSsize);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientNameEditText, ingName1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientQuantityEditText, ingQty1);
        AddIngredientsInstrumentedTest.typeText(R.id.ingredientUnitEditText, IngUnit1);

        // Click "Add ingredient" button
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).perform(ViewActions.scrollTo());
        Espresso.onView(ViewMatchers.withId(R.id.addIngredientButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click done button
        Espresso.onView(ViewMatchers.withId(R.id.doneButton))
                .perform(ViewActions.scrollTo(), ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName)),
                        ViewActions.click()
                ));

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.shopForButton))
                .perform(ViewActions.click());

        SystemClock.sleep(2000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_grocery_list))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Tofu")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("199")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(2000);
    }
}


