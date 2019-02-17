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

    String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

    /**
     * parses the json file from the url
     * @return the parsed json
     * @throws Exception
     */
    public Layout parsingJson() throws Exception {
        Gson gson = new Gson();
        parsedJson = gson.fromJson(AdventuresJSON, Layout.class);

//        try {
//            URL obj = new URL(url);
//            InputStreamReader reader = new InputStreamReader(obj.openStream());
//            parsedJson = gson.fromJson(reader, Layout.class);
//
//        } catch(Exception e) {
//            System.out.println("bad url");
//        }
        arrayRooms = parsedJson.getRooms();
        startRoom = parsedJson.getStartingRoom();
        endRoom = parsedJson.getEndingRoom();

        return parsedJson;
    }

    /**
     * goes through the array of rooms in my json until it finds the current room
     * and sets it to a Room object and sets the possible directions you can go from this room to a directions array
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
            if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();

                System.out.println(setCurrentRoomObject().getDescription());
          //      printPossibleDirectionsBasedOnInput(inputtedDirection);
                break;
            }
        }
        return setCurrentRoomObject().getDescription();
    }


    /**
     * prints the directions you can possibly go from the room based
     * on the input and returns a string of these directions
     * @return string of possible directions
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
     * prints the room description based on the direction inputted by the user and returns a string of this description
     * @param inputtedItem
     * @return description of current room object
     */
    public String printDirectionsBasedOnItem(String inputtedItem) {
        if (inputtedItem == null) {
            return null;
        }
        String[] x = inputtedItem.split(" ");
        String actualItem = x[1].toLowerCase();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().toLowerCase().equals(actualItem)) {
                player.setItems(items);
                currentRoom = player.getItems()[i].getRoom();


                for (int j = 0; j < setCurrentRoomObject().getDirections().length; j++) {
                    System.out.println("From here you can go: " + setCurrentRoomObject().getDirections()[j].getDirectionName());
                }
                break;
            }
        }
        return setCurrentRoomObject().getDescription();
    }


    /**
     * helper method for checkIfInputIsAValidDirection that declares if the input is valid
     * @param tracker
     * @param equalsKeyWordIndicator
     * @param input
     * @return
     */
    public boolean declareInputIsValid2(boolean tracker, boolean equalsKeyWordIndicator, String input) {
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
                if (items[i].getName().toLowerCase().equals(actualItem)) {
                    tracker = true;
                }
            }
            if (!(declareInputIsValid2(tracker, x[0].toLowerCase().equals(keyword), inputtedItem))) {
                tracker = false;
            }
        } else if (x[0].toLowerCase().equals(keyword)) {
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
        if((item.toLowerCase().equals(quitWord.toLowerCase())) || (item.toLowerCase().equals(exitWord.toLowerCase()))) {
            System.out.println("EXIT");
            return false;
        } else {
            if (checkIfNull(item) != null) {
                checkIfInputIsAValidItem(item);
                if (validInput) {
                    while(tracker) {
                        printDirectionsBasedOnItem(item);
                        Scanner scanner2 = new Scanner(System.in);
                        String itemAndDirection = scanner2.nextLine();
                        if (indicateHavingReachedEnd(itemAndDirection)) {
                            System.out.println("You have reached your final destination");
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
     * fight ursula by answering the 5 questions asked
     * @return true or false based on if they defeated the villain
     */
    public boolean fightUrsula() {
        System.out.println("Oh no! Ursula has found you. In order to defeat her and continue the game you must answer 5 questions:");
        System.out.println("1) How many tentacles does ursula have?");
        Scanner scanner = new Scanner(System.in);
        String firstAnswer = scanner.nextLine();
        if (firstAnswer.toLowerCase().equals("eight")) {
            System.out.println("2) What is 2 + 3 equal to?");
            Scanner scanner2 = new Scanner(System.in);
            String secondAnswer = scanner2.nextLine();
            if (secondAnswer.toLowerCase().equals("five")) {
                System.out.println("3) What is the color of Ursula's skin?");
                Scanner scanner3 = new Scanner(System.in);
                String thirdAnswer = scanner3.nextLine();
                if (thirdAnswer.toLowerCase().equals("purple")) {
                    System.out.println("4) Which Disney princess does she hate?");
                    Scanner scanner4 = new Scanner(System.in);
                    String fourthAnswer = scanner4.nextLine();
                    if (fourthAnswer.toLowerCase().equals("ariel")) {
                        System.out.println("5) What did Ursula take from Ariel?");
                        Scanner scanner5 = new Scanner(System.in);
                        String fifthAnswer = scanner5.nextLine();
                        if (fifthAnswer.toLowerCase().equals("voice")) {
                            System.out.println("You have defeated Ursula, congratulations!!!");
                            return true;
                        }
                    }
                }
            }
        } else {
            System.out.println("Ursula has defeated you, try again!");
            return false;
        }
        return false;
    }


    /**
     * enables or disables the direction a player can go based on their input
     * @param input
     * @return true or false
     */
    public boolean enableDirectionBasedOnInput(String input) {
        boolean tracker = true;

        String[] splitInput = input.split(" ");
        if (splitInput.length != 4) {
            System.out.println("Wrong input, try again");
            return false;
        }

        String playerItem = splitInput[1].toLowerCase();
        String inputtedDirection = splitInput[3].toLowerCase();

        Direction[] directions = room.getDirections();
        for (int i = 0; i < directions.length; i++) {
            while(tracker) {
                if (directions[i].getRoom().toLowerCase().equals("libertysquare")) {
                    if (fightUrsula()) {
                        tracker = false;
                    } else {
                        tracker = true;
                    }
                } else {
                    tracker = false;
                }
            }
            if (directions[i].getEnabled().equals("false")) {
                if (directions[i].getValidKeyNames()[0].toLowerCase().equals(playerItem) && directions[i].getDirectionName().toLowerCase().equals(inputtedDirection)) {
                    inputDir = directions[i].getDirectionName();
                    return true;
                }
            } else if (directions[i].getEnabled().equals("true") && directions[i].getDirectionName().toLowerCase().equals(inputtedDirection)){
                inputDir = directions[i].getDirectionName();
                return true;
            }
        }

        System.out.println("Try a different item or direction");
        return false;
    }

    /**
     * returns true or false based on if the user's inputted direction takes you to the ending room
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
            if (directions[i].getDirectionName().toLowerCase().equals(inputtedDirection)) {
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
    public boolean urlRunner() throws Exception {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter url");
        String urlInput = scanner2.nextLine();
        url = urlInput;
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
        boolean firstLoop = true;
        while (firstLoop) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Do you want to change your JSON file to a new URL? (type yes or no)");
            String answer = scanner1.nextLine();
            if (answer.toLowerCase().equals("yes")) {
                if (game.urlRunner()) {
                    firstLoop = true;
                } else {
                    firstLoop = false;
                }
            } else if (answer.toLowerCase().equals("no")) {
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
