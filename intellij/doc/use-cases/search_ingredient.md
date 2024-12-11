# Search Ingredient

## 1. Primary actor and goals
* __User__: wants to find specific ingredients from pantry to use in recipes or for inventory purposes.
* __Recipe Management System__: provides functionality to search and retrieve ingredients stored in the pantry based on user input.


## 2. Other stakeholders and their goals

* __Developer__: Ensure the Recipe Management System is functioning properly and that the ingredient database is updated and maintained.


## 3. Preconditions

* The pantry must contain valid ingredients stored in the system.
* The user must have access to pantry.

## 4. Postconditions

* The system provides the user with a list of ingredients matching the search criteria.
* If no ingredients match the search criteria, the system notifies the user that no results were found.


## 5. Workflow

Fully dressed workflow for _search_ingredient_:

```plantuml
@startuml

skin rose

title Search Ingredient (fully dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Input search query (e.g., "cheese");

|Recipe Management System|
:Receive search query;
:Search for ingredients in pantry;
while (Ingredients found?) is (no)
:Notify user that no ingredients were found;
:User inputs a new query;
|User|
:Input new query;
endwhile(yes)

|Recipe Management System|
:Display found ingredients in SearchIngredientFragment;
|User|
:See list of found ingredients in pantry;
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
participant ": Pantry" as pantry
participant ": SearchIngredientFragment" as fragment

user -> ui: Click search icon
ui -> cont: onSearchIngredientClicked()
cont -> ui: displayFragment(searchIngredientFragment)
user -> ui : Enter ingredient query
ui -> cont : onSearchIngredient(String name)
cont -> pantry : String searchIngredient(String name)
cont -> fragment : displayFoundIngredients(foundIngredients)

alt No matches found
    fragment -> cont : showIngredientNotFoundError()
    cont -> ui:  showIngredientNotFoundError()
    ui -> user : Sees no found ingredients Snackbar
    user -> ui : Enter new query
    ui -> cont : onSearchIngredient(String name)
    cont -> pantry : searchIngredient(String name)
    pantry -> cont : displayFoundIngredients(foundIngredients)
    cont -> ui : displayFoundIngredients(foundIngredients)
end

ui -> user : Shows list of found ingredients
@enduml
```