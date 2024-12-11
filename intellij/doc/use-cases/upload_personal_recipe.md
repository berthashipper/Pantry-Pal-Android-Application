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
participant ": AddRecipeFragment" as arf
participant ": CookbookFragment" as cf
participant ": RecipeDetailFragment" as rdf

user -> ui : Selects "View Cookbook"
ui -> cont : onViewCookCookbookMenuClicked()
cont -> cf: onViewCookbookMenu()
cont -> ui: displayFragment(CookbookFragment)
ui -> user: Shows recipe list

user -> ui : Selects add Recipe button
ui -> cont: navigateToAddRecipe()
cont -> cf: navigateToAddRecipe()
cont -> ui: displayFragment(AddRecipeFragment)
ui -> user: Shows Add Recipe Fragment

user -> ui : Input Recipe Details(name, description, cooktime, servingsize, ingredients, instructions, tags)
user -> ui : Clicks "Done" Button
ui -> cont : onDoneButtonClicked()
cont -> arf : onDoneButtonClicked()
cont -> cf: updateCookbookFragment()
cont -> rdf: onRecipeCreated(recipe)
cont -> ui : displayFragment(RecipeDetailFragment)
ui -> user: Shows the newly Added Recipe Details

@enduml
```