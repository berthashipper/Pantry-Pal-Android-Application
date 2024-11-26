/*
package com.example.pantrypalandroidprototype.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    private static final String APP_ID = "3aba7c72";
    private static final String APP_KEY = "9c740f924d4308a0ac174b24a4b1fc86";
    private static final String API_URL = "https://api.edamam.com/api/recipes/v2";

    public List<Recipe> fetchRecipes(String query) throws IOException {
        String urlString = API_URL + "?q=" + query + "&app_id=" + APP_ID + "&app_key=" + APP_KEY;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }

            bufferedReader.close();
            return parseRecipes(responseBuilder.toString());
        } else {
            throw new IOException("Failed to fetch recipes. HTTP response code: " + responseCode);
        }
    }

    private List<Recipe> parseRecipes(String jsonResponse) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray hitsArray = jsonObject.getJSONArray("hits");

            for (int i = 0; i < hitsArray.length(); i++) {
                JSONObject recipeObject = hitsArray.getJSONObject(i).getJSONObject("recipe");

                String name = recipeObject.getString("label");
                String imageUrl = recipeObject.getString("image");
                String url = recipeObject.getString("url");

                // Parse other details as needed
                Recipe recipe = new Recipe(name, imageUrl, url);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}*/
