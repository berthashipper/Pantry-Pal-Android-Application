# Non-Functional Specifications:
## FURPS+ model:
* __Functional__:
  * The system must allow user to modify their pantry (upload/delete ingredients).
  * Must process and store ingredients.
  * Must hold a database of recipes from both the internet and upload by user.
  * Must be able to identify ingredients in recipes and further filter recipes by other restrictions.


* __Usability__:
  * Should be easy and intuitive to use, as the application is designed to simplify and help manage cooking processes, not make them more complicated.
  * Text should be visible from 1 meter if a screen.
  * Colors associated with common forms of color blindness should be avoided.


* __Reliability__:
  * Must be able to recover stored ingredients and uploaded recipes if the app crashes
  * Should provide reliable and accurate recipe suggestions to the user.


* __Performance__:
  * Fast response time (<10 seconds) so that the user isn't waiting around to get their recipe suggestions — this means the database is easily accessed so that the management system can search through the recipes with ease according to the ingredients it has stored.


* __Supportability__:
  * Recipe management system should be able to adapt to ingredients it hasn't seen before.
  * It might be hard to make software as a whole configurable internationally because of language differences — as of now, the application will be based in English.


* __Implementation__:
  * Software must run on Android devices.
  * Software must be written using Java 
  * English is the only language the app can understand as of now.


* __External interfaces__:
  * Must connect to internet to be working with 3rd-party systems on the internet to gather recipes from various sources/blogs/websites.


* __Legal__:
  * There shouldn't be any copyright issues with recipes or brand names of ingredients.