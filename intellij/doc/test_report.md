# Test Report

## Overview
Summarizes the testing performed on the prototype, focusing on the Pantry and Recipe functionalities. The tests included adding ingredients, filtering by dietary tags, searching for ingredients, managing the grocery list, and generating recipes based on pantry contents. All tests were conducted using the text-based interface of the application.
## Test Cases

### 1. Add Ingredients
**Input:**
- Add "White Bread" with quantity 2, unit "slices", tags [VEGAN, VEGETARIAN].
- Add "Butter" with quantity 1, unit "tablespoon", tags [VEGETARIAN, NUT_FREE].
- Add "Cheddar Cheese" with quantity 1, unit "slice", tags [VEGETARIAN, NUT_FREE].
- Add "Mozzarella Cheese" with quantity 1, unit "pack", tags [VEGETARIAN, NUT_FREE].
- Add "Apple" with quantity 3, unit "apples", tags [VEGAN, VEGETARIAN, NUT_FREE].

**Expectation:** All ingredients are added successfully, and corresponding confirmation messages are displayed.

**Output:**
- `Added Butter to pantry.`
  `Added White Bread to pantry.`
  `Added Cheddar Cheese to pantry.`
  `Added Mozzarella Cheese to pantry.`
  `Added Apple to pantry.`

### 2. Edit Ingredient
**Input:** Edit quantity of "Apple" to 10.

**Expectation:** The quantity of "Apple" is updated successfully.

**Output:**
- `Updated Apple to 10 apples`

### 3. Delete Ingredient
**Input:** Delete "Apple" from the pantry.

**Expectation:** The ingredient is removed from the pantry successfully.

**Output:**
- `Deleted Apple from pantry.`


### 4. Display Pantry Contents
**Input:** Call `System.out.println(pantry);`

**Expectation:** The pantry displays all ingredients with correct quantities and tags after edits.

**Output:**
- `Pantry contents:
  Butter: 1 tablespoons, Tags: [NUT_FREE, VEGETARIAN]
  Mozzarella Cheese: 1 pack, Tags: [NUT_FREE, VEGETARIAN]
  Cheddar Cheese: 1 slice, Tags: [NUT_FREE, VEGETARIAN]
  White Bread: 2 slices, Tags: [VEGAN, VEGETARIAN]`

### 5. Filter Ingredients by Dietary Tag
**Input:** Filter by `Ingredient.dietary_tags.NUT_FREE`.

**Expectation:** Only nut-free ingredients are displayed.

**Output:**
- `Nut-free Ingredients in Pantry:
  Ingredient: Butter, Quantity: 1 tablespoons, Tags: [NUT_FREE, VEGETARIAN]
  Ingredient: Mozzarella Cheese, Quantity: 1 pack, Tags: [NUT_FREE, VEGETARIAN]
  Ingredient: Cheddar Cheese, Quantity: 1 slice, Tags: [NUT_FREE, VEGETARIAN]`

### 6. Search for an Ingredient
**Input:** Search for "Butter".

**Expectation:** The search returns the correct ingredient.

**Output:**
- `Searching for 'Butter':
  Ingredient: Butter, Quantity: 1 tablespoons, Tags: [NUT_FREE, VEGETARIAN]`

### 7. Search for Ingredients with Substring
**Input:** Search for "cheese".

**Expectation:** All ingredients containing "cheese" are returned.

**Output:**
- `Searching for 'cheese':
  Ingredient: Mozzarella Cheese, Quantity: 1 pack, Tags: [NUT_FREE, VEGETARIAN]
  Ingredient: Cheddar Cheese, Quantity: 1 slice, Tags: [NUT_FREE, VEGETARIAN]`

### 8. Search for Non-Existing Ingredient
**Input:** Search for "milk".

**Expectation:** The system indicates that the ingredient is not found.

**Output:**
- `Searching for 'milk':
  Ingredient milk not found in the pantry.`

### 9. Add to Grocery List
**Input:** Add "White Bread" with quantity 10 to the grocery list.

**Expectation:** The ingredient is successfully added to the grocery list.

**Output:**
- `Added 10 slices of White Bread to the grocery list.`

### 10. View Grocery List
**Input:** Call `pantry.printGroceryList();`

**Expectation:** The grocery list displays all added ingredients.

**Output:**
- `Grocery List:
  White Bread: 10 slices`

### 11. Use RecipeBuilder to Create Recipes
**Input:** Use `RecipeBuilder` to create a `Grilled Cheese Sandwich` and a `Vegetable Stir Fry`.

**Expectation:** Recipes are created successfully with correct details.

**Output:**
- Printed recipe details for `Grilled Cheese Sandwich`:

`Recipe: Grilled Cheese Sandwich`

  `Description: A classic grilled cheese sandwich with crispy golden bread and melted cheddar cheese.`
  
  `Cook Time: 00:10 minutes`
  
  `Serves: 1`


  `Ingredients:`

  `- 1 slice of Cheddar Cheese`

  `- 1 tablespoons of Butter`

  `- 2 slices of White Bread`

  `Instructions:`
  
  `1. Heat a skillet over medium heat.`

  `2. Butter 2 slices of bread and place 1 slice in the skillet, butter side down.`

  `3. Add 1 slice of cheddar cheese, then top with the second slice of bread, butter side up.`

  `4. Cook until golden brown and flip to cook the other side.`
`
### 12. Generate Recipes Based on Pantry
**Input:** Generate recipes using `Generate_Recipe` with current pantry contents.

**Expectation:** The system generates and displays recipes that can be made with available ingredients.

**Output:**
- `Matched Recipes:`

  `1. Grilled Cheese Sandwich`


## Conclusion
All tests were executed successfully, and the outputs matched the expected results. The prototype demonstrates the core functionalities of the application, allowing users to manage their pantry effectively.