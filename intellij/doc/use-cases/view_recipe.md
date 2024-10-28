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
:Execute __search_recipes__;
|Recipe Management System|
:Retrieve list of matching recipes;
:Display search results to user;

|User|
:Select a recipe to view;
|Recipe Management System|
:Retrieve recipe details;
:Display recipe details (name, ingredients, instructions);

|User|
:Decide whether to use or save the recipe;
if (save) is (yes) then
:Execute __save_recipe__;
stop
else (no)
:Can search again or stop;
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

ui -> user: Present list of relevant recipes
user -> ui: Select a recipe to view details of
ui -> cont: Communicate recipe choice
cont -> rec: recipe.printRecipeDetails()
rec -> cont: Return recipe details (name, ingredients, instructions)
cont -> ui: Display recipe details
ui -> user: Present recipe details

loop User decides to save or not
    ui -> user: Give user option to save recipe to cookbook
    user -> ui: Choose to save recipe
    alt User chooses to save
        ui -> cont: recipe.save()
        cont -> rec: recipe.save()
        rec -> cont: Recipe saved to cookbook
        cont -> ui: Recipe has been saved
        ui -> user: Tell user recipe has been saved
    else User does not save
        ui -> user: Recipe not saved, user can search again or exit
    end

@enduml
```