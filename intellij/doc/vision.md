## Target Audience
Anyone who wants to streamline and simplify their cooking processes.
Specifically, college students, young adults, or people with food restrictions.

## Main Features
An intuitive meal-planning assistant app that suggests recipes based on available ingredients. Users can upload their pantry items, and the app generates recipes that can be filtered by ease, serving size, and dietary restrictions. Features include scaling ingredients to match serving sizes, uploading personal favorite recipes, weekly meal planning, and generating customized grocery lists based on selected meals. The app helps users efficiently plan meals while accommodating specific food restrictions.r people with food restrictions.

## Constraints


## Actors
* __System__: processes ingredients and generates recipes
* __User__: Uploads recipes, browses for recipes, submits their pantry items


```plantuml
@startuml
skin rose

title Full overview

' human actors
actor "User" as user

' system actors
actor "Recipe system" <<recipe system>> as recipeSystem

' list all use cases in package
package NextGenPOS{
 usecase "Upload ingredients" as uploadingredients
 usecase "Generate recipe" as generaterecipe
 usecase "Upload recipes" as uploadrecipes
 usecase "Store ingredients" as storeing
 usecase "Store recipes" as storerec
 usecase "See pantry" as seepantry
 usecase "Delete ingredients" as deleing
 usecase "Search recipes" as searchrec
 usecase "Filter restrictions" as filterrestr
}

' list associations

seepantry -down-> deleing : <<includes>>
seepantry -down-> uploadingredients : <<includes>>
seepantry -down-> searchrec : <<includes>>
uploadingredients -down-> filterrestr : <<extends>>

uploadingredients -right-> generaterecipe : <<extends>>
filterrestr -right-> generaterecipe : <<extends>>

user --> uploadrecipes 
user --> seepantry

uploadingredients -right-> storeing : <<extends>>
uploadrecipes -left-> storerec : <<extends>>



recipeSystem --> generaterecipe
recipeSystem --> storeing
recipeSystem --> storerec



@enduml
```