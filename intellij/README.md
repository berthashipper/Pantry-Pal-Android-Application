# Pantry Pal Prototype

## Overview
This is a prototype Java application for managing pantry ingredients. The application features a text-based interface and follows the Model-View-Controller (MVC) architectural design pattern, allowing users to easily manage their pantry items.

## Functionality
The implemented functionalities of prototype include:

- **Add Ingredient**: Users can add new ingredients to their pantry by providing the ingredient name, quantity, unit of measurement (e.g., grams, loaves), and any dietary tags (e.g., vegan, gluten-free). If no dietary tags are specified, an empty set is created by default.

- **Delete Ingredient**: Users can remove ingredients from their pantry by name. If the specified ingredient is found, it is deleted, and a confirmation message is displayed. If the ingredient is not found, an error message is shown.

- **Edit Ingredient Quantity**: Users can modify the quantity of an existing ingredient in their pantry. The user specifies the ingredient name and the new quantity. If the ingredient exists, its quantity is updated, and a confirmation message is displayed. If the ingredient is not found, an error message is shown.

- **Filter Ingredients by Dietary Tag**: Users can filter the list of ingredients based on specific dietary tags. This allows users to quickly find ingredients that meet their dietary restrictions or preferences. The filtered list is returned to the user.

- **Search for Ingredients**: Users can search for ingredients in the pantry using a substring of the ingredient name. This feature allows users to quickly locate ingredients, even if they are unsure of the exact name. If matching ingredients are found, they are displayed; otherwise, an appropriate message is shown.

- **Temporary Grocery List**: Users can manage a grocery list that stores ingredients they need to purchase. Users can add ingredients from their pantry to the grocery list, specifying the desired quantity. This functionality is helpful for meal planning and shopping.

- **Print Grocery List**: Users can view the contents of their grocery list. If the grocery list is empty, a message is displayed; otherwise, the list of ingredients and with their quantities is printed.

- **View Pantry Contents**: Users can view all ingredients currently stored in the pantry, along with their quantities, units of measurement, and any associated dietary tags. This provides an overview of what is available for meal preparation.

## Limitations and Simplifying Assumptions
- **Input Validation**: The application assumes that user inputs (especially for quantities) are valid and does not perform extensive validation. For example, it does not check for negative quantities.
- **Dietary Tags**: The dietary tags feature is defined but not fully implemented in the user interface. Users cannot currently specify dietary tags when adding ingredients since our vision for this functionality is to have a pre-defined list of ingredients for users to select from.
- **Grocery List Functionality**: The grocery list feature is only partially implemented. Users can add ingredients to the grocery list and print it, but functionalities such as removing items, saving the grocery list, and incorporating it with other elements are not yet available.
- **Persistence**: The application does not persist data; all pantry contents are lost when the application is closed.

## How to Run the Prototype

1. **Clone the Repository**: Clone this repository to your local machine or download the ZIP file.

2. **Navigate to the Source Directory**: Open a terminal and navigate to the `src` directory where the Java files are located.

3. **Compile the Java Files**: Run the following command to compile the Java files:
   ```bash
   javac *.java
4. **Run the Application**: Execute the main application using the command:
   ```bash
   java Main