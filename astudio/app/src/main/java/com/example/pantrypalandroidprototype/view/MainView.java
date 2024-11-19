package com.example.pantrypalandroidprototype.view;

import android.content.Context;
import android.os.Bundle;
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
import com.example.pantrypalandroidprototype.controller.ControllerActivity;
import com.example.pantrypalandroidprototype.databinding.FragmentPantryBinding;
import com.example.pantrypalandroidprototype.databinding.MainBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.PantryAdapter;

import java.util.List;

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


        // Configure app to maximize space usage by drawing on top of system bars
        EdgeToEdge.enable((FragmentActivity) context);
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void setListener(IPantryView.Listener listener) {
        this.listener = listener;
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(@NonNull Fragment fragment) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(binding.fragmentContainerView.getId(), fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void displayFragment(@NonNull final Fragment fragment, final String transName) {
        final FragmentTransaction ft = this.fmanager.beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment);
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
