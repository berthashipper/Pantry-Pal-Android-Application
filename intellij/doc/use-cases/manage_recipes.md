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
participant ": RecipeDetailFragment" as rdf
participant ": CookbookFragment" as cf
participant ": AddRecipeFragment" as arf

user -> ui : Selects "View Cookbook"
ui -> cont : onViewCookCookbookMenuClicked()
cont -> cf: onViewCookbookMenu()
cont -> ui: displayFragment(CookbookFragment)
ui -> user: Shows recipe list

user -> ui : Selects add Recipe button
ui -> cont: navigateToAddRecipe()
cont -> cf: navigateToAddRecipe()
cont -> arf : AddRecipeFragment.newInstance()
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
````

```plantuml
@startuml
skin rose

actor User as user
participant ": UI" as ui
participant ": Controller" as cont
participant ": CookbookFragment" as cf
participant ": SearchRecipeFragment" as srf

user -> ui : Selects "View Cookbook"
ui -> cont : onViewCookCookbookMenuClicked()
cont -> cf: onViewCookbookMenu()
cont -> ui: displayFragment(CookbookFragment)
ui -> user: Shows recipe list

user -> ui : Click "Search Recipe" Button
ui -> cont: onSearchRecipesMenu()
cont -> cf: onSearchRecipesMenu()
cont -> ui: displayFragment(SearchRecipeFragment)
ui -> user: Shows Search Recipe Fragment

user -> ui : Input Recipe Name
user -> ui : Clicks "Search" Button
ui -> cont : onSearchRecipe(query)
cont -> srf : onSearchRecipe(String query)
srf ->  cont : displayFoundRecipes(foundRecipes)
cont -> ui : displayFragment(RecipeFragment)
ui -> user: Shows Search Result

user -> ui : Selects "Done Search"
ui -> cont : onSearchDone()
cont -> srf : onSearchDone()
srf -> cont : onBackToCookbookClicked() 
cont -> ui : displayFragment(CookbookFragment)
ui -> user : Display the Cookbook

@enduml
````
