# Pantry Pal Prototype

## Overview
This prototype allows users to manage a pantry and generate recipes based on the available ingredients. The system includes classes for managing ingredients, recipes, and pantry operations.


## Functionality
Implemented functionalities of prototype:

### Pantry Management
- **View Pantry Contents**: Users can view all ingredients currently stored in the pantry, along with their quantities, units of measurement, and any associated dietary tags. This provides an overview of what is available for meal preparation.

- **Add Ingredient**: Users can add new ingredients to their pantry by providing the ingredient name, quantity, unit of measurement (e.g., grams, loaves), and any dietary tags (e.g., vegan, gluten-free).

- **Delete Ingredient**: Users can remove ingredients from their pantry by name. If the specified ingredient is found, it is deleted, and a confirmation message is displayed. If the ingredient is not found, an error message is shown.

- **Edit Ingredient Quantity**: Users can modify the quantity of an existing ingredient in their pantry. The user specifies the ingredient name and the new quantity. If the ingredient exists, its quantity is updated, and a confirmation message is displayed. If the ingredient is not found, an error message is shown.

- **Filter Ingredients by Dietary Tag**: Users can filter the list of ingredients in their pantry based on specific dietary tags. This allows users to quickly find ingredients that meet their dietary restrictions or preferences. The filtered list is returned to the user.

- **Search for Ingredients**: Users can search for ingredients in the pantry using a substring of the ingredient name. This feature allows users to quickly locate ingredients, even if they are unsure of the exact name. If matching ingredients are found, they are displayed; otherwise, an appropriate message is shown.


### Recipe Management
- **View Cookbook**: Users can view a list of all the recipes (and their details) that have been pre-loaded by us and any ones they upload.
- **Search Recipe**: Similarly to searching for an ingredient, users can search for a recipe in their Cookbook by full name or substring.
- **Generate Recipe Suggestions**: Users can generate recipe suggestions based on the ingredients (and quantities of them) currently available in their pantry. This feature allows users to make the most of what they have.
- **Create Recipe**: Users can upload their own personal recipes to their Cookbook by providing required information.

## Limitations and Simplifying Assumptions
- **Input Validation**: The application assumes that user inputs (especially for ingredient names and units) are valid and does not check against a pre-validated list of ingredients or units.
- **Grocery List Functionality**: This functionality only exists in teh Main class testing as of now, not implemented in tthe prototype.
- **Viewing Recipes**: When users view their cookbook they see the recipe details, but we haven't implemented the methods yet for users to be able to select a recipe to view the details of upon search.
- **Recipe Volume**: Currently we just have 2 recipes uploaded for the purpose of testing and prototype functionality. Working on getting a database to parse from.


## How to Run the Prototype

1. **Clone the Repository**: Clone this repository to your local machine or download the ZIP file.

2. **Navigate to the Source Directory**: Open a terminal and navigate to the `src` directory where the Java files are located.

3. **Compile the Java Files**: Run the following command to compile the Java files:
   ```bash
   javac *.java
4. **Run the Application**: Execute the main application using the following command. Tests exist in the Main class, as well as the method call to instantiate and start the UI.
   ```bash
   java Main
   