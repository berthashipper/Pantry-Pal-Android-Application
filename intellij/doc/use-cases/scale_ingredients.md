# Scale Ingredients

## 1. Primary actor and goals
* __User__: wants to easily get clear information on the amount of ingredients they'll need without doing calculation.
* __Recipe Management System__: wants to calculate ingredient amounts and scale them comprehensively, with ease, and without error.


## 2. Other stakeholders and their goals

* __Recipe Database__: Doesn't want original recipe files to be corrupted or altered.


## 3. Preconditions

* The recipe management system can understand and process the given quantity for each ingredient.
* The user has indicated how they want to scale the recipe (doubled, halved, etc.).

## 4. Postconditions

* Ingredients are identified and matched to all recipes in the database.
* The recipe management system has filtered the recipes based on additional filters supplied by user.


## 5. Workflow

Casual workflow for _scale ingredients_:

```plantuml
@startuml

skin rose

title Scale Ingredients (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Identifies recipe to scale;
:Indicate scalar for the given recipe (*2, *0.5, etc.);

|Recipe Management System|
:Identifies the ingredients and corresponding
quantities in the given recipe;

|Recipe Management System|
:Applies the scalar multiple to the
qualitative quantities in the recipe;
:Produces a new, modified version of the recipe
with the new ingredient values;

:Present scaled recipe to user;
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

ui -> user: Present recipe
user -> ui: Choose to scale recipe
user -> ui: Enter scalar (double recipe, half it, etc.)
ui -> cont: Communicate the (scalar)
cont -> rec: recipe.scale_ingredients(scalar)
rec -> cont: Return recipe details (name, ingredients, instructions)
cont -> ui: Display recipe details with new ingredient quantites
ui -> user: Present recipe details

@enduml
```


