# Generate Recipe Suggestions

## 1. Primary actor and goals
* __User__: wants to receive accurate and appealing recipe suggestions that align with the ingredients available.
* __Recipe Management System__:  filters through the recipe database based on the ingredients stored and user specifications, presenting fitting recipes to the user.


## 2. Other stakeholders and their goals

* __User__: wants the recipe system and database to interact efficiently to receive timely and relevant recipe suggestions based on their input.



## 3. Preconditions

* The recipe management system has understood, processed, and stored all the uploaded ingredients.
* The recipe database can pull the ingredients stored in the management system and identify the ingredients in all the recipes in the database.

## 4. Postconditions

* Ingredients are identified and matched to all recipes in the database.
* The recipe management system has filtered recipes.



## 5. Workflow

Fully-dressed workflow for _generate_recipe_:

```plantuml
@startuml

skin rose

title Generate & Filter Recipe (fully-dressed level)

'define the lanes
|#application|User|
|#implementation|Recipe Management System|

|User|
start
:Request recipe suggestions based on ingredients in pantry;

|Recipe Management System|
:Evaluate ingredients in pantry;
:Generate list of matching recipes;

|Recipe Management System|
:Check pantry ingredients against recipes;
:Present list of matching recipes to user;

if (No matching recipes)
    :Show "No matching recipes found" message;
    stop
   else
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
participant ": GenerateRecipe" as gr
participant ": RecipeFragment" as rf
participant ": Cookbook" as cb
participant ": Pantry" as pantry

ui -> user: Display main menu
user -> ui: Select "Generate Recipe Suggestions"
ui -> cont: onGenerateRecipes()

cont -> gr: generateMatchingRecipes()
gr -> cb: Access all recipes
gr -> pantry: Access pantry ingredients
loop For each recipe in cookbook
    gr -> gr: Check if pantry has enough ingredients
    alt Matching ingredients found
        gr -> gr: Add recipe to matched recipes
    else No match
        gr -> gr: Skip recipe
    end
end
gr -> cont: Return matched recipes

alt Matched recipes found
    cont -> rf: Create RecipeFragment with matched recipes
    cont -> ui: displayFragment(RecipeFragment)
    ui -> rf: Display list of matched recipes
    rf -> ui: Display recipes
    ui -> user: Show matched recipes
else No matched recipes
    alt Current fragment is RecipeFragment
        cont -> rf: Show no recipes message
        rf -> ui: Display "No matching recipes found"
    else Different fragment
        cont -> rf: Create RecipeFragment with empty cookbook
        cont -> ui: displayFragment(RecipeFragment)
        ui -> user: Display "No matching recipes found"
    end
end

@enduml

```

