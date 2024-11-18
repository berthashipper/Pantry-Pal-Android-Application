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
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.MainBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.PantryAdapter;

import java.util.List;

public class MainView implements IMainView {

    MainBinding binding;
    FragmentManager fmanager;
    Context context;

    /**
     * Constructor method.
     *
     * @param context the context in which the UI is to operate - influences look & feel.
     */
    public MainView(final Context context) {
        // Initialize the binding and pantry adapter
        this.context = context;
        this.binding = MainBinding.inflate(LayoutInflater.from(context));
        this.fmanager = ((FragmentActivity) context).getSupportFragmentManager();


        // Configure app to maximize space usage by drawing on top of system bars
        EdgeToEdge.enable((FragmentActivity) context);
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Retrieve the graphical widget (android view) at the root of the screen hierarchy.
     *
     * @return the screen's root android view (widget)
     */
    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument.
     *
     * @param fragment The fragment to be displayed.
     */
    @Override
    public void displayFragment(@NonNull Fragment fragment) {
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ft.replace(binding.fragmentContainerView.getId(), fragment);
        ft.commit();
    }

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument,
     * and adds the transaction to the back stack, under the name specified as an argument (if non-null).
     *
     * @param fragment  The fragment to be displayed.
     * @param transName the name this transaction can be referred by.
     */
    @Override
    public void displayFragment(@NonNull final Fragment fragment, final String transName) {
        final FragmentTransaction ft = this.fmanager.beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment);  // Ensure fragmentContainerView ID exists in your layout XML
        if (transName != null) ft.addToBackStack(transName);
        ft.commit();
    }


    public void onNavigateBackToPantry() {
        PantryFragment pantryFragment = new PantryFragment();
        fmanager.beginTransaction()
                .replace(R.id.fragmentContainerView, pantryFragment)
                .commit();
    }
}
