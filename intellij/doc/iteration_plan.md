# Iteration Planning
Factors in assessing use case priority:
* **Criticality**: how much business value the functionality brings to the client
* **Risk**: technical complexity, uncertainty regarding feasibility, usability, or other
* **Coverage**: how many parts of the system are affected by it

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
    * Complementary features, not core the main functionality of app
* **Search recipes**
  * More important that recipes can be suggested rather than searched 

___
## Next Iteration (10/30)
- Incorporating more recipes (thinking about parsing an API) so we can implement **filtering recipe** functionality.
- On that note, it would be good to flesh out what we can do with more recipes, including the whole **manage cookbook** (includes saving and viewing saved recipes), and **scaling ingredients** in recipes.