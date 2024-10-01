# Delete Pantry Items

## 1. Primary actor and goals
* __User__: want to delete ingredients their pantry.
* __Recipe Management System__: wants to understand each ingredient.


## 2. Other stakeholders and their goals

* __User__: Want the recipe system to delete the right ingredients efficiently.



## 3. Preconditions

* The user identifies and chooses the items in the pantry.
* The system is ready to process and understand the ingredients.

## 4. Postconditions

* Ingredients are successfully deleted from the user's online pantry.


## 4. Workflow

Casual workflow for _delete_pantry_items_:

```plantuml
@startuml

skin rose

title  Delete Pantry Items (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|
|#lightgreen|Recipe Database|


|User|
 start
:Select one ingredient from pantry;

|Recipe Management System|
while (More ingredients?) is (yes)
:Ask user to select another ingredient;
|User|
:Selects ingredient;
endwhile (no)

  |Recipe Management System|
  :Delete ingredient;

stop
@enduml
```


