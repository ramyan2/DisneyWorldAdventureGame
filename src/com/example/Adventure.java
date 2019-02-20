package com.example;

import com.google.gson.Gson;
import java.net.URL;
import java.util.Scanner;
import java.io.InputStreamReader;


public class Adventure {

    public String currentRoom;

    public Layout parsedJson;

    public Room[] arrayRooms;

    public String startRoom;

    public String endRoom;

    public static Adventure game = new Adventure();

    public Direction[] directions;

    public Direction currentDirection;

    public Item[] items;

    public static String quitWord = "QUIT";

    public static String exitWord = "EXIT";

    public Room room;

    Player player = new Player();

    public boolean validInput = true;

    private static final String AdventuresJSON = Data.getFileContentsAsString("AdventuresJSON");

    public String inputDir;

    String url;

    public String itemAndDirection = "";

    public String answer = "";


    /**
     * parses the json file from the url
     * @return the parsed json
     */
    public Layout parsingJson() {
        System.out.println(url);
        Gson gson = new Gson();
        parsedJson = gson.fromJson(AdventuresJSON, Layout.class);

        try {
            URL obj = new URL(url);
            InputStreamReader reader = new InputStreamReader(obj.openStream());
            parsedJson = gson.fromJson(reader, Layout.class);

        } catch(Exception e) {
            System.out.println(e);
        }
        arrayRooms = parsedJson.getRooms();
        startRoom = parsedJson.getStartingRoom();
        endRoom = parsedJson.getEndingRoom();

        return parsedJson;
    }


    /**
     * goes through the array of rooms in my json until it finds the current room
     * and sets it to a Room object and sets an array of directions and items
     * @return room the current room
     */
    public Room setCurrentRoomObject() {
        for (int i = 0; i < arrayRooms.length; i++) {
            if (arrayRooms[i].getName().equals(currentRoom)) {
                room = arrayRooms[i];
                directions = room.getDirections();
                items = room.getItems();
            }
        }
        return room;
    }


    /**
     * checks if the inputted item is null
     * @param inputtedItem
     * @return
     */
    public String checkIfNull(String inputtedItem) {
        if (inputtedItem == null) {
            return null;
        }
        return inputtedItem;
    }


    /**
     * starts the game by setting the current room to the starting room
     * and prints this room's description and directions you can go from this room
     */
    public void startGame() {
        startRoom = parsedJson.getStartingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here. ");
        System.out.println(arrayRooms[0].getDescription());
    }


    /**
     * prints the room description based on the direction inputted by the user and returns a string of this description
     * @param inputtedDirection
     * @return description of current room object
     */
    public String printRoomDescriptionBasedOnDirection(String inputtedDirection) {
        if (inputtedDirection == null) {
            return null;
        }
        String actualDirection = inputtedDirection.toLowerCase();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().equalsIgnoreCase(actualDirection)) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();

                System.out.println(setCurrentRoomObject().getDescription());
                break;
            }
        }
        return setCurrentRoomObject().getDescription();
    }


    /**
     * prints the items you can possibly pickup from the room based
     * on the input and returns a string of these items
     * @return string of possible items
     */
    public String printPossibleItemsBasedOnInput() {
        setCurrentRoomObject();
        String itemsFromInput = "";
        System.out.println("Here you can pick up: ");
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i].getName());
        }
        for (int j = 0; j < items.length; j++) {
            itemsFromInput += items[j].getName() + " ";
        }
        return itemsFromInput.trim();
    }


    /**
     * prints the possible directions a player can go based on the room they are in
     * identifies which room a player is in based on the object they just picked up
     * @param inputtedItem
     * @return description of current room
     */
    public String printPossibleDirectionsPlayerCanGo(String inputtedItem) {
        if (inputtedItem == null) {
            return null;
        }
        String[] x = inputtedItem.split(" ");
        String actualItem = x[1].toLowerCase();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().equalsIgnoreCase(actualItem)) {
                player.setItems(items);
                currentRoom = player.getItems()[i].getRoom();
                for (int j = 0; j < setCurrentRoomObject().getDirections().length; j++) {
                    System.out.println("From here you can go: "
                            + setCurrentRoomObject().getDirections()[j].getDirectionName());
                }
                break;
            }
        }

        String directionNames = "";
        for (int k = 0; k < setCurrentRoomObject().getDirections().length; k++) {
            directionNames += setCurrentRoomObject().getDirections()[k].getDirectionName();
        }
        return directionNames.trim();
    }


    /**
     * helper method for checkIfInputIsAValidDirection that declares if the input is valid
     * @param tracker
     * @param equalsKeyWordIndicator
     * @param input
     * @return
     */
    public boolean declareInputIsValid(boolean tracker, boolean equalsKeyWordIndicator, String input) {
        if ((tracker && !equalsKeyWordIndicator) || (!tracker && !equalsKeyWordIndicator)) {
            System.out.println("I don't understand " + "'" + input + "'");
            validInput = false;
            return false;
        } else if (!tracker && equalsKeyWordIndicator) {
            System.out.println("I can't " + input);
            validInput = false;
            return false;
        }
        return true;
    }


    /**
     * if the user inputs an invalid direction then it prints
     * it cannot go that direction or it does not understand that input
     * @param inputtedItem
     * @return tracker which indicates if the input is valid (true) or not valid(false)
     */
    public boolean checkIfInputIsAValidItem(String inputtedItem) {
        if (inputtedItem == null) {
            return false;
        }
        String keyword = "pickup";
        boolean tracker = false;
        String[] x = inputtedItem.split(" ");

        if (x.length == 2) {
            String actualItem = x[1].toLowerCase();
            for (int i = 0; i < items.length; i++) {
                if (items[i].getName().equalsIgnoreCase(actualItem)) {
                    tracker = true;
                }
            }
            if (!(declareInputIsValid(tracker, x[0].equalsIgnoreCase(keyword), inputtedItem))) {
                tracker = false;
            }
        } else if (x[0].equalsIgnoreCase(keyword)) {
            System.out.println("I can't " + inputtedItem);
            validInput = false;
            tracker = false;
        } else {
            System.out.println("I don't understand " + "'" + inputtedItem + "'");
            validInput = false;
            tracker = false;
        }
        return tracker;
    }


    /**
     * runs the game when user inputs a valid url or types no
     * @return true if the game should continue
     * and false if the user reached the final destination or quit
     */
    public boolean runGame() {
        boolean tracker = true;
        printPossibleItemsBasedOnInput();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an item: ");
        String item = scanner.nextLine();

        if((item.equalsIgnoreCase(quitWord) || (item.equalsIgnoreCase(exitWord)))) {
            System.out.println("EXIT");
            return false;
        } else {
            if (checkIfNull(item) != null) {
                checkIfInputIsAValidItem(item);
                if (validInput) {
                    while(tracker) {
                        printPossibleDirectionsPlayerCanGo(item);
                        itemAndDirection = scanner.nextLine();
                        if (indicateHavingReachedEnd(itemAndDirection)) {
                            System.out.println("You have reached your final destination");
                            System.out.println("EXIT");
                            return false;
                        } else if (teleportPlayer(itemAndDirection)) {
                            System.out.println("You have reached your final destination through teleportation");
                            System.out.println("EXIT");
                            return false;
                        } else {
                            if (enableDirectionBasedOnInput(itemAndDirection)) {
                                printRoomDescriptionBasedOnDirection(inputDir);
                                validInput = true;
                                return true;
                            } else {
                                tracker = true;
                            }
                        }
                    }
                } else {
                    validInput = true;
                }
            }
        }
        return true;
    }


    /**
     * fights a monster
     * @return true if defeated the monster false otherwise
     */
    public boolean fightMonster() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(room.monster.description);
        for (int i = 0; i < room.monster.questions.length; i++) {
            System.out.println(room.monster.questions[i].name);

            answer = scanner.nextLine();
            if (!(answer.equalsIgnoreCase(room.monster.questions[i].answer))) {
                return false;
            }
        }
        return true;
    }


    /**
     * teleports a player to the end room if they answer the question right
     * @param input
     * @return
     */
    public boolean teleportPlayer(String input) {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 4) {
            System.out.println("Wrong input, try again");
            return false;
        }

        String inputtedDirection = splitInput[3].toLowerCase();
        Direction[] directions = room.getDirections();

        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().equalsIgnoreCase(inputtedDirection)) {
                if (directions[i].getRoom().equalsIgnoreCase("adventureland")) {
                    System.out.println("Look it's a fairy godmother, she can grant your wish of teleporting!");
                    System.out.println("You have one chance to answer this question right: What is the color of Cinderella's ball gown?");
                    Scanner scanner = new Scanner(System.in);
                    answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("blue")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }


    /**
     * enables or disables the direction a player can go based on their input
     * @param input
     * @return true or false
     */
    public boolean enableDirectionBasedOnInput(String input) {

        String[] splitInput = input.split(" ");
        if (splitInput.length != 4) {
            System.out.println("Wrong input, try again");
            return false;
        }

        if (room.monster != null) {
            boolean beatMonster = false;
            while (!beatMonster) {
                beatMonster = fightMonster();
            }
        }

        String playerItem = splitInput[1].toLowerCase();
        String inputtedDirection = splitInput[3].toLowerCase();

        Direction[] directions = room.getDirections();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getEnabled().equals("false") && directions[i].getValidKeyNames().length != 0) {
                if (directions[i].getValidKeyNames()[0].equalsIgnoreCase(playerItem) &&
                        directions[i].getDirectionName().equalsIgnoreCase(inputtedDirection)) {
                            inputDir = directions[i].getDirectionName();
                            return true;
                    }
            } else if (directions[i].getEnabled().equals("true")
                    && directions[i].getDirectionName().equalsIgnoreCase(inputtedDirection)){
                inputDir = directions[i].getDirectionName();
                return true;
            }
        }

        System.out.println("Try a different item or direction");
        return false;
    }


    /**
     * returns true or false based on if the user's inputted item and direction takes you to the ending room
     * @param input
     * @return true if it does not take you to the ending room so the game can continue
     * or false if it does take you to the ending room so it can end the game
     */
    public boolean indicateHavingReachedEnd(String input) {
        if (input == null) {
            return false;
        }
        String[] splitInput = input.split(" ");
        if (splitInput.length != 4) {
            return false;
        }
        String inputtedDirection = splitInput[3].toLowerCase();
        Direction[] directions = room.getDirections();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().equalsIgnoreCase(inputtedDirection)
                    && directions[i].getValidKeyNames().length != 0) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();
            }
        }
        if (currentRoom == null) {
            return false;
        }
        if (currentRoom.equals(endRoom)) {
            return true;
        }
        return false;
    }


    /**
     * if the user inputs a url it checks if its a valid url
     * if it is not a valid url it returns true to ask them for a valid again
     * if it is a valid url it returns false and starts this url's game world
     * @return true or false
     * @throws Exception
     */
    public boolean urlRunner() {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter url");
        url = scanner2.nextLine();
        parsingJson();
        // check if rooms are null and if they are its not a game world
        if (startRoom == null || endRoom == null) {
            System.out.println("This is not a valid adventure: ");
            return true;
        } else {
            return false;
        }
    }


    public static void main (String[] args) throws Exception {
        game.url = args[0];
        boolean firstLoop = true;
        while (firstLoop) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Do you want to change your JSON file to a new URL? (type yes or no)");
            String answer = scanner1.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                if (game.urlRunner()) {
                    firstLoop = true;
                } else {
                    firstLoop = false;
                }
            } else if (answer.equalsIgnoreCase("no")) {
                firstLoop = false;
            } else {
                throw new IllegalArgumentException();
            }
        }

        boolean loop = true;
        game.parsingJson();
        game.startGame();
        while (loop) {
            if (!(game.runGame())) {
                loop = false;
            }
        }
    }
}
