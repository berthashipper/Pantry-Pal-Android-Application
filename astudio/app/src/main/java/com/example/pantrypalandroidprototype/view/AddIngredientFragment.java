package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentAddItemsBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.IngredientAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddIngredientFragment extends Fragment {

    public interface Listener {
        void onAddIngredients(List<Ingredient> ingredients);
        void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags);
        void onItemsDone();
    }

    private FragmentAddItemsBinding binding;
    private Listener listener;
    private List<Ingredient> addedIngredients = new ArrayList<>();
    private IngredientAdapter ingredientAdapter;

    public static AddIngredientFragment newInstance(Listener listener) {
        AddIngredientFragment fragment = new AddIngredientFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddItemsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        binding.addIngredientButton.setOnClickListener(v -> onAddButtonClicked());
        binding.doneButton.setOnClickListener(v -> onDoneButtonClicked());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredientAdapter = new IngredientAdapter(addedIngredients, getContext());
        binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }

    private void onAddButtonClicked() {
        String name = binding.itemNameText.getText().toString().trim();
        String qtyString = binding.itemQtyText.getText().toString().trim();
        String unit = binding.itemUnitText.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double qty = Double.parseDouble(qtyString);
        Set<Ingredient.dietary_tags> tags = new HashSet<>();

        // Check dietary tags (checkboxes) and add corresponding tags
        if (binding.veganCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.VEGAN);
        }
        if (binding.glutenFreeCheckbox.isChecked()) {
            tags.add(Ingredient.dietary_tags.GLUTEN_FREE);
        }

        Ingredient newIngredient = new Ingredient(name, qty, unit, new HashSet<>());
        addedIngredients.add(newIngredient);
        ingredientAdapter.notifyItemInserted(addedIngredients.size() - 1);
        clearInputs();
    }

    private void clearInputs() {
        binding.itemNameText.setText("");
        binding.itemQtyText.setText("");
        binding.itemUnitText.setText("");
        binding.veganCheckbox.setChecked(false);
        binding.glutenFreeCheckbox.setChecked(false);
    }

    private void onDoneButtonClicked() {
        if (listener != null) {
            listener.onAddIngredients(addedIngredients);
        }
    }
}
