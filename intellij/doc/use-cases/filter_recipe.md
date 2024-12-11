# Filter Recipe

## 1. Primary actor and goals
* __User__: wants to receive accurate and appealing recipe that aligns with their specifications like allergies.
* __Recipe Database__: should be holding a comprehensive list of recipes.
* __Recipe Management System__:  filters through the recipe database based on the tags and restrictions from users presents fitting ones to the user.


## 2. Other stakeholders and their goals

* __User__: Wants the recipe system and database to interact efficiently so that they get their recipe suggestions in a timely manner.



## 3. Preconditions
* The user has a recipe to filter
* The recipe management system has understood, processed, and stored all the filters.
* The recipe database can pull the recipes stored in the database.

## 4. Postconditions

* filters and restrictions are identified and matched to all recipes in the database.
* The recipe management system has filtered the recipes based on additional filters supplied by user.


## 5. Workflow

Fully-dressed workflow for _filter recipe_:

```plantuml
@startuml

skin rose

title Filter Recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Opens Cookbook and clicks filter button;
:Selects tag to filter recipes by;

|Recipe Management System|
:Checks all recipes to see if they contain the selected tag;
:Produces a final list of recipes that satisfy the given filter;
:Presents list of filtered recipes to user;
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
participant "CookbookFragment" as cookbook
participant "FilterRecipeFragment" as filterFrag

user -> ui : Select filtering option
ui -> cont : onFilterRecipesMenu()
cont -> cookbook : getAllUniqueTags()
cookbook --> cont : List<String> uniqueTags
cont -> filterFrag : newInstance(this, uniqueTags)
cont -> ui : Display FilterRecipeFragment

user -> ui : Select dietary tag
ui -> cont : onApplyFiltersClicked()
filterFrag -> cont : onFilterRecipes(String dietaryTag)
cont -> cookbook : filterRecipes(String dietaryTag)
cookbook --> cont : Map<String, Recipe> filteredRecipes
alt Recipes found
    cont -> ui : Display RecipeFragment
    ui -> user : Show list of matching recipes
else No recipes found
    cont -> ui : showNoRecipesFoundError()
    ui -> user : Show error Snackbar
end
@enduml
```


