# Upload Personal Recipe

## 1. Primary actor and goals
* __User__: wants to upload a complete personal recipe, including ingredients and preparation steps.
* __Recipe Management System__: wants to understand each ingredient in the new uploaded recipe and store both the new ingredients and recipe for access by the Recipe Database.


## 2. Other stakeholders and their goals

* __Recipe Database__: wants to understand and process the newly uploaded recipe for future retrieval and display.

## 3. Preconditions

* The user identifies the personal recipe.
* The system is ready to process and understand the personal recipe.

## 4. Postconditions

* Recipe is successfully saved into a Recipe Database.


## 5. Workflow

Casual workflow for _upload personal recipe_:

```plantuml
@startuml

skin rose

title Upload Personal Recipe (casual level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Enters personal recipe name;
:Enters ingredients for the recipe;

|Recipe Management System|
:Analyze ingredients in the recipe;

while (Are ingredients valid?) is (no)
  |User|
  :Error message, prompts user to re-enter or modify
  invalid ingredients;
  :Modifies/re-enters ingredients;  
endwhile(yes) 

|User|
:Enters preparation steps;

|Recipe Management System|
:Analyze preparation steps;

while (Are preparation steps valid?) is (no)
  |User|
  :Error message, prompts user to re-enter or modify
  invalid preparation steps;
  :Modifies/re-enters preparation steps;  
endwhile(yes)
  

|Recipe Management System|
:Approve recipe;
:Store approved recipe;

stop


@enduml
```