# Search Recipes

## 1. Primary actor and goals
* __User__: wants to search accurate recipe that aligns with their specifications.
* __Recipe Database__: should be holding a comprehensive list of recipes.
* __Recipe Management System__:  filters through the recipe database based on the recipes that the Recipe Database has stored and presents fitting ones to the user.


## 2. Other stakeholders and their goals

* __User__: Wants the recipe system and database to interact efficiently so that they get their recipe suggestions in a timely manner.



## 3. Preconditions

* The recipe system has understood, processed the recipe
* The recipe database can pull the recipes stored in the database.

## 4. Postconditions

* The recipe management system has filtered the recipes based on additional filters supplied by user.


## 4. Workflow

Fully-dressed workflow for _search recipe_:

```plantuml
@startuml

skin rose

title Search recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|
|#lightgreen|Recipe Database|

|User|
start
:Search recipe;

|Recipe Management System|
:Checks if there are recipes
that match keywords;

if (Matches found?) is (no) then
:Prepare error message;
|User|
:See that no recipes were
found from search;
stop
else (yes)

|Recipe Database|
:Pulls matching recipes held in API;

|Recipe Management System|
:Gathers a list of recipes from API
and internal list;
:Apply __filter_recipe__ use case;


:Present list of recipes to user;
stop
@enduml
```


