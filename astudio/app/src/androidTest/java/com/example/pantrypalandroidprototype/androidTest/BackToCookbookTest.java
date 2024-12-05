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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test class for verifying navigation back to the Cookbook view.
 */
@RunWith(AndroidJUnit4.class)
public class BackToCookbookTest {

    /**
     * Specifies the activity to launch before each test.
     */
    @Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests whether clicking the "done" button navigates back to the Cookbook view from different screens.
     */
    @Test
    public void testDoneButtonsNavigateToCookbook() {

        /**
         * Back from Search Recipes Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.viewCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.searchRecipesButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToCookbookButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Add Recipe Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.addRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToCookbookIcon))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Recipe Detail View Fragment
         */
        Espresso.onView(ViewMatchers.withText("Spaghetti Bolognese"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Spaghetti Bolognese")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Scale Recipe Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Spaghetti Bolognese")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.scale_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Add Recipe Ingredient Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Quinoa Salad")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.add_recipe_ingredient))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Edit Recipe Ingredient Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Chocolate Cake")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.edit_recipe_ingredient))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Delete Recipe Ingredient Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Pancakes")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.delete_recipe_ingredient))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        SystemClock.sleep(1000);

        /**
         * Back from Edit Recipe Instruction Fragment
         */
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Veggie Stir Fry")),
                        ViewActions.click()
                ));

        Espresso.onView(ViewMatchers.withId(R.id.edit_instruction))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.backToRecipeButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.done_button))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
