# Generate Recipe Suggestions

## 1. Primary actor and goals
* __User__: wants to receive accurate and appealing recipe suggestions that align with their specifications (e.g., type of dish, dietary preferences) and the ingredients available.
* __Recipe Management System__:  filters through the recipe database based on the ingredients stored and user specifications, presenting fitting recipes to the user.


## 2. Other stakeholders and their goals

* __User__: wants the recipe system and database to interact efficiently to receive timely and relevant recipe suggestions based on their input.



## 3. Preconditions

* The recipe management system has understood, processed, and stored all the uploaded ingredients.
* The recipe database can pull the ingredients stored in the management system and identify the ingredients in all the recipes in the database.

## 4. Postconditions

* Ingredients are identified and matched to all recipes in the database.
* The recipe management system has filtered recipes based on additional filters supplied by the user (e.g., recipe type, dietary preferences).



## 5. Workflow

Fully-dressed workflow for _generate_recipe_:

```plantuml
@startuml

skin rose

title Generate & filter recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Request recipe suggestions;
:Provide specifications (e.g., type of dish, dietary tags);

|Recipe Management System|
:Evaluates ingredients in pantry;
:Pulls recipes from database;

|Recipe Management System|
:Evaluate specifications against each recipe;
:Filter recipes based on user specifications;
:Produce a final compilation of recipes
that satisfy the given ingredients and preferences;
:Present list of recipes to user;

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
participant ": RecipeDatabase" as recd

ui -> user : Display generate recipe button
user -> ui : Click "get recipe suggestions"
ui -> user : Prompt for recipe type and dietary preferences
user -> ui : Provide preferences
ui -> cont : getRecipes(preferences)
[o-> cont : getPantry()
[o-> cont : getListofAllRecipes()

participant "getRecipeList[i] : RecipeList" as rec
cont -> recd : getRecipeList(preferences)
loop i in 0..recipeList.size-1
recd -> rec : recipe = getRecipeList[i].getRecipeInfo()
end

rec -> cont : recipeList.list()
cont -> ui : displayRecipes()
ui -> user : Present list of recipes

@enduml
```

