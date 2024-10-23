```plantuml
@startuml

skin rose

hide empty methods
' classes

class Ingredient{
 String name
 int quantity
 String unit
 Set<dietary_tags> tags
 __
 public String toString()
}

class Pantry{
 Map<String, Ingredient> ingredientList
 --
 public void add_ingredient(String name, int quantity, String unit, Set<Ingredient.dietary_tags> tags)
 public void delete_ingredient(String name)
 public void edit_ingredient(String name, int quantity)
 public void searchIngredient(String name)
 public List<Ingredient> filter_ingredients_by_tag(Ingredient.dietary_tags tag)
 public String toString()
}

class Recipe{
 name
}

class RecipeDescription{
 summary
 ingredients
 cook time
 serving size
 dietary tags
}

class Application{

}

class User{
 username
}


' associations
 User "1" -down- "1" Application : \tOperates\t\t
Application "1" -right- "1" Pantry : \tContains\t\t
Pantry "1" -up- "*" Ingredient : \tContained-in\t\t
Recipe "*" -down- "1" RecipeDescription : \tIs-described-by\t\t
User "*" -left- "1" Recipe : \tIs-shown-to\t\t




@enduml
```