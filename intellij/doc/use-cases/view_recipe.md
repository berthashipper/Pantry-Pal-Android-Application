# View Recipe

## 1. Primary actor and goals
* __User__: wants to view the details of a specific recipe from search results in the Recipe Management System to decide whether to use or save it.
* __Recipe Management System__: retrieves and displays the details of a selected recipe based on the user's search.


## 2. Other stakeholders and their goals

* __Developer__: Ensures the system is secure and that recipe data is accurately retrieved and displayed.


## 3. Preconditions

* User must have performed a search that yields at least one recipe.

## 4. Postconditions

* User successfully views the details of the selected recipe from search results.
* User can decide whether to use the recipe or save it for future reference.


## 5. Workflow

Fully-dressed workflow for _view_recipe_:

```plantuml
@startuml

skin rose

title View Recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select option (View Cookbook);
|Recipe Management System|
:Display list of recipes;

|User|
:Select a recipe to view;
|Recipe Management System|
:Navigate to RecipeDetailFragment;
:Display recipe details (name, ingredients, instructions);

|User|
:Decide whether to go back to Cookbook;
:Navigate back to CookbookFragment;
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
participant ": CookbookFragment" as cf
participant ": RecipeDetailFragment" as rdf

user -> ui : Selects "View Cookbook"
ui -> cont : onViewCookCookbookMenuClicked()
cont -> cf: onViewCookbookMenu()
cont -> ui: displayFragment(CookbookFragment)
ui -> user: Shows recipe list

user -> ui : Selects a recipe to view
ui -> cont: onRecipeClick(recipe)
cont -> rdf: onRecipeClick(recipe)
cont -> ui: displayFragment(recipeDetailFragment)
ui -> user: Shows recipe details

user -> ui : Selects "Done" to return to Cookbook
ui -> cont : onDoneViewingRecipe()
cont -> ui: displayFragment(cookbookFragment)
ui -> user: Shows Cookbookdetails

@enduml
```