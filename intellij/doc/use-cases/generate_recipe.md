# Generate Recipe Suggestions

## 1. Primary actor and goals
* __User__: wants to receive accurate and appealing recipe suggestions that aligns with their specifications and ingredients.
* __Recipe Database__: should be holding a comprehensive list of recipes.
* __Recipe Management System__:  filters through the recipe database based on the ingredients the system has stored and presents fitting ones to the user.


## 2. Other stakeholders and their goals

* __User__: Wants the recipe system and database to interact efficiently so that they get their recipe suggestiions in a timely manner.



## 3. Preconditions

* The recipe management system has understood, processed, and stored all the uploaded ingredients.
* The recipe database can pull the ingredients stored in the management system and identify the ingredients in all the recipes in the database.

## 4. Postconditions

* Ingredients are identified and matched to all recipes in the database.
* The recipe management system has filtered the recipes based on additional filters supplied by user. 


## 4. Workflow

Fully-dressed workflow for _generate recipe_:

```plantuml
@startuml

skin rose

title Generate recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|
|#lightgreen|Recipe Database|

|User|
start
:Ask for recipe suggestions;

|Recipe Management System|
:Pulls ingredients from the stored list;

|Recipe Database|
:Holds a list of all recipes;

|Recipe Management System|
:Evaluates list of ingredients on each
recipe in the database;
:Produces a final compilation of recipes
that satisfy the given ingredients;

if (Asks user for filters) is  ( yes ) then
|User|
:Specify filters (cook time,
vegetarian, etc.);
|Recipe Management System|
:Filter through the compiled list of recipes
according to additional specifications;
|Recipe Management System|
else ( no ) 
:Hold the compiled
list of recipes;
endif

:Present list of recipes to user;
stop
@enduml
```


