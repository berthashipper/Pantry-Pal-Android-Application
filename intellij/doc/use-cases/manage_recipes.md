# Manage Recipes

## 1. Primary actor and goals
* __User__: wants to upload their own recipes, generate recipe suggestions based on ingredients in their pantry, search for existing recipes, and manage the list of recipes.
* __Recipe Management System__: allows users to efficiently handle their recipes, make suggestions based on available ingredients, and manage their cookbook.


## 2. Other stakeholders and their goals

* __Developer__: Ensures the Recipe Management System functions correctly and maintains user data security.


## 3. Preconditions

* The user should have a pantry with ingredients listed to generate recipe suggestions.
* The user should have the ability to add recipes to their personal cookbook.

## 4. Postconditions

* The user successfully uploads a new recipe to the system.
* Recipe suggestions are generated based on the ingredients available in the user's pantry.
* The user can search for existing recipes and manage their personal cookbook.


## 5. Workflow

Fully dressed workflow for _manage_recipes_:

```plantuml
@startuml

skin rose

title Manage Recipes (fully dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select option (Upload Recipe, Generate Suggestions, Search Recipes, Manage Cookbook);
|Recipe Management System|
:Receive user action;

if (Upload Recipe) then
|User|
:Input recipe information;
|Recipe Management System|
:Save recipe to user's cookbook;
stop

elseif (Generate Recipe)
|Recipe Management System|
:Execute __generate_recipe__;
stop

elseif (Search Recipe)
:Execute __search_recipes__;
stop

elseif (Manage Cookbook)
:Execute __manage_cookbook__;
stop

else
stop
@enduml

```

## 6. Sequence Diagram

```plantuml
@startuml
skin rose

actor User as user
participant ": UI" as ui
participant ": Controller" as cont
participant ": Pantry" as pantry
participant ": Recipe" as recipe
participant ": Cookbook" as cookbook
participant ": GenerateRecipe" as genRecipe
participant ": RecipeFragment" as recipeFragment
participant ": AddRecipeFragment" as addRecipeFragment

user -> ui : Selects "View Cookbook"
ui -> cont : onViewCookbookMenu()
cont -> cookbook : CookbookFragment.newInstance()
cookbook --> ui : Display recipes

user -> ui : Selects "Upload Recipe"
ui -> cont : onNavigateToAddRecipe()
cont -> addRecipeFragment : AddRecipeFragment.newInstance()
addRecipeFragment --> ui : Show Add Recipe Form

user -> ui : Inputs recipe details
ui -> cont : onRecipeCreated(name, ingredients, instructions)
cont -> recipe : Create new Recipe object
recipe -> cookbook : Add recipe to cookbook
cookbook --> cont : Confirmation
cont --> ui : Show "Recipe uploaded successfully"

user -> ui : Selects "Search Recipe"
ui -> cont : onSearchRecipe(query)
cont -> cookbook : Filter recipes by query
cookbook --> cont : Return filtered recipes
cont --> ui : Display filtered recipes

@enduml
````