package com.example.pantrypalandroidprototype.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public Cookbook cookbook;

    public List<Recipe> recipes;
    public Context context;
    public OnRecipeClickListener onRecipeClickListener;

    public RecipeAdapter(Cookbook cookbook, Context context, OnRecipeClickListener onRecipeClickListener) {
        this.cookbook = cookbook;
        this.context = context;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = new ArrayList<>(cookbook.recipeList.values()).get(position);
        holder.recipeName.setText(recipe.recipeName);
        holder.itemView.setOnClickListener(v -> onRecipeClickListener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return cookbook.recipeList.size();
    }

    public void updateRecipes(Cookbook newCookbook) {
        this.cookbook = newCookbook;
        notifyDataSetChanged();
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }
    }

}
