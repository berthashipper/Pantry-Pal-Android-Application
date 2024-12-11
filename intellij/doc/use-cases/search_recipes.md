# Search Recipes

## 1. Primary actor and goals
* __User__: wants to search accurate recipe that aligns with their specifications.
* __Recipe Management System__:  Should be holding a comprehensive list of recipes. Filters through the recipe database based on the recipes that the Recipe Database has stored and presents fitting ones to the user.


## 2. Other stakeholders and their goals

* __User__: Wants the recipe system and database to interact efficiently so that they get their recipe suggestions in a timely manner.



## 3. Preconditions

* The recipe system has understood, processed the recipe
* The recipe database can pull the recipes stored in the database.

## 4. Postconditions

* The recipe management system has filtered the recipes and presented them to user.


## 5. Workflow

Fully-dressed workflow for _search recipe_:

```plantuml
@startuml

skin rose

title Search Recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select "Search Recipe by Name";
:Enter recipe name;
|Recipe Management System|
:Receive recipe name input;

:Search for recipes that match input;

if (Match found?) then
    :Return list of matching recipes;
    :Display recipe details to User;
    |User|
    :View search results or retry;
    stop
else (No match)
    :Notify User "Recipe not found";
    |User|
    :Retry search or exit;
    stop
end

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

user -> ui : Click search icon in cookbook
ui -> user : prompt "Enter recipe name"
user -> ui : Enters recipe name
ui -> cont : searchRecipeByName(String name)

cont -> CookbookFragment : onSearchRecipe(query)
CookbookFragment -> cont : displayFoundRecipes(foundRecipes)
cont -> ui : displayFragment(recipeFragment)
ui -> user : Shows search results
@enduml
```
