package com.example.pantrypalandroidprototype.model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientList;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.nameTextView.setText(ingredient.getName());

        // Display multiple tags or default message if no tags
        if (ingredient.getTags().isEmpty()) {
            holder.dietaryTagsTextView.setText("No tags selected");
        } else {
            holder.dietaryTagsTextView.setText(TextUtils.join(", ", ingredient.getTags()));
        }

        holder.dietaryTagsLayout.setOnClickListener(v -> {
            // Open dialog or new screen to select/deselect dietary tags
            showDietaryTagsDialog(ingredient, position);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dietaryTagsTextView;
        LinearLayout dietaryTagsLayout;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_name);
            dietaryTagsTextView = itemView.findViewById(R.id.dietary_tags);
            dietaryTagsLayout = itemView.findViewById(R.id.dietary_tags_layout);
        }
    }

    private void showDietaryTagsDialog(Ingredient ingredient, int position) {
        // Display a dialog to allow multiple dietary tag selections (e.g., using a MultiChoice dialog)
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
                ingredient.addDietaryTag(tags[which]);  // Ensure this method exists in the Ingredient class
            } else {
                ingredient.removeDietaryTag(tags[which]);  // Ensure this method exists in the Ingredient class
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            notifyItemChanged(position); // Refresh the list to show updated tags
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
