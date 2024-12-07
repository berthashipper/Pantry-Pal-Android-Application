package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.text.InputType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.databinding.FragmentGroceryListBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
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
            showClearListConfirmationDialog();
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
            binding.groceryStatusText.setText("🛒 Shopping For:");
        }
    }

    @Override
    public void onEditIngredientGroceryList(Ingredient ingredient) {
        // Call the method to show the edit quantity dialog
        adapter.showEditQuantityDialog(ingredient);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the grocery list and adapter
        adapter.notifyDataSetChanged();
        binding.groceryStatusText.setText(groceryList.isEmpty() ?
                getString(R.string.pantry_empty) : "🛒 Shopping For:");
    }

    private void showClearListConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Clear Grocery List")
                .setMessage("Are you sure you want to clear all ingredients? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (listener != null) {
                        listener.onClearShoppingList();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public void showClearedMessage() {
        Snackbar.make(getView(), "Grocery List cleared", Snackbar.LENGTH_LONG).show();
    }

    public void showDeletedMessage(Ingredient ingredient) {
        Snackbar.make(getView(), "Deleted " + ingredient.getName() + " from Grocery List", Snackbar.LENGTH_SHORT).show();
    }

    public void showUpdatedQuantityMessage(String name, Double quantity) {
        Snackbar.make(getView(), "Updated " + name + " to quantity " + quantity, Snackbar.LENGTH_SHORT).show();
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
            // Set up the edit button to show the dialog
            holder.editButton.setOnClickListener(v -> {
                // Show the edit quantity dialog when the edit button is clicked
                showEditQuantityDialog(ingredient);
            });
            holder.removeButton.setOnClickListener(v -> removeIngredient(ingredient));
        }

        @Override
        public int getItemCount() {
            return groceryList.size();
        }

        private void removeIngredient(Ingredient ingredient) {
            // Show a confirmation dialog before removing the ingredient
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Ingredient")
                    .setMessage("Are you sure you want to delete " + ingredient.getName() + " from the grocery list? This action cannot be undone.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Perform the deletion
                        listener.onRemoveIngredient(ingredient);
                        notifyDataSetChanged(); // Update the UI
                        if (getView() != null) {
                            Snackbar.make(getView(), ingredient.getName() + " deleted from Grocery List", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // Just dismiss the dialog if "No" is clicked
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        }

        private void showEditQuantityDialog(Ingredient ingredient) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Edit Quantity of " + ingredient.getName());

            final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // Set the initial value of the input field to the current quantity
            Double currentQuantity = groceryList.get(ingredient);
            input.setText(String.valueOf(currentQuantity));
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                double newQuantity = Double.parseDouble(input.getText().toString());
                onEditQuantity(ingredient, newQuantity);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.show();
        }

        public void onEditQuantity(Ingredient ingredient, double newQuantity) {
            if (groceryList.containsKey(ingredient)) {
                groceryList.put(ingredient, newQuantity);
                notifyDataSetChanged(); // Update the adapter
                listener.onEditIngredientGroceryList(ingredient);
                showUpdatedQuantityMessage(ingredient.getName(), newQuantity);
                //Snackbar.make(requireView(), "Updated quantity of " + ingredient.getName() + " to " + newQuantity, Snackbar.LENGTH_SHORT).show();
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView ingredientName, ingredientQuantity;
            ImageView removeButton, editButton;

            public ViewHolder(View itemView) {
                super(itemView);
                ingredientName = itemView.findViewById(R.id.ingredient_name);
                ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity);
                removeButton = itemView.findViewById(R.id.delete_icon);
                editButton = itemView.findViewById(R.id.edit_icon);
            }
        }
    }
}