package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentGroceryListBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryListFragment extends Fragment implements IGroceryListView {
    FragmentGroceryListBinding binding;
    Pantry pantry;
    GroceryListAdapter adapter;
    Listener listener;
    Map<Ingredient, Double> groceryList;
    static final String ARG_GROCERY_LIST = "grocery_list";

    // Modify newInstance method to accept listener
    public static GroceryListFragment newInstance(Listener listener, Map<Ingredient, Double> groceryList) {
        GroceryListFragment fragment = new GroceryListFragment();
        fragment.groceryList = groceryList;  // Directly assign the map
        fragment.listener = listener;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroceryListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Ensure groceryList is not null and directly use it
        if (groceryList == null) {
            groceryList = new HashMap<>();  // Initialize if null
        }

        // Log the grocery list for debugging
        Log.d("GroceryListFragment", "Initializing with groceryList: " + groceryList);

        adapter = new GroceryListAdapter(groceryList);
        binding.recyclerViewGroceryList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewGroceryList.setAdapter(adapter);

        binding.addIngredientsButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddIngredientsToGroceryListMenu();
            }
        });

        binding.clearShoppingListButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClearShoppingList();
            }
            clearShoppingList();
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Update the grocery list status text based on whether the list is empty or not
        if (groceryList.isEmpty()) {
            binding.groceryStatusText.setText(getString(R.string.pantry_empty));
        } else {
            binding.groceryStatusText.setText("🛒 Shopping List Contents:");
        }

    }

    private void clearShoppingList() {
        pantry.getGroceryList().clear();  // Use getter method for groceryList
        adapter.notifyDataSetChanged();
    }

    // Adapter for the RecyclerView to display the shopping list
    private class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

        final Map<Ingredient, Double> groceryList;

        public GroceryListAdapter(Map<Ingredient, Double> groceryList) {
            this.groceryList = groceryList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_grocery_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Ingredient ingredient = (Ingredient) groceryList.keySet().toArray()[position];
            Double quantity = groceryList.get(ingredient);
            Log.d("GroceryListAdapter", "Binding ingredient: " + ingredient.getName() + ", Qty: " + quantity);
            holder.ingredientName.setText(ingredient.getName());
            holder.ingredientQuantity.setText(quantity + " " + ingredient.getUnit());
            holder.removeButton.setOnClickListener(v -> removeIngredient(ingredient));
        }

        @Override
        public int getItemCount() {
            return groceryList.size();
        }

        private void removeIngredient(Ingredient ingredient) {
            pantry.getGroceryList().remove(ingredient);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView ingredientName, ingredientQuantity;
            ImageView removeButton;

            public ViewHolder(View itemView) {
                super(itemView);
                ingredientName = itemView.findViewById(R.id.ingredient_name);
                ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity);
                removeButton = itemView.findViewById(R.id.delete_icon);
            }
        }
    }
}