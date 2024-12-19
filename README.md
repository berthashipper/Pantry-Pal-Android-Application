# Pantry Pal Android App

Welcome to **Pantry Pal**, your ultimate kitchen assistant! This app brings your kitchen to life by organizing your pantry, suggesting recipes, helping plan your grocery list, and manage your cookbook in one seamless experience. Whether you’re an avid home cook or a college student looking to make meals from whatever is left in the fridge, Pantry Pal is here to simplify your process, making cooking stress-free and enjoyable.
Key features include:
- Effortless pantry tracking to help you see what you have and what’s running low.
- Personalized recipe suggestions based on your pantry contents and cookbook recipes.
- A customizable digital cookbook to store, organize, and filter your favorite recipes.
- Integrated grocery list management to streamline shopping trips.

---

## Description
**Pantry Pal** is more than just an app—it’s a solution to modern kitchen challenges. Developed with Java using an MVC architecture, Pantry Pal enhances the cooking experience by combining advanced features into a seamless application:
1. **Pantry**: Maintain a digital inventory of your pantry items.
2. **Grocery List**: Build and organize your grocery list effortlessly for quick and efficient shopping.
3. **Cookbook**: Create, save, and manage your personal collection of recipes in a cleanly designed digital cookbook.
4. **Recipe Suggestions**: Get personalized recipe recommendations based on the items you already have in your pantry and recipes you have in your cookbook.

Pantry Pal was inspired by a personal mission to address the challenges of managing food allergies and organizing a safe, nutritious kitchen. By streamlining processes like recipe management and grocery planning, it reduces the workload for users while fostering a sustainable, waste-free cooking practices.

---

## Features

### **Pantry Management**
- **Dynamic Ingredient Display**: The pantry has been transformed from a basic bullet-pointed list to an interactive card-based design using RecyclerView. Each card neatly displays the ingredient name, and tapping it reveals additional details such as quantity, unit, and associated tags.
- **Effortless Editing and Deletion**: Each ingredient card includes intuitive edit and delete icons. Editing allows users to seamlessly update the quantity, while deletion prompts an AlertDialog confirmation to avoid accidental removals.
- **Add and Search Ingredients**: The top of the pantry interface features dedicated icons for adding and searching ingredients. These icons direct users to designed screens for each of these tasks, enhancing usability and simplicity.
- **Seamless Navigation**: All secondary screens provide a `Back to Pantry` arrow for convenient cancellation or quick returns without committing to changes.
- **Clear Pantry Option**: A one-click `Clear Pantry` button allows users to delete all ingredients, with a confirmation dialog to prevent unintended actions.
- **Comprehensive Pantry Storage**: The pantry serves as the central repository for ingredients, powering advanced features like recipe generation by analyzing available items.

### **Recipe Suggestions**
- Provides suggested recipes that work with their pantry contents.
- **Smart Recipe Generation**: The `Generate Recipes` algorithm intelligently matches pantry ingredients to recipes, emphasizing both flexibility and practicality. It accommodates variations in ingredient names and types, ensuring that recipes are suggested even when pantry items don’t exactly match the recipe’s specified ingredients, enhancing its adaptability and suggestion power.
- **Quantity Verification**: The system checks pantry quantities before suggesting recipes, ensuring that users have sufficient amounts of each ingredient to prepare the dish.

### **Cookbook Management**
- View a collection of 10 pre-loaded recipes, displayed through an intuitive CardView recycler view format. Clicking on a recipe reveals full details, including preparation steps, ingredients, cook time, and serving size.
- **Streamlined Navigation**: Secondary screens now features a `Back to Cookbook` arrow icon to allow them to cancel and leave their action without changing anything, if desired. As well, screens that take a user away from the recipe they were on, show a `Back to Recipe` arrow to allow them to go back to the recipe they were on just as easily.
- **Upload Your Favorites**: Users can upload their own recipes by providing details like ingredients, steps, cook time, serving size, and descriptions. Uploaded recipes integrate seamlessly into the suggestion engine and pantry.
- **Search Functionality**: Quickly locate recipes by entering a name or keyword; works for both preloaded and user-added recipes.
- **Comprehensive Recipe Editing**:
  - Cook time, serving size, ingredients, and instructions fields can all be edited and customized after a recipe has been created.
  - Clicking the edit icon on the cook time and yield fields will open an EditDialogue (with cancel option), for quick adjustments without switching screens, and pre-fills the quantity field with the current quantity to make updating it easy and intuitive. 
  - Clicking the add, edit, or delete icon on the ingredients field will allow the user to perform those actions through intuitive UI fragments.
- **Customizable Tags**: Users can add or remove tags to categorize recipes, such as VEGAN, HIGH PROTEIN, DESSERT. Tags are fully customizable, allowing users to tailor categories to their preferences. Simply click the add or delete icons by the `Tags` header to do so.
- **Filter Cookbook**: Users can filter their cookbook by dietary tags assigned to recipes. These tags can represent preferences, dietary restrictions, or any other personalized category. This feature makes navigating and exploring recipes much faster, tailored to individual dietary goals or user preferences. Filtering works dynamically with the tags a user has assigned, allowing recipes to be easily sorted and located by these categories.
- **Recipe Scaling**: Scaling recipes for different serving sizes can be tedious and error prone, but with the `Scale Recipe` feature, it's effortless. Simply input a multiplier, and the app recalculates all ingredient quantities instantly. Whether you're doubling a recipe for a party or halving it for a quiet dinner, this feature ensures accurate adjustments. Plus, you can generate a grocery list tailored to the scaled recipe, saving you time and hassle at the store.
- **Integrated With Grocery List**: The most special feature of the recipe view within the cookbook is it's interaction with the grocery list. There is now a `Shop For` shopping cart icon that, when clicked, adds the necessary ingredients and quantities to the grocery list, with smart logic to ensure accuracy and efficiency. This feature checks both the pantry and grocery list to avoid unnecessary duplication: if an ingredient is already in the pantry in sufficient quantity, it won't be added; if more is needed, only the required amount will be added. Similarly, if the ingredient is already on the grocery list, it will simply update the quantity rather than duplicating entries. This ensures users always have the right amount of ingredients without the hassle of manual adjustments.

### **Grocery List Management**
- **Card-Based Design**: The grocery list adopts the same clean CardView format as the pantry and cookbook, presenting ingredient names and quantities in an organized way.
- **Editable Entries**: Users can update ingredient quantities directly from the grocery list by clicking on the edit icon for a given ingredient, and are presented with an intuitive EditDialog.
- **Confirmation for Deletion**: Clicking the delete icon to delete an ingredient prompts a confirmation dialog to prevent accidental actions.
- **Clear List Option**: Like the pantry, the grocery list includes a `Clear List` button, with a confirmation dialog for safety.

### **Navigation**
- **Consistent Menu**: A unified menu ensures smooth transitions between key app features, including:
  - **`View Pantry`**
  - **`View Cookbook`**
  - **`View Grocery List`**
  - **`Generate Recipe Suggestions`**
- **Back Navigation**: All secondary screens offer a back arrow for quick returns, maintaining an intuitive user experience.

### **Data Persistence**
- **Reliable Local Storage**: PantryPal keeps all your important cooking data—pantry ingredients, grocery list contents, cookbook recipes, and personalized edits—securely stored locally and seamlessly persistent across sessions. This means you can always rely on PantryPal as your go-to, comprehensive cooking companion, knowing your information is safe, accessible, and ready whenever you are.

### **User Interface**
- The app’s clean and visually appealing UI is crafted to provide an intuitive experience, prioritizing usability and efficiency.
- Fragment container views enable smooth transitions between app sections.

---

## Installation

### Steps:
1. Clone the repository:  
```
https://gitlab.cs.vassar.edu/cmpu203-f24-projects/team-2g.git
```
2. Open the project in Android Studio.
3. Connect your Android device or emulator.
4. Build and run the app.

---

## Limitations

- **Meal Planning And Recipe Discovery**
  - Currently, the app does not support a dedicated meal-planning feature or external recipe browsing. Future updates will enhance these areas to improve the user experience.
---
