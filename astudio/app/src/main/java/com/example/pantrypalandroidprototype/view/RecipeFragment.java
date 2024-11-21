package com.example.pantrypalandroidprototype.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.R;
import com.example.pantrypalandroidprototype.model.Recipe;
import com.example.pantrypalandroidprototype.model.RecipeAdapter;

import java.io.Serializable;
import java.util.List;

public class RecipeFragment extends Fragment {

    private static final String ARG_RECIPES = "recipes";
    List<Recipe> recipes;

    public static RecipeFragment newInstance(List<Recipe> recipes) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPES, (Serializable) recipes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipes = (List<Recipe>) getArguments().getSerializable(ARG_RECIPES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recipe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeAdapter adapter = new RecipeAdapter(recipes, getContext(), recipe -> {
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipe);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);
        return view;
    }
}

