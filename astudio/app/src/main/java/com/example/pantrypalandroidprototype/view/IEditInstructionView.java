package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;
/**
 * Interface that defines the methods for a view that allows a user to edit instructions in a recipe.
 */
public interface IEditInstructionView {

    /**
     * Listener interface for handling instruction editing events.
     */
    interface Listener {

        /**
         * Called when an instruction is edited.
         *
         * @param instruction The updated instruction text.
         */
        void onEditInstruction(String instruction);

        /**
         * Called when the editing of the instruction is completed.
         */
        void onEditInstructionDone();

        /**
         * Called when the user wants to navigate back to the recipe.
         */
        void onBackToRecipe();
    }
}