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

Casual workflow for _search_ingredient_:

```plantuml
@startuml

skin rose

title Search Ingredient (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Input search term (e.g., "cheese");

|Recipe Management System|
:Receive search term;
:Search ingredient list for matches;
while (Matches found?) is (no)
:Notify user that no ingredients were found;
:Lets user input new parameters;
|User|
:Inputs new parameters;
endwhile(yes)

|Recipe Management System|
:Display matching ingredients;
|User|
:See list of matching ingredients in pantry;
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

user -> ui : Enter ingredient name to search
ui -> cont : searchIngredientByName(name)
cont -> pantry : searchIngredient(name)
pantry -> cont : return matching ingredients
cont -> ui : display matching ingredients

alt No matches found
    ui -> user : Notify no ingredients found
    user -> ui : Enter new search parameters
    ui -> cont : searchIngredientByName(newName)
    cont -> pantry : searchIngredient(newName)
    pantry -> cont : return matching ingredients
    cont -> ui : display matching ingredients
end

user -> ui : See list of matching ingredients


@enduml
```