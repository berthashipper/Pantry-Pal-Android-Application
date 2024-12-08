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
import com.example.pantrypalandroidprototype.databinding.ItemGroceryListBinding;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.Pantry;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;
/**
 * Fragment that displays and manages the grocery list of ingredients for shopping.
 * This fragment allows users to view, edit, delete, and add ingredients to the grocery list.
 */
public class GroceryListFragment extends Fragment implements IGroceryListView {
    FragmentGroceryListBinding binding;
    Pantry pantry;
    GroceryListAdapter adapter;
    Listener listener;
    Map<Ingredient, Double> groceryList;

    /**
     * Creates a new instance of the GroceryListFragment.
     *
     * @param listener    The listener to handle actions such as adding or removing ingredients.
     * @param groceryList The map of ingredients and their quantities to display in the list.
     * @return A new instance of GroceryListFragment.
     */
    public static GroceryListFragment newInstance(Listener listener, Map<Ingredient, Double> groceryList) {
        GroceryListFragment fragment = new GroceryListFragment();
        fragment.groceryList = groceryList;  // Directly assign the map
        fragment.listener = listener;
        return fragment;
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater to inflate views.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    /**
     * Called when the fragment's view has been created and fully initialized.
     *
     * @param view               The root view of the fragment's layout.
     * @param savedInstanceState The saved state of the fragment.
     */
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

    /**
     * Handles the event when an ingredient in the grocery list needs to be edited.
     * This method is called to display the dialog where the user can edit the quantity of the ingredient.
     *
     * @param ingredient The ingredient to be edited.
     */
    @Override
    public void onEditIngredientGroceryList(Ingredient ingredient) {
        // Call the method to show the edit quantity dialog
        adapter.showEditQuantityDialog(ingredient);
    }

    /**
     * Refreshes the grocery list and updates the adapter when the fragment resumes.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Refresh the grocery list and adapter
        adapter.notifyDataSetChanged();
        binding.groceryStatusText.setText(groceryList.isEmpty() ?
                getString(R.string.pantry_empty) : "🛒 Shopping For:");
    }

    /**
     * Shows a confirmation dialog to clear the entire grocery list.
     */
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

    /**
     * Displays a message indicating that the grocery list has been cleared.
     */
    public void showClearedMessage() {
        Snackbar.make(binding.getRoot(), "Grocery List cleared", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Displays a message indicating that an ingredient has been deleted from the grocery list.
     *
     * @param ingredient The ingredient that was deleted.
     */
    public void showDeletedMessage(Ingredient ingredient) {
        Snackbar.make(binding.getRoot(), "Deleted " + ingredient.getName() + " from Grocery List", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Displays a message indicating that the quantity of an ingredient has been updated.
     *
     * @param name     The name of the ingredient.
     * @param quantity The updated quantity of the ingredient.
     */
    public void showUpdatedQuantityMessage(String name, Double quantity) {
        Snackbar.make(binding.getRoot(), "Updated " + name + " to quantity " + quantity, Snackbar.LENGTH_SHORT).show();
    }


    /**
     * Adapter for the RecyclerView that displays ingredients in the grocery list.
     */
    // Adapter for the RecyclerView to display the shopping list
    private class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
        final Map<Ingredient, Double> groceryList;

        /**
         * Constructs the adapter with the provided grocery list.
         *
         * @param groceryList The map of ingredients and their quantities to display.
         */
        public GroceryListAdapter(Map<Ingredient, Double> groceryList) {
            this.groceryList = groceryList;
        }

        /**
         * Creates a new ViewHolder for the RecyclerView.
         * This method is called when a new item view needs to be created.
         *
         * @param parent   The parent view group that the item view will be attached to.
         * @param viewType The view type of the new view.
         * @return A new instance of ViewHolder containing the view for the item.
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemGroceryListBinding binding = ItemGroceryListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        /**
         * Binds data to the specified ViewHolder. This method is called to display the data at a specific position in the RecyclerView.
         *
         * @param holder   The ViewHolder which should be updated with data.
         * @param position The position of the item within the RecyclerView.
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Ingredient ingredient = (Ingredient) groceryList.keySet().toArray()[position];
            Double quantity = groceryList.get(ingredient);
            holder.bind(ingredient, quantity);
            Log.d("GroceryListAdapter", "Binding ingredient: " + ingredient.getName() + ", Qty: " + quantity);
        }

        /**
         * Returns the number of items in the grocery list.
         *
         * @return The number of items in the grocery list.
         */
        @Override
        public int getItemCount() {
            return groceryList.size();
        }

        /**
         * Removes the specified ingredient from the grocery list after confirming the action.
         *
         * @param ingredient The ingredient to be removed.
         */
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
                            Snackbar.make(binding.getRoot(), ingredient.getName() + " deleted from Grocery List", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // Just dismiss the dialog if "No" is clicked
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        }

        /**
         * Displays a dialog to edit the quantity of the specified ingredient.
         *
         * @param ingredient The ingredient whose quantity is to be edited.
         */
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

        /**
         * Updates the quantity of the specified ingredient in the grocery list.
         *
         * @param ingredient  The ingredient whose quantity is being updated.
         * @param newQuantity The new quantity for the ingredient.
         */
        public void onEditQuantity(Ingredient ingredient, double newQuantity) {
            if (groceryList.containsKey(ingredient)) {
                groceryList.put(ingredient, newQuantity);
                notifyDataSetChanged(); // Update the adapter
                listener.onEditIngredientGroceryList(ingredient);
                showUpdatedQuantityMessage(ingredient.getName(), newQuantity);
            }
        }

        /**
         * ViewHolder for displaying each ingredient item in the RecyclerView.
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemGroceryListBinding binding;

            public ViewHolder(ItemGroceryListBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            /**
             * Binds the ingredient and its quantity to the view.
             *
             * @param ingredient The ingredient to display.
             * @param quantity   The quantity of the ingredient.
             */
            public void bind(Ingredient ingredient, Double quantity) {
                binding.ingredientName.setText(ingredient.getName());
                binding.ingredientQuantity.setText(quantity + " " + ingredient.getUnit());

                binding.editIcon.setOnClickListener(v -> {
                    showEditQuantityDialog(ingredient);
                });

                binding.deleteIcon.setOnClickListener(v -> removeIngredient(ingredient));
            }
        }
    }
}