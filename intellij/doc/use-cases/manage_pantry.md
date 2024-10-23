# Manage Pantry Items

## 1. Primary actor and goals
* __User__: want to manage ingredients their pantry, including action of upload and delete.
* __Recipe Management System__: wants to process the action that user takes and change the database accordingly.


## 2. Other stakeholders and their goals

* __User__: Want the recipe system to delete or upload the right ingredients efficiently.


## 3. Preconditions

* The user get into the pantry, either upload or delete the items.
* The system is ready to process and understand the ingredients.

## 4. Postconditions

* Ingredients are successfully deleted from or uploaded to the user's online pantry.


## 5. Workflow

Casual workflow for _manage_pantry_items_:

```plantuml
@startuml

skin rose

title  Manage Pantry Items: UPLOAD (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|Recipe Management System|
start
:Displays pantry;
|User|
:Searches for ingredient;
|Recipe Management System|
while (There are ingredients match user's search) is (no) 
|User|
:Searches again;
endwhile (yes);

|Recipe Management System|
:Shows ingredients that match search;

|User|
:Selects one ingredient to add to pantry;
|Recipe Management System|

:Store ingredient to pantry;

|User|
:See the changes in pantry;

stop
@enduml
```

```plantuml
@startuml

skin rose

title  Manage Pantry Items: DELETE (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|Recipe Management System|
start
:Displays pantry;
|User|
:Select ingredient from pantry;
|Recipe Management System|

:Remove ingredient from pantry;

|User|
:See the changes in pantry;

stop
@enduml
```

```plantuml
@startuml

skin rose

title  Manage Pantry Items: EDIT (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|Recipe Management System|
start
:Displays pantry;
|User|
:Selects ingredient from pantry to edit;
:Updates quantity of ingredient;

|Recipe Management System|

:Stores new ingredient quantity to pantry;

|User|
:See the changes in pantry;

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
participant ": Controller"  as cont
participant ": curPantry : Pantry" as pantry
participant ": Ingredient" as ingr

ui -> user : Display add/delete buttons
user -> ui : Input ingredient name
user -> ui : Click "add ingredient"
ui -> cont : addItem(name)
cont -> pantry : addItem(name)
pantry -> ingr **: ingr = create(name)
pantry -> cont : curPantry.list()
cont -> ui : updateDisplay(pantry)
ui -> user : show current pantry list

@enduml
````

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller"  as cont
participant ": curPantry : Pantry" as pantry

ui -> user : Display add/delete buttons
user -> ui : Select existing ingredient name
user -> ui : Click "delete ingredient"
ui -> cont : deleteItem(name)
cont -> pantry : deleteItem(name)
pantry -> cont : curPantry.list()
cont -> ui : updateDisplay(pantry)
ui -> user : show current pantry list

@enduml
````

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller"  as cont
participant ": curPantry : Pantry" as pantry

ui -> user : Display edit buttons
user -> ui : Select existing ingredient name
user -> ui : Click edit button to change quantity
ui -> cont : editItem(name, quantity)
cont -> pantry : editItem(name, quantity)
pantry -> cont : curPantry.ingredient()
cont -> ui : updateDisplay(pantry)
ui -> user : show updated ingredient information

@enduml
````