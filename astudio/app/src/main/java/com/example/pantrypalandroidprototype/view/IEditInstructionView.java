package com.example.pantrypalandroidprototype.view;

import com.example.pantrypalandroidprototype.model.Recipe;

public interface IEditInstructionView {

    interface Listener{
        void onEditInstruction(String instruction);
        void onAddInstruction(String instruction);
        void onEditInstructionDone();
    }

}
