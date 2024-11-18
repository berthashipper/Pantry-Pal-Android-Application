package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.databinding.FragmentPantryBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PantryFragment extends Fragment implements IPantryView {

    FragmentPantryBinding binding;
    Listener listener;
    Pantry pantry;


    public static PantryFragment newInstance(IPantryView.Listener listener, Pantry pantry) {
        PantryFragment fragment = new PantryFragment();
        fragment.listener = listener;
        fragment.pantry = pantry;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPantryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.binding.pantryContentsTextView.setText(this.pantry.toString());

        binding.addIngredientsMenuButton.setOnClickListener(v -> onAddIngredientButtonClicked());
    }

    public void onAddIngredientButtonClicked() {
        if (listener != null) listener.onAddIngredientsMenu();
    }
}
