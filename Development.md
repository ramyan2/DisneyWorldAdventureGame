Fixes to make based on feedback:
* Add more vertical space between variables and methods
* Make sure variable names are nounds
* Use more helper methods
* Check if JSON is formatted properly
* Test for bad and null URL

Outline for the plan of changes for this assignment:
* First I must extend my game so that it can take either a filename or a URL and load the JSON from that.
* This should be done as an argument on the command line
* Then work on extending the game engine by incorporating hidden rooms which can only be accessed if one has possession of a special item(golden key)
* To acquire this golden key the user has to solve a riddle or answer a question, once they do so they can access hidden rooms that get you to the end of the game much faster

Change in plan:
* 2 game extensions: fighting a monster by answering 5 questions in the room before the endroom, and having the ability to teleport to the end room when you answer a particular question on the first try at a particular room

Issues encountered during process:
* Wrote my JSON file and encountered some formatting issues but fixed them.
* Had trouble continuing the game with invalid inputs when I wanted the player to teleport
* Enabling a room was a difficult process since there were many conditions I had to make sure were met before enabling a certain room
* Figuring out how to incorporate my game extension of fighting a monster and adding it to my JSON
* Writing test cases for methods with user input