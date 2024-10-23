
# Plan Weekly Meals

## 1. Primary actor and goals
* __User__: wants to plan their weekly meals by selecting and organizing recipes from a saved list.
* __Recipe Management System__: wants to process the user's selections, pull recipes, and create an accessible list


## 2. Other stakeholders and their goals

* __User__: want the list to be clear and formed efficiently



## 3. Preconditions

* The user has access to a list of saved recipes.
* The system is ready to process and understand the user's selections.

## 4. Postconditions

* The Recipe Management System processes the selected recipes and displays a structured weekly meal plan to the user.



## 5. Workflow

Casual workflow for _plan weekly meals_:

```plantuml
@startuml

skin rose

title Plan Weekly Meals (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:__View saved recipes__;
:Selects recipes for the week;

|Recipe Management System|
:Stores selected recipes in the meal plan;
while (More recipes?) is (yes)
   |User|
  :Choose another recipe;
  |Recipe Management System|
:Store recipe to meal plan;
endwhile (no)

|Recipe Management System|
:Present the final weekly meal plan;
:Provide option to execute __generate grocery list__
using ingredients from recipes;

stop
@enduml

```


