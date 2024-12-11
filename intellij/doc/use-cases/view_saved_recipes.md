# View Saved Recipe

## 1. Primary actor and goals
* __User__: wants to view the details of a specific recipe from their saved recipes within the Recipe Management System.
* __Recipe Management System__: retrieves and displays the details of a selected recipe to the user.


## 2. Other stakeholders and their goals

* __Developer__: Ensures the system is secure and that recipe data is accurately retrieved and displayed.


## 3. Preconditions

* User must have at least one recipe saved in their cookbook.

## 4. Postconditions

* User successfully views the details of the selected recipe, including ingredients and instructions.


## 5. Workflow

Fully-dressed workflow for _view_saved_recipe_:

```plantuml
@startuml

skin rose

title View Saved Recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:View cookbook;
:Select a recipe from cookbook or search to view details of;
|Recipe Management System|
:Retrieve details of the selected recipe;
:Display recipe details (name, ingredients, instructions);

|User|
:View recipe details;

if (User wants to view another recipe?)
    ->yes;
    |Recipe Management System|333
    :Navigate back to saved recipes list;
    :Select another recipe to view details;
    |Recipe Management System|
    :Retrieve and display details of the new recipe;
    |User|
    :View recipe details;
    stop
endif
->no;

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
participant ": RecipeDetailFragment" as rdf

user -> ui: Open saved recipes (cookbook)
ui -> user: Display list of saved recipes
loop User wants to view another recipe
    user -> ui: Select a recipe to view details
    ui -> cont: onRecipeClick(Recipe recipe)
    cont -> rdf: onRecipeClick(Recipe recipe)
    cont -> ui: displayFragment(recipeDetailFragment)
    ui -> user: Present recipe details
    user -> ui: Return to saved recipes list
end

@enduml
```