package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.pantrypalandroidprototype.databinding.MainBinding;
/**
 * {@code MainView} is the implementation of the {@link IMainView} interface and represents the main view of the application.
 * It manages the user interface for interacting with the pantry, cookbook, and generating recipes.
 * This view also handles the setup of UI elements such as buttons and fragments, as well as the interactions with the associated presenter or controller.
 */
public class MainView implements IMainView {

    MainBinding binding;
    FragmentManager fmanager;
    Context context;
    IPantryView.Listener listener;

    public MainView(final Context context,  IPantryView.Listener listener) {
        // Initialize the binding and pantry adapter
        this.context = context;
        this.listener = listener;  // Set the listener to the passed controller
        this.binding = MainBinding.inflate(LayoutInflater.from(context));
        this.fmanager = ((FragmentActivity) context).getSupportFragmentManager();

        // Set up listener for the View Pantry button
        binding.viewPantryButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewPantryMenu();
            }
        });

        // Set up listener for the View Cookbook button
        binding.viewCookbookButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewCookbookMenu();
            }
        });

        // Set up listener for the Generate Recipes button
        binding.generateRecipesMenuButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGenerateRecipes();
            }
        });

        EdgeToEdge.enable((FragmentActivity) context);
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Sets the listener that handles the user interactions for the pantry view.
     *
     * @param listener The listener to handle the interactions, typically the controller or presenter.
     */
    @Override
    public void setListener(IPantryView.Listener listener) {
        this.listener = listener;
    }


    /**
     * Returns the root view for the main layout of the view.
     * This view contains the UI elements that the user interacts with.
     *
     * @return The root {@link View} object representing the main layout of the view.
     */
    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    /**
     * Displays a fragment in the fragment container view.
     * The fragment will replace any existing fragment within the container.
     *
     * @param fragment The fragment to be displayed.
     */
    @Override
    public void displayFragment(@NonNull Fragment fragment) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(binding.fragmentContainerView.getId(), fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    /**
     * Displays a fragment in the fragment container view with a custom transaction name.
     * The fragment will replace any existing fragment within the container, and the transaction will be added to the back stack with the specified name.
     *
     * @param fragment The fragment to be displayed.
     * @param transName The transaction name, or {@code null} if not needed.
     */
    @Override
    public void displayFragment(@NonNull final Fragment fragment, final String transName) {
        final FragmentTransaction ft = this.fmanager.beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment);
        if (transName != null) ft.addToBackStack(transName);
        ft.commit();
    }
}
