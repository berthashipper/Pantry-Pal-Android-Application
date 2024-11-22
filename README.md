# CMPU-203 F24 - Team 2G

## Pantry Pal Android App
Welcome to our **Pantry Pal Android App**! This app brings your kitchen to life by organizing your pantry, suggesting recipes, and managing your cookbook in one seamless experience. Whether you’re an avid home cook or someone looking to make meals from whatever is left in the fridge, Pantry Pal is here to simplify your culinary journey.

---

## Description
**Pantry Pal** is a comprehensive kitchen management tool that combines pantry tracking, recipe suggestions, and a customizable cookbook. Users can seamlessly navigate between sections like "View Pantry," "Generate Recipe Suggestions," and "View Cookbook" through a clean and intuitive menu.  

This project represents a port of an extensive Java-based prototype into an Android environment, ensuring a robust foundation for future enhancements. Future iterations aim to enhance its utility further with shopping lists, meal planning, and ingredient scaling.

---

## Features

### **Pantry Management**
- Add, edit, and delete ingredients with intuitive buttons.
- The pantry stores all ingredients, allowing the app to analyze available items for recipe generation.
- **Example**: Adding "bread" and "cheese" suggests a "Grilled Cheese Sandwich."

### **Recipe Suggestions**
- The "Generate Recipes" feature provides personalized recipe ideas based on pantry contents.
- The algorithm intelligently matches ingredients to recipes, prioritizing practicality (ingredients in the pantry don't have to exactly match how they're written in the recipe, allowing for flexibility in ingredient variations and better suggesting power).

### **Cookbook Management**
- View a collection of 10 pre-loaded recipes (for now) with detailed instructions, ingredients, preparation steps, cook time, and serving size.
- Upload personal recipes by specifying ingredients, steps, cook time, serving size, and a description.
- **Search Functionality**: Quickly access recipes by name or keyword, including user-added recipes.
- **Planned Enhancements**: Filter recipes by categories like "dinner," "dessert," or "vegan."

### **Navigation**
- A consistent, accessible menu allows users to switch between features without disruption.
- Features include:
  - **View Pantry**
  - **Generate Recipe Suggestions**
  - **View Cookbook**
  - **View Shopping List** *(planned for next iteration)*

### **User Interface**
- Clean, visually appealing design prioritizing ease of use.
- Fragment container views enable seamless navigation across app sections.

---

## Installation

### Steps:
1. Clone the repository:  
2.
```
https://gitlab.cs.vassar.edu/cmpu203-f24-projects/team-2g.git
```
3. Open the project in Android Studio.
4. Connect your Android device or emulator.
5. Build and run the app.

---

## Limitations

- **Recipe Database**
  - The recipe database currently includes only 10 pre-loaded recipes. More recipes will be added in future iterations.
- **Shopping List**
  - The "View Shopping List" feature is not yet functional and will be implemented in the next iteration.
- **Ingredient Scaling**
  - Recipes cannot currently adjust ingredient quantities for different serving sizes.
- **Meal Planning**
  - While recipes can be added, there is no dedicated meal-planning feature or advanced recipe browsing beyond the pre-loaded ones.

---
