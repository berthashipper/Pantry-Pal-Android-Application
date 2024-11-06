package com.example.pantrypalandroidprototype;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrypalandroidprototype.model.Ingredient;
import com.example.pantrypalandroidprototype.model.IngredientAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ingredientRecyclerView;
    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredientList;
    private EditText ingredientNameInput, ingredientQtyInput, ingredientUnitInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingredientRecyclerView = findViewById(R.id.ingredient_recycler_view);
        ingredientList = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(ingredientList, this);

        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        ingredientNameInput = findViewById(R.id.itemNameText);
        ingredientQtyInput = findViewById(R.id.itemQtyText);
        ingredientUnitInput = findViewById(R.id.itemUnitText);

        Button addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(v -> addIngredient());
    }

    private void addIngredient() {
        String name = ingredientNameInput.getText().toString().trim();
        String qtyString = ingredientQtyInput.getText().toString().trim();
        String unit = ingredientUnitInput.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(this, "Please enter both name and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(qtyString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        Ingredient newIngredient = new Ingredient(name, quantity, unit, new HashSet<>());
        ingredientList.add(newIngredient);
        ingredientAdapter.notifyItemInserted(ingredientList.size() - 1);

        ingredientNameInput.setText("");
        ingredientQtyInput.setText("");
        ingredientUnitInput.setText("");
    }
}
