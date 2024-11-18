package com.example.pantrypalandroidprototype;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.view.AddIngredientFragment;
import com.example.pantrypalandroidprototype.view.PantryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddIngredientFragment.Listener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare a list of ingredients to pass to the fragment
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Tomato", 3, "pcs", null));
        ingredients.add(new Ingredient("Flour", 500, "g", null));

        Bundle bundle = new Bundle();
        bundle.putSerializable("addedIngredients", (ArrayList<Ingredient>) ingredients);

        PantryFragment pantryFragment = new PantryFragment();
        pantryFragment.setArguments(bundle);

        // Replace the container with the pantry fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, pantryFragment)
                .commit();
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    @Override
    public void onAddIngredients(List<Ingredient> ingredients) {
        PantryFragment pantryFragment = new PantryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("addedIngredients", new ArrayList<>(ingredients));
        pantryFragment.setArguments(bundle);
        displayFragment(pantryFragment);
    }

    @Override
    public void onAddIngredient(String name, double qty, String unit, Set<Ingredient.dietary_tags> tags) {

    }

    @Override
    public void onItemsDone() {
        displayFragment(new PantryFragment());
    }
}
