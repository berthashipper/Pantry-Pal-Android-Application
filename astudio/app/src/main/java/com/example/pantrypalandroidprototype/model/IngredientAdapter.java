package com.example.pantrypalandroidprototype.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;

import java.util.List;

/**
 * The {@code IngredientAdapter} class is a custom adapter for displaying a list of ingredients
 * in a {@link RecyclerView}. Each item in the RecyclerView represents an ingredient, showing
 * its name and allowing interaction for managing dietary tags.
 * <p>
 * This adapter handles the binding of ingredient data to views and provides functionality
 * to manage and display dietary tags through a dialog.
 * </p>
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    /**
     * List of {@link Ingredient} objects to be displayed in the RecyclerView.
     */
    private final List<Ingredient> ingredientList;

    /**
     * Context for inflating views and creating dialogs.
     */
    private final Context context;


    /**
     * Constructs an {@code IngredientAdapter} with the specified ingredient list and context.
     *
     * @param ingredientList The list of ingredients to display.
     * @param context        The context used for inflating views and creating dialogs.
     */
    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }


    /**
     * Inflates the view for an individual ingredient item and creates a new {@link IngredientViewHolder}.
     *
     * @param parent   The parent view group into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new {@link IngredientViewHolder} holding the inflated view.
     */
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }


    /**
     * Binds the data of the ingredient at the specified position to the {@link IngredientViewHolder}.
     *
     * @param holder   The {@link IngredientViewHolder} to bind data to.
     * @param position The position of the ingredient in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.nameTextView.setText(ingredient.getName());
    }


    /**
     * Returns the total number of ingredients in the list.
     *
     * @return The number of ingredients in the list, or 0 if the list is null.
     */
    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }


    /**
     * The {@code IngredientViewHolder} class holds references to the views for each ingredient item.
     */
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dietaryTagsTextView; //TextView to display the ingredient's name, TextView to display dietary tags associated with the ingredient.
        LinearLayout dietaryTagsLayout; //Layout for displaying dietary tags.
        /**
         * Constructs an {@code IngredientViewHolder} and initializes its views.
         *
         * @param itemView The view representing an individual ingredient item.
         */
        public IngredientViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_name);
            dietaryTagsTextView = itemView.findViewById(R.id.dietary_tags);
            dietaryTagsLayout = itemView.findViewById(R.id.dietary_tags_layout);
        }
    }

    /**
     * Displays a dialog allowing the user to select dietary tags for a specified ingredient.
     *
     * @param ingredient The ingredient for which dietary tags are being managed.
     * @param position   The position of the ingredient in the list.
     */
    private void showDietaryTagsDialog(Ingredient ingredient, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Dietary Tags");

        final String[] tags = {"Vegetarian", "Vegan", "Gluten-Free", "Dairy-Free", "Nut-Free"};
        boolean[] checkedItems = new boolean[tags.length];

        // Set the checked state based on the current tags for this ingredient
        for (int i = 0; i < tags.length; i++) {
            checkedItems[i] = ingredient.getTags().contains(tags[i]);
        }

        builder.setMultiChoiceItems(tags, checkedItems, (dialog, which, isChecked) -> {
            if (isChecked) {
                ingredient.addDietaryTag(tags[which]);
            } else {
                ingredient.removeDietaryTag(tags[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> notifyItemChanged(position));
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
