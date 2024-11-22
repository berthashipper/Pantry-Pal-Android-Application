# Manage Cookbook

## 1. Primary actor and goals
* __User__: wants to save recipes to cookbook and view recipes in their cookbook.
* __Recipe Management System__: provides functionalities for users to manage their recipes effectively, including saving new recipe and viewing full list.


## 2. Other stakeholders and their goals

* __Developer__: Ensures that the Recipe Management System runs smoothly and that user data is secure and properly maintained.



## 3. Preconditions

* The user should have an existing cookbook or the ability to create one.
* The system has access to recipes.

## 4. Postconditions

* The user successfully adds recipes to their cookbook and can view all of them.
* The updated list of recipes is displayed to the user after any modifications.



## 5. Workflow

Fully-dressed workflow for _manage_cookbook_:

```plantuml
@startuml

skin rose

title Manage Cookbook (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Open Cookbook;

:Choose an action: "View Saved Recipes" or "Upload New Recipe";
|Recipe Management System|
:Receive user action;

if (View saved recipes) then
    :Call controller to fetch and display list of saved recipes;
    |User|
    :Select a recipe to view details;
    |Recipe Management System|
    :Call controller to retrieve recipe details;
    :Display recipe details (name, ingredients, instructions);
else (Upload new recipe)
    :Call controller to present option to upload new recipe;
    |User|
    :Upload new recipe details (name, ingredients, instructions);
    |Recipe Management System|
    :Call controller to save recipe to cookbook;
    :Display confirmation of save action;
endif

|User|
:Return to cookbook menu or exit;

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
participant ": RecipeAdapter" as ra
participant ": RecipeDetailFragment" as rdf
participant ": AddRecipeFragment" as arf

user -> ui: Open cookbook
ui -> cf: Display CookbookFragment
cf -> cont: onViewCookbookMenu()
cont -> cf: CookbookFragment.newInstance()
cf -> ui: Display recipe list
cf -> ra: Set up RecyclerView with recipes
ra -> ui: Display list of saved recipes
ui -> user: Show saved recipes

user -> ui: Select recipe to view details
ui -> cont: onRecipeClick(recipe)
cont -> rdf: Display RecipeDetailFragment
rdf -> ui: Show recipe details (name, ingredients, instructions)
ui -> user: Show recipe details

user -> ui: Add new recipe
ui -> cont: onNavigateToAddRecipe()
cont -> arf: Display AddRecipeFragment

user -> ui: Enter new recipe details
ui -> cont: onRecipeCreated(newRecipe)
cont -> cf: Update recipes list with new recipe
cf -> ui: Display updated CookbookFragment with new recipe
ui -> user: Show confirmation of saved recipe

@enduml

```
