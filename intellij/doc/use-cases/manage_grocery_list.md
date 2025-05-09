# Manage Grocery List

## 1. Primary actor and goals
* __User__: wants to create, edit, delete, and view items on their grocery list.
* __Recipe Management System__: allows users to manage their grocery list efficiently, including adding items they need to buy, marking items as purchased, and removing items from the list.


## 2. Other stakeholders and their goals

* __Developer__: Ensures the Recipe Management System is functional and that user data is secure.


## 3. Preconditions

* User should have an existing grocery list or the ability to create a new one.


## 4. Postconditions

* User successfully adds, edits, or deletes items from their grocery list.
* The updated grocery list is displayed to the user after any modifications.


## 5. Workflow

Casual workflow for _manage_grocery_list_:

```plantuml
@startuml

skin rose

title Manage Grocery List (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select option (Add, Delete, View Grocery List, Edit);

|Recipe Management System|
:Receive user action;
if (Add Ingredient)
|Recipe Management System|
:Prompt for item details (name, quantity);
|User|
:Input item information;
:Save item to grocery list;
|Recipe Management System|
:Notify user of successful addition;
stop

elseif (Delete Ingredient)
:Show list of existing grocery items;
|User|
:Select item to delete;
:Remove item from grocery list;
|Recipe Management System|
:Notify user of successful deletion;
stop

elseif (View List)
|Recipe Management System|
:Display list of items in the grocery list;
stop


elseif (Edit Ingredient)
:Show list of existing grocery items;
|User|
:Select item to edit;
:updated quantity of ingredient;
|Recipe Management System|
:Stores new ingredient quantity to pantry;
|User|
:See the changes in pantry;
stop

else
stop
@enduml
```


## 6. Sequence Diagram


```plantuml
```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller"  as cont

ui -> user : Display add ingredient option
user -> ui : Input ingredient name
user -> ui : Input ingredient quantity
user -> ui : Input ingredient unit
user -> ui : Input dietary tags
ui -> cont : addIngredient(name, quantity, unit, tags)
cont -> GroceryListFragment : onAddIngredientToGroceryList(String name, double qty, String unit)
cont -> ui : displayFragment(groceryListFragment)
ui -> user : Shows updated Grocery list

@enduml
````

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller"  as cont

ui -> user : Display delete ingredient option
user -> ui : Input ingredient name to delete
ui -> cont : deleteIngredient(name)
cont -> GroceryListFragment : onRemoveIngredient(Ingredient ingredient)
cont -> ui : displayFragment(groceryListFragment)
ui -> user : Shows updated Grocery list

@enduml
````

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller"  as cont

ui -> user : Display edit buttons
user -> ui : Click edit button on chosen ingredient card
ui -> cont : editItem(name, quantity)
cont -> GroceryListFragment : onEditIngredientGroceryList(Ingredient ingredient)
cont -> ui : displayFragment(groceryListFragment)
ui -> user : Shows updated ingredient information

@enduml
````