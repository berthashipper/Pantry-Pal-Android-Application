# Test Report

## Overview
This report summarizes the testing performed on the prototype, focusing on the Pantry functionality. The tests included adding ingredients, filtering by dietary tags, searching for ingredients, and managing the grocery list. All tests were conducted using the text-based interface of the application.

## Test Cases

### Add Ingredients
**Input:**
- Add "apple" with quantity 10, unit "pieces", tags [VEGAN, KOSHER]
- Add "banana" with quantity 5, unit "pieces", tags [VEGAN]
- Add "bread" with quantity 2, unit "loaves", tags [GLUTEN_FREE, VEGAN]
- Add "mozzarella cheese" with quantity 1, unit "pack", no tags
- Add "cheddar cheese" with quantity 11, unit "slices", no tags

**Expectation:** All ingredients are added successfully, and corresponding confirmation messages are displayed.

**Output:**


### Display Pantry Contents
**Input:** Call `System.out.println(pantry);`

**Expectation:** The pantry displays all ingredients with correct quantities and tags.

**Output:**

### Filter Ingredients by Dietary Tag
**Input:** Filter by `Ingredient.dietary_tags.VEGAN`.

**Expectation:** Only vegan ingredients are displayed.

**Output:**

### Search for an Ingredient
**Input:** Search for "banana".

**Expectation:** The search returns the correct ingredient.

**Output:**

### Search for Ingredients with Substring
**Input:** Search for "cheese".

**Expectation:** All ingredients containing "cheese" are returned.

**Output:**

### Search for Non-Existing Ingredient
**Input:** Search for "milk".

**Expectation:** The system indicates that the ingredient is not found.

**Output:**

### Add to Grocery List
**Input:** Add "bread" with quantity 4 to the grocery list.

**Expectation:** The ingredient is successfully added to the grocery list.

**Output:**

### View Grocery List
**Input:** Call `pantry.printGroceryList();`

**Expectation:** The grocery list displays all added ingredients.

**Output:**


## Conclusion
All tests were executed successfully, and the outputs matched the expected results. The prototype demonstrates the core functionalities of the application, allowing users to manage their pantry effectively.