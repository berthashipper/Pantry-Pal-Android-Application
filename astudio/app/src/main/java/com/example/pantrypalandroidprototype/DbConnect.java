package com.example.pantrypalandroidprototype;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DbConnect extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "PantryPal.db"; // Combined database for simplicity
    private Context mContext;

    // Constructor
    public DbConnect(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table for recipes
        db.execSQL("CREATE TABLE Recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeName TEXT NOT NULL, " +
                "ingredients TEXT, " + // Store as JSON or comma-separated list
                "instructions TEXT, " +
                "tags TEXT, " + // Store as JSON or comma-separated list
                "description TEXT, " +
                "cookTime INTEGER, " + // In minutes
                "servingSize INTEGER, " +
                "url TEXT" +
                ");");

        // Table for ingredients
        db.execSQL("CREATE TABLE Ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL, " +
                "unit TEXT, " +
                "tags TEXT" + // Store as JSON or comma-separated list
                ");");

        Toast.makeText(mContext, "Databases created successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate tables if schema changes
        db.execSQL("DROP TABLE IF EXISTS Recipes");
        db.execSQL("DROP TABLE IF EXISTS Ingredients");
        onCreate(db);
    }

    // CRUD Operations for Recipes
    public void addRecipe(String recipeName, String ingredients, String instructions, String tags, String description, int cookTime, int servingSize, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Recipes (recipeName, ingredients, instructions, tags, description, cookTime, servingSize, url) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[]{recipeName, ingredients, instructions, tags, description, cookTime, servingSize, url});
        Toast.makeText(mContext, "Recipe added successfully", Toast.LENGTH_SHORT).show();
    }

    public Cursor getRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Recipes", null);
    }

    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Recipes WHERE id = ?", new Object[]{id});
        Toast.makeText(mContext, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
    }

    // CRUD Operations for Ingredients
    public void addIngredient(String name, double quantity, String unit, String tags) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Ingredients (name, quantity, unit, tags) " +
                        "VALUES (?, ?, ?, ?)",
                new Object[]{name, quantity, unit, tags});
        Toast.makeText(mContext, "Ingredient added successfully", Toast.LENGTH_SHORT).show();
    }

    public Cursor getIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Ingredients", null);
    }

    public void updateIngredient(String name, double newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Ingredients SET quantity = ? WHERE name = ?", new Object[]{newQuantity, name});
        Toast.makeText(mContext, "Ingredient updated successfully", Toast.LENGTH_SHORT).show();
    }

    public void deleteIngredient(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Ingredients WHERE name = ?", new Object[]{name});
        Toast.makeText(mContext, "Ingredient deleted successfully", Toast.LENGTH_SHORT).show();
    }

    public Cursor searchIngredient(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Ingredients WHERE name LIKE ?", new String[]{"%" + name + "%"});
    }
}
