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

        binding.addIngredientsButton.setOnClickListener(v -> onAddIngredientButtonClicked());
        binding.viewPantryButton.setOnClickListener(v -> onViewPantryMenu());
        binding.deleteIngredientsButton.setOnClickListener(v -> onDeleteButtonClicked());
        binding.editIngredientsButton.setOnClickListener(v -> onEditButtonClicked());


        this.binding.pantryContentsTextView.setText(this.pantry.toString());
    }

    public void onAddIngredientButtonClicked() {
        if (listener != null) {
            listener.onAddIngredientsMenu();
        }
    }

    public void onViewPantryMenu() {
        if (listener != null) {
            listener.onViewPantryMenu();
        }
    }

    public void onDeleteButtonClicked(){
        if (listener != null) {
            listener.onDeleteIngredientsMenu();
        }
    }

    public void onEditButtonClicked(){
        if (listener != null) {
            listener.onEditIngredientsMenu();
        }
    }
}
