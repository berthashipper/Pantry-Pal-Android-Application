# Iteration Planning
Factors in assessing use case priority:
* **Criticality**: how much business value the functionality brings to the client
* **Risk**: technical complexity, uncertainty regarding feasibility, usability, or other
* **Coverage**: how many parts of the system are affected by it

___
## First Iteration (10/4)

## Rank
### High
* **Manage pantry**
  * Software relies on the fact that ingredients can be uploaded, processed, and stored
  * This will be the starting point for the app and user interface
* **Generate recipes**
  * Need to incorporate the EDAMAM API
  * Recipe database needs to be populated and be able to be searched through given ingredients in recipes
### Medium
* **Upload personal recipes**
  * As long as the recipe database can have some recipes in it, this is complementary
* **Scale ingredients**
  * Affects user satisfaction
### Low
* **Plan weekly meals & Generate grocery list**
    * Complementary features, not core or main functionality of app
* **Search recipes**
  * More important that recipes can be suggested rather than searched 

___
## Next Iteration (10/30)
- The prototype functionality we implemented here includes Managing Pantry, Generating Recipie Suggestions (based on pantry, not filters), Uploading personal Recipes, Viewing recipes in Cookbook, and Searching Recipes (in Cookbook) by name.

For the next iteration:
- Incorporating more recipes (thinking about parsing a database) so we can implement **filtering recipe** functionality.
- On that note, it would be good to flesh out what we can do with more recipes, including the whole **manage cookbook** (includes saving and viewing saved recipes), and **scaling ingredients** in recipes.
- Also would be good to populate pantry with more ingredients (possibly from API database).


___
## Next/FINAL Iteration (11/22)
- In this iteration, we focused on transferring the extensive functionality we had developed in our Java prototype into the Android environment. Given the scope and intricacy of the features in the Java prototype, we dedicated considerable effort to ensuring that all the existing functionalities were fully integrated into the Android app. As a result, while we couldn't introduce new use cases, we prioritized refining and optimizing the features already in place to ensure they worked seamlessly across the Android platform and that we had a robust, working prototype.

- The application includes a functional pantry feature where users can add, edit, and delete ingredients through easy buttons. Upon adding ingredients like bread and cheese to their pantry, users can click the "Generate Recipes" menu button, which offers recipe suggestions based on available ingredients. For instance, if bread and cheese are added, the app will suggest a "Grilled Cheese Sandwich." This feature is powered by a well-structured recipe suggestion algorithm, which analyzes the pantry contents and generates matching recipes.

- The menu offers easy navigation with options to "View Pantry," "Generate Recipe Suggestions," "View Cookbook," and "View Shopping List" (this one staged for next iteration). These menu options are accessible at any time through use of the fragment container view, ensuring that users can quickly and easily navigate between different features without any unnecessary disruptions to their experience.

- The "View Cookbook" section currently contains 10 pre-loaded recipes, and users have the ability to add their own personal recipes via the "Upload Recipe" button. When uploading a recipe, users can enter as many ingredients and steps as necessary, as well as other details like cook time, serving size, and a description. Additionally, the cookbook supports a search function, allowing users to find recipes by name or keyword. For example, searching for "cake" will return results like "Chocolate Cake" and "Pancakes". Any time the user is viewing a list of recipes, they can click on its title card to open a detailed page displaying the full recipe with all ingredients, instructions, and preparation steps.

- Throughout the app, we have maintained a consistent, clean, and aesthetically pleasing user interface. The design prioritizes ease of use while ensuring a visually appealing layout that enhances the overall user experience.

For the next iteration:
- We plan to build on this solid foundation by introducing additional functionality, including the the shopping list feature, which will allow users to create and manage shopping lists based on recipe ingredients or pantry shortages.
- Additionally, we aim to expand the recipe database, with the goal of making the app even more dynamic and versatile.
- A feature that excited us about this app was the ability to scale ingredients in recipes. We will prioritize the implementation of an intuitive scaling feature that makes these adjustments seamless, eliminating the need for cumbersome manual calculations.
- Lastly, we plan to introduce a meal planning feature that will allow users to save their favorite recipes for an upcoming week. The app will automatically generate a grocery list by identifying any missing ingredients from the saved recipes, making it easier for users to stay organized and prepared for the week ahead. This combination of meal planning and grocery list generation will further elevate the app's utility.