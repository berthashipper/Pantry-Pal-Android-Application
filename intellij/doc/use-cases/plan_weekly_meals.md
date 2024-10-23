
# Plan Weekly Meals

## 1. Primary actor and goals
* __User__: want to plan their weekly meals by adding recipes they can make to list.
* __Recipe Management System__: wants to understand the recipes and pull from the Recipe Database.


## 2. Other stakeholders and their goals

* __User__: want the list to be clear and formed efficiently



## 3. Preconditions

* The user choose recipes.
* The system is ready to process and understand the recipes and form the list.

## 4. Postconditions

* Recipe System processes the list and show it to the user.


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
:__Searches recipes__;
:Chooses recipes to make for the week;

|Recipe Management System|
:Pulls the recipes;
:Store recipes to list;
while (More recipes?) is (yes)
   |User|
  :Choose another recipe;
  |Recipe Management System|
:Store Recipe to list;
endwhile (no)

|Recipe Management System|
:Present the final weekly meals;
:Give user option to enact __generate grocery list__ use case;

stop
@enduml

```


