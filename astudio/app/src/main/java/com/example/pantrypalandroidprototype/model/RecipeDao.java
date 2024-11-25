package com.example.pantrypalandroidprototype.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Query("SELECT * FROM recipe WHERE name LIKE :query")
    List<Recipe> searchRecipes(String query);

    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes();

    @Delete
    void deleteRecipe(Recipe recipe);
}