//import android.content.Context;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class Edamam {
//    private static final String APP_ID = "f44b99f5";
//    private static final String APP_KEY = "92573b2fde792e7e2025da84549c0b95";
//    private static final String API_URL = "https://api.edamam.com/search"; // API endpoint
//
//    private DbConnect dbConnect;
//
//    // Constructor to initialize database connection
//    public Edamam(Context context) {
//        this.dbConnect = new DbConnect(context);
//    }
//
//    public void fetchAndStoreRecipes(String query) {
//        try {
//            // Build API request URL
//            String url = API_URL + "?q=" + query + "&app_id=" + APP_ID + "&app_key=" + APP_KEY;
//
//            // Set up HTTP client and request
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .GET()
//                    .build();
//
//            // Send the request and get the response
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Check if response status is successful (200 OK)
//            if (response.statusCode() == 200) {
//                String responseBody = response.body();
//
//                // Parse JSON response
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode rootNode = mapper.readTree(responseBody);
//
//                // Check if 'hits' field is present and is an array
//                if (rootNode.has("hits") && rootNode.get("hits").isArray()) {
//                    JsonNode recipes = rootNode.path("hits");
//
//                    // Loop through each recipe in the response
//                    recipes.forEach(hit -> {
//                        JsonNode recipeNode = hit.path("recipe");
//
//                        // Extract recipe details
//                        String recipeName = recipeNode.path("label").asText();
//                        String ingredients = recipeNode.path("ingredientLines").toString(); // As JSON array
//                        String instructions = "Check the source URL for instructions."; // Placeholder
//                        String tags = recipeNode.path("dietLabels").toString(); // As JSON array
//                        String description = recipeNode.path("source").asText();
//                        int cookTime = recipeNode.path("totalTime").asInt(0); // Total time in minutes
//                        int servingSize = recipeNode.path("yield").asInt(1); // Default yield to 1
//                        String url = recipeNode.path("url").asText();
//
//                        // Store recipe in the database
//                        dbConnect.addRecipe(recipeName, ingredients, instructions, tags, description, cookTime, servingSize, url);
//
//                        // Extract and store individual ingredients
//                        recipeNode.path("ingredients").forEach(ingredientNode -> {
//                            String name = ingredientNode.path("text").asText();
//                            double quantity = ingredientNode.path("quantity").asDouble(0); // Default to 0
//                            String unit = ingredientNode.path("measure").asText("unit"); // Default unit
//                            String ingredientTags = ""; // Optional: You can derive tags if needed
//
//                            dbConnect.addIngredient(name, quantity, unit, ingredientTags);
//                        });
//                    });
//                } else {
//                    System.out.println("Unexpected JSON structure: 'hits' field is missing or not an array.");
//                }
//            } else {
//                System.out.println("Failed to fetch data. Status code: " + response.statusCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
