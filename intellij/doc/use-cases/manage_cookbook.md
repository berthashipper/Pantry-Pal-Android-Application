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

Casual workflow for _manage_cookbook_:

```plantuml
@startuml

skin rose

title Manage Cookbook (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Select option (Upload, Save, View);

|Recipe Management System|
:Receive user action;
if (Save Recipe)
|Recipe Management System|
:Present user with recipe;
:Prompt user to save recipe;
|User|
:Save to cookbook;
stop

elseif (View Recipes)
|Recipe Management System|
:Display list of recipes in the cookbook;
stop

else
stop
@enduml
```
