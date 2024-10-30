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

## 6. Sequence Diagram

```plantuml
@startuml
skin rose

hide footbox

actor User as user
participant ": UI" as ui
participant ": Controller" as cont
participant ": Recipe Database" as db

ui -> user : Display main menu
user -> ui : Select "Upload a Recipe"
user -> ui : Enter recipe name
user -> ui : Enter description
user -> ui : Enter cook time
user -> ui : Enter serving size

ui -> user : Add ingredients (enter 'done' to finish)
loop Add ingredients
    user -> ui : Enter ingredient name
    user -> ui : Enter quantity
    user -> ui : Enter unit
    user -> ui : Enter dietary tags
    ui -> cont : Add ingredient(ingredient)
end

user -> ui : Add cooking instructions
loop Add instructions
    user -> ui : Enter instruction
end

ui -> cont : uploadRecipe(name, description, cookTime, servingSize, ingredients, instructions)
cont -> db : uploadRecipe(recipe)
db -> cont : confirmation
cont -> ui : Recipe uploaded successfully!



@enduml
```