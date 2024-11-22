# Upload Personal Recipe

## 1. Primary actor and goals
* __User__: wants to upload a complete personal recipe, including ingredients and preparation steps.
* __Recipe Management System__: wants to understand each ingredient in the new uploaded recipe and store both the new ingredients and recipe for access by the Recipe Database.


## 2. Other stakeholders and their goals

* __Recipe Database__: wants to understand and process the newly uploaded recipe for future retrieval and display.

## 3. Preconditions

* The user identifies the personal recipe.
* The system is ready to process and understand the personal recipe.

## 4. Postconditions

* Recipe is successfully saved into a Recipe Database.


## 5. Workflow

Casual workflow for _upload personal recipe_:

```plantuml
@startuml

skin rose

title Upload Personal Recipe (fully dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select "Upload Recipe";
|Recipe Management System|
:Navigate to AddRecipeFragment;
|User|
:Enter recipe details (name, ingredients, instructions, etc.);

while (Are ingredients valid?) is (no)
  |User|
  :Error message for invalid ingredient(s);
  :Modifies ingredients;
endwhile(yes)

while (Are instructions valid?) is (no)
  |User|
  :Error message for invalid instruction(s);
  :Modifies instructions;
endwhile(yes)

|User|
:Click "Done" to submit recipe;
|Recipe Management System|
:Validate and build recipe;
:Add recipe to cookbook;
:Display RecipeDetailFragment;
:Update CookbookFragment;
stop

@enduml

```

## 6. Sequence Diagram

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller" as cont
participant ": Recipe" as recipe
participant ": RecipeDetailFragment" as recipeDetailFragment
participant ": CookbookFragment" as cookbookFragment

user -> ui : Selects "Upload Recipe"
ui -> cont : onNavigateToAddRecipe()
cont -> ui : Display AddRecipeFragment
user -> ui : Enters recipe details (name, ingredients, instructions)
user -> ui : Clicks "Done"

ui -> cont : onRecipeCreated(recipe)
cont -> recipe : Create Recipe object using RecipeBuilder
recipe -> cookbookFragment : Add recipe to cookbook
cookbookFragment --> cont : Recipe added to cookbook
cont -> ui : Display RecipeDetailFragment
ui -> recipeDetailFragment : Show Recipe details
cont -> ui : Update CookbookFragment with new recipe

@enduml
```