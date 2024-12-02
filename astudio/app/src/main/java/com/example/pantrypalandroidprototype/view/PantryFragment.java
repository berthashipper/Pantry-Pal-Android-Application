package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentPantryBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code PantryFragment} is a {@link Fragment} implementation of the {@link IPantryView} interface.
 * It represents the user interface for managing pantry items such as adding, deleting, editing, and searching ingredients.
 * The fragment provides buttons to navigate to the respective pantry operations and displays the current contents of the pantry.
 */
public class PantryFragment extends Fragment implements IPantryView {

    FragmentPantryBinding binding;
    Listener listener;
    Pantry pantry;

    /**
     * Creates a new instance of the {@code PantryFragment}.
     *
     * @param listener The listener that will handle the interactions triggered by the fragment's UI elements.
     * @param pantry The pantry object to be displayed and managed by the fragment.
     * @return A new instance of {@code PantryFragment}.
     */
    public static PantryFragment newInstance(IPantryView.Listener listener, Pantry pantry) {
        PantryFragment fragment = new PantryFragment();
        fragment.listener = listener;
        fragment.pantry = pantry;
        return fragment;
    }

    /**
     * Called to inflate the fragment's layout. This method initializes the view for the pantry fragment.
     *
     * @param inflater The LayoutInflater used to inflate the view.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A saved instance state for the fragment.
     * @return The root view of the fragment's layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPantryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        return rootView;
    }


    /**
     * Called after the fragment's view has been created. This method sets up the event listeners for the various buttons.
     * It also displays the contents of the pantry in a {@link TextView}.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A saved instance state for the fragment.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addIngredientsButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.viewPantryButton.setOnClickListener(v -> onViewPantryMenu());
        binding.deleteIngredientsButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.editIngredientsButton.setOnClickListener(v -> onEditButtonClicked());
        binding.searchIngredientsButton.setOnClickListener(v -> onSearchIngredientsMenu());


        this.binding.pantryContentsTextView.setText(this.pantry.toString());
    }

    /**
     * Handles the click event for the Delete Ingredients button.
     * It triggers the listener's {@link IPantryView.Listener#onDeleteIngredientsMenu()} method.
     */
    public void onAddIngredientButtonClicked() {
        if (listener != null) {
            listener.onAddIngredientsMenu();
        }
    }

    /**
     * Handles the click event for the View Pantry button.
     * It triggers the listener's {@link IPantryView.Listener#onViewPantryMenu()} method.
     */
    public void onViewPantryMenu() {
        if (listener != null) {
            listener.onViewPantryMenu();
        }
    }

    /**
     * Handles the click event for the Delete Ingredients button.
     * It triggers the listener's {@link IPantryView.Listener#onDeleteIngredientsMenu()} method.
     */
    public void onDeleteButtonClicked() {
        if (listener != null) {
            listener.onDeleteIngredientsMenu();
        }
    }

    /**
     * Handles the click event for the Edit Ingredients button.
     * It triggers the listener's {@link IPantryView.Listener#onEditIngredientsMenu()} method.
     */
    public void onEditButtonClicked() {
        if (listener != null) {
            listener.onEditIngredientsMenu();
        }
    }

    /**
     * Handles the click event for the Search Ingredients button.
     * It triggers the listener's {@link IPantryView.Listener#onSearchIngredientsMenu()} method.
     */
    public void onSearchIngredientsMenu() {
        if (listener != null) {
            listener.onSearchIngredientsMenu();
        }
    }
}
