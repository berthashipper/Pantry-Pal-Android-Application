import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Edamam {
    private static final String APP_ID = "f44b99f5";
    private static final String APP_KEY = "92573b2fde792e7e2025da84549c0b95";
    private static final String API_URL = "https://api.edamam.com/search"; //not accessible for now

    public static void main(String[] args) {
        try {
            // Define your query
            String query = "chicken";
            String url = API_URL + "?q=" + query + "&app_id=" + APP_ID + "&app_key=" + APP_KEY;

            // Set up HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if response status is successful (200 OK)
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Print the raw response for debugging
                System.out.println("Response Body: " + responseBody);

                // Parse JSON response
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode rootNode = mapper.readTree(responseBody);

                    // Check if 'hits' is present and is an array
                    if (rootNode.has("hits") && rootNode.get("hits").isArray()) {
                        JsonNode recipes = rootNode.path("hits");
                        if (recipes.isEmpty()) {
                            System.out.println("No recipes found for the given query.");
                        } else {
                            recipes.forEach(hit -> {
                                System.out.println("Recipe Name: " + hit.path("recipe").path("label").asText());
                                System.out.println("Source: " + hit.path("recipe").path("source").asText());
                                System.out.println("URL: " + hit.path("recipe").path("url").asText());
                                System.out.println("Calories: " + hit.path("recipe").path("calories").asDouble());
                                System.out.println("------");
                            });
                        }
                    } else {
                        System.out.println("Unexpected JSON structure: 'hits' field is missing or not an array.");
                    }
                } catch (JsonMappingException e) {
                    System.err.println("Failed to parse JSON: " + e.getMessage());
                }
            } else {
                System.out.println("Failed to fetch data. Status code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());  // Print response body for debugging
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
