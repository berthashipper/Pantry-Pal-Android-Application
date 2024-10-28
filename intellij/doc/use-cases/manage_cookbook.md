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
    :Retrieve and display list of saved recipes;
    |User|
    :Select a recipe to view details;
    |Recipe Management System|
    :Retrieve recipe details;
    :Display recipe details (name, ingredients, instructions);
else (Upload new recipe)
    :Present option to upload new recipe;
    |User|
    :Upload new recipe details (name, ingredients, instructions);
    |Recipe Management System|
    :Save recipe to cookbook;
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
participant ": Recipe" as rec

user -> ui: Open cookbook
ui -> user: Display cookbook menu options ("View Saved Recipes" or "Upload New Recipe")
user -> ui: Choose an action

alt View Saved Recipes
    ui -> cont: Request saved recipes list
    cont -> rec: Retrieve saved recipes
    rec -> cont: Return list of saved recipes
    cont -> ui: Display list of saved recipes
    ui -> user: Show saved recipes

    user -> ui: Select recipe to view details
    ui -> cont: Request recipe details
    cont -> rec: Fetch recipe details (name, ingredients, instructions)
    rec -> cont: Return recipe details
    cont -> ui: Display recipe details
    ui -> user: Show recipe details

else Upload New Recipe
    ui -> user: Prompt user to enter new recipe details
    user -> ui: Enter new recipe details (name, ingredients, instructions)
    ui -> cont: Send new recipe data
    cont -> rec: Save new recipe to cookbook
    rec -> cont: Confirm save action
    cont -> ui: Display confirmation message
    ui -> user: Show confirmation of saved recipe
end

@enduml
```
