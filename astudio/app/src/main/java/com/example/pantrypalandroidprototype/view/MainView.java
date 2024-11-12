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

public class MainView implements IMainView {

    final private MainBinding binding;
    final private FragmentManager fmanager;

    /**
     * Constructor method.
     *
     * @param context the context in which the UI is to operate - influences look & feel.
     * @param factivity The android activity the screen is associated with.
     */
    public MainView(final Context context, final FragmentActivity factivity) {
        this.binding = MainBinding.inflate(LayoutInflater.from(context));

        // configure app to maximize space usage by drawing of top of system bars
        EdgeToEdge.enable(factivity);
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.fmanager = factivity.getSupportFragmentManager();
    }

    /**
     * Retrieve the graphical widget (android view) at the root of the screen hierarchy/
     * @return the screen's root android view (widget)
     */
    @Override
    public View getRootView() { return this.binding.getRoot(); }

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument.
     *
     * @param fragment The fragment to be displayed.
     */
    @Override
    public void displayFragment(@NonNull final Fragment fragment) {
        this.displayFragment(fragment, null);
    }

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument,
     * and adds the transaction to the back stack, under the name specified as an argument (iff non-null).
     *
     * @param fragment The fragment to be displayed.
     * @param transName the name this transaction can be referred by.
     */
    @Override
    public void displayFragment(@NonNull final Fragment fragment, final String transName) {
        final FragmentTransaction ft = this.fmanager.beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment);
        if (transName != null) ft.addToBackStack(transName);
        ft.commit();
    }
}

