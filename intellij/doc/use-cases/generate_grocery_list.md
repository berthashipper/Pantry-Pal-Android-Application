# Generate Grocery List

## 1. Primary actor and goals
* __User__: should be able to select recipes they want to make in a week and recieve information about ingredients they still need in order to make those dishes..
* __Recipe Management System__: parses ingredients from recipes the user has selected and compares them to a list of ingredients the user already has in their pantry.


## 2. Other stakeholders and their goals

* __Recipe Database__: should be holding a comprehensive list of recipes that the user can select from.


## 3. Preconditions

* The recipe system understands every ingredient in every recipe in the Recipe Database.
* The recipe system can compare a list of ingredients in recipes selected against ingredients owned (with quantities considered).

## 4. Postconditions

* The recipe management system has provided a list of ingredients to the user the recipes based on additional filters supplied by user.


## 5. Workflow

Fully-dressed workflow for _generate_grocery_list_:

```plantuml
@startuml

skin rose

title Generate grocery list (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:__Searches recipes__;
:Select recipes for the system to add
missing ingredients from to grocery list;

|Recipe Management System|
:Parses ingredients from recipes;
:Forms a list of all ingredients from all the recipes;

:Compares list to ingredients in the User's Pantry;

:Finds which ingredients are missing
and how much of each is needed;

:Presents list of ingredients to user;

|User|
:Confirms ingredients to add to grocery list;

|Recipe Management System|
:Adds ingredients into pantry, indicated as pending;

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
participant ": Recipe" as rec
participant ": Pantry" as pantry

ui -> user: Displays search/generate recipe button
user -> ui: Searches/generates recipes
user -> ui: Selects multiple recipes to add to grocery list

loop for each selected recipe
    ui -> cont: Get ingredients from selected recipe
    cont -> rec: recipe.getIngredients()
    rec -> cont: Returns ingredient list
    
    'Loop for each ingredient in the recipe
    loop for each ingredient
        cont -> pantry: Check if ingredient is in pantry
        alt ingredient not found or insufficient
            rec -> cont: Add ingredient to grocery list
        end
    end
end

cont -> ui: Display completed grocery list
ui -> user: Shows final grocery list with missing ingredients


@enduml
```


