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

    public Item currentItem;

    public static String quitWord = "QUIT";

    public static String exitWord = "EXIT";

    public Room room;

    public String[] enablers;

    public String enabler;

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






//    /**
//     * helper method for checkIfInputIsAValidDirection that declares if the input is valid
//     * @param tracker
//     * @param equalsKeyWordIndicator
//     * @param input
//     * @return
//     */
//    public boolean declareInputIsValid(boolean tracker, boolean equalsKeyWordIndicator, String input) {
//        if ((tracker && !equalsKeyWordIndicator) || (!tracker && !equalsKeyWordIndicator)) {
//            System.out.println("I don't understand " + "'" + input + "'");
//            System.out.println(room.getDescription());
//            validInput = false;
//            return false;
//        } else if (!tracker && equalsKeyWordIndicator) {
//            System.out.println("I can't " + input);
//            System.out.println(room.getDescription());
//            validInput = false;
//            return false;
//        }
//        return true;
//    }


//    /**
//     * if the user inputs an invalid direction then it prints
//     * it cannot go that direction or it does not understand that input
//     * @param inputtedDirection
//     * @return tracker which indicates if the input is valid (true) or not valid(false)
//     */
//    public boolean checkIfInputIsAValidDirection(String inputtedDirection) {
//        if (inputtedDirection == null) {
//            return false;
//        }
//        String keyword = "grab";
//        boolean tracker = false;
//        String[] x = inputtedDirection.split(" ");
//
//        if (x.length == 2) {
//            String actualDirection = x[1].toLowerCase();
//            for (int i = 0; i < directions.length; i++) {
//                if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
//                    tracker = true;
//                }
//            }
//            if (!(declareInputIsValid(tracker, x[0].toLowerCase().equals(keyword), inputtedDirection))) {
//                tracker = false;
//            }
//        } else if (x[0].toLowerCase().equals(keyword)) {
//            System.out.println("I can't " + inputtedDirection);
//            System.out.println(room.getDescription());
//            validInput = false;
//            tracker = false;
//        } else {
//            System.out.println("I don't understand " + "'" + inputtedDirection + "'");
//            System.out.println(room.getDescription());
//            validInput = false;
//            tracker = false;
//        }
//        return tracker;
//    }


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


//    /**
//     * prints the directions you can possibly go from the room based
//     * on the input and returns a string of these directions
//     * @return string of possible directions
//     */
//    public String printPossibleItemsBasedOnInput() {
//        setCurrentRoomObject();
//        String itemsFromInput = "";
//        System.out.println("Here you can pick up: ");
//        for (int i = 0; i < items.length; i++) {
//            System.out.println(items[i].getName());
//        }
////        for (int i = 0; i < directions.length; i++) {
////            System.out.println(directions[i].getDirectionName());
////        }
//        for (int j = 0; j < items.length; j++) {
//            itemsFromInput += items[j].getName() + " ";
//        }
//        return itemsFromInput.trim();
//    }

//    /**
//     * prints the directions you can possibly go from the room based
//     * on the input and returns a string of these directions
//     * @return string of possible directions
//     */
//    public String printPossibleDirectionsBasedOnInput(String inputtedDirection) {
//        if (inputtedDirection == null) {
//            return null;
//        }
//        setCurrentRoomObject();
//
//        String directionsFromInput = "";
//        System.out.println("From here you can go: ");
//        for (int i = 0; i < directions.length; i++) {
//            System.out.println(directions[i].getDirectionName());
//        }
//        for (int j = 0; j < items.length; j++) {
//            directionsFromInput += items[j].getName() + " ";
//        }
//        return directionsFromInput.trim();
//    }


//    /**
//     * starts the game by setting the current room to the starting room
//     * and prints this room's description and directions you can go from this room
//     */
//    public void startGame() {
//        startRoom = parsedJson.getStartingRoom();
//        currentRoom = startRoom;
//        game.setCurrentRoomObject();
//
//        System.out.println("Your journey begins here.");
//        System.out.println(arrayRooms[0].getDescription());
//
//        printPossibleDirectionsBasedOnInput();
//    }

//--------------//



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
    public void startGame2() {
        startRoom = parsedJson.getStartingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here. ");
        System.out.println(arrayRooms[0].getDescription());

        printPossibleItemsBasedOnInput();


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
//        for (int i = 0; i < directions.length; i++) {
//            System.out.println(directions[i].getDirectionName());
//        }
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
    public boolean runGame2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an item: ");
        String item = scanner.nextLine();
        if((item.toLowerCase().equals(quitWord.toLowerCase())) || (item.toLowerCase().equals(exitWord.toLowerCase()))) {
            System.out.println("EXIT");
            return false;
//        } else if (game.indicateHavingReachedEnd(item)) {
//            System.out.println("You have reached your final destination");
//            System.out.println("EXIT");
//            return false;
        } else {
            if (checkIfNull(item) != null) {
                checkIfInputIsAValidItem(item);
                if (validInput) {
                    printDirectionsBasedOnItem(item);
                    Scanner scanner2 = new Scanner(System.in);
                    String itemAndDirection = scanner2.nextLine();

                   if (enableDirectionBasedOnInput(itemAndDirection)) {
                       printRoomDescriptionBasedOnDirection(inputDir);
                       validInput = true;
                       return true;
                   }
                } else {
                    validInput = true;
                }
            }
        }
        return true;
    }

//    public boolean checkItemIsValidKey() {
//        Direction[] directions = room.getDirections();
//        for (int i = 0; i < directions.length; i++) {
//            System.out.println(directions[i].getDirectionName());
//            System.out.println(directions[i].getRoom());
//            System.out.println(directions[i].getValidKeyNames()[i]);
//            System.out.println(directions[i].getEnabled());
//        }
//        return true;
//    }

    public boolean enableDirectionBasedOnInput(String input) {

        String[] splitInput = input.split(" ");
        String playerItem = splitInput[1].toLowerCase();
        String inputtedDirection = splitInput[3].toLowerCase();


        Direction[] directions = room.getDirections();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getEnabled().equals("true")) {
                return true;
            } else if (directions[i].getEnabled().equals("false")){
                if (directions[i].getValidKeyNames()[i].toLowerCase().equals(playerItem) && directions[i].getDirectionName().toLowerCase().equals(inputtedDirection)) {
                    inputDir = directions[i].getDirectionName();
                    return true;
                } else {
                    System.out.println("Try a different item or direction");
                    return false;
                }
            }
        }
        return false;
    }

//    public String enableDirectionBasedOnInput(String input) {
//        System.out.println("test");
//        String[] splitInput = input.split(" ");
//        String playerItem = splitInput[1].toLowerCase();
//        String inputtedDirection = splitInput[3].toLowerCase();
//
//        for (int i = 0; i < player.getItems().length; i++) {
//            if (player.getItems()[i].getName().equals(playerItem)) {
//                currentItem = player.getItems()[i];
//            }
//        }
//        for (int j = 0; j < directions.length; j++) {
//            if (directions[j].getDirectionName().equals(inputtedDirection)) {
//                System.out.println("test2");
//                currentDirection = directions[j];
//                currentRoom = currentDirection.getRoom();
//                System.out.println("test3");
//                System.out.println(setCurrentRoomObject().getDescription());
//            }
//        }
//        return setCurrentRoomObject().getDescription();
//    }


//    public
//
//    public boolean enableDisabledDirection() {
//
//    }
//--------//
    /**
     * returns true or false based on if the user's inputted direction takes you to the ending room
     * @param inputtedDirection
     * @return true if it does not take you to the ending room so the game can continue
     * or false if it does take you to the ending room so it can end the game
     */
//    public boolean indicateHavingReachedEnd(String inputtedDirection) {
//        if (inputtedDirection == null) {
//            return false;
//        }
//        String[] x = inputtedDirection.split(" ");
//        if (x.length == 2) {
//            String actualDirection = x[1].toLowerCase();
//            for (int i = 0; i < directions.length; i++) {
//                if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
//                    currentDirection = directions[i];
//                    currentRoom = currentDirection.getRoom();
//                }
//            }
//        }
//        if (currentRoom.equals(endRoom)) {
//            return true;
//        }
//        return false;
//    }

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

//
//    /**
//     * runs the game when user inputs a valid url or types no
//     * @return true if the game should continue
//     * and false if the user reached the final destination or quit
//     */
//    public boolean runGame() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter a direction: ");
//        String direction = scanner.nextLine();
//        if((direction.toLowerCase().equals(quitWord.toLowerCase())) || (direction.toLowerCase().equals(exitWord.toLowerCase()))) {
//            System.out.println("EXIT");
//            return false;
//        } else if (game.indicateHavingReachedEnd(direction)) {
//            System.out.println("You have reached your final destination");
//            System.out.println("EXIT");
//            return false;
//        } else {
//            if (checkIfNull(direction) != null) {
//                checkIfInputIsAValidDirection(direction);
//                if (validInput) {
//                    printRoomDescriptionBasedOnDirection(direction);
//                } else {
//                    validInput = true;
//                }
//            }
//        }
//        return true;
//    }

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
//        game.url =  "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
        game.parsingJson();
        game.startGame2();
        while (loop) {
            if (!(game.runGame2())) {
                loop = false;
            }
        }
    }
}
