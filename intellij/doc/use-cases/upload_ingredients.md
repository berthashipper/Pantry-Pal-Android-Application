# Upload Ingredients

## 1. Primary actor and goals
__User__: wants to upload all the ingredients in their pantry to find out what recipes they can make with them.


## 2. Other stakeholders and their goals

* __System__: Wants to save the ingredients into the database for easy access and analysis.



## 3. Preconditions

* The user has successfully uploaded all the ingredients to they system and specified any other filters/specifications.

## 4. Postconditions

* Ingredients are saved into their online pantry.


## 4. Workflow

Casual workflow for _uploading ingredients_:

```plantuml
@startuml

skin rose

title Upload Ingredients (casual level)

'define the lanes
|#application|User|
|#implementation|System|

|User|
start
:Enter all ingredients in pantry;

|System|
while (More items?) is (yes)
  :Enter ingredient info;
  |System|
  :Approve ingredient;
  :Store ingredient;
endwhile (no)


:Ask for any other filters/specifications;

|User|
:Select filters/specifications for recipe;

|System|
if (Filters?) is  ( Yes ) then
:Generate recipe with filters considered;
else ( No ) 
:Generate any recipe from database;
endif

stop
@enduml
```


