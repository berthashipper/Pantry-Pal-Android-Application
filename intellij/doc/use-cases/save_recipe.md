# Save Recipe

## 1. Primary actor and goals
* __User__: wants to save a recipe in the Recipe Management System for future reference.
* __Recipe Management System__: stores recipes, ensuring they are easily retrievable and well-organized.



## 2. Other stakeholders and their goals

* __Developer__:  Ensures the system is secure, reliable, and that recipe data is properly managed.


## 3. Preconditions

* A recipe must be already formed (either by user or from database)

## 4. Postconditions

* The recipe is saved in the user's cookbook within the Recipe Management System.

## 5. Workflow

Casual workflow for _save_recipe_:

```plantuml
@startuml

skin rose

title Save Recipe (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|Recipe Management System|
start
:Presents recipe information;
|User|
:Choose to save recipe;

|Recipe Management System|
:Save recipe to user's cookbook;
:Notify user of successful save;

|User|
:See saved recipe;
stop
@enduml
```