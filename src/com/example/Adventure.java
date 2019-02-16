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

    public static String quitWord = "QUIT";

    public static String exitWord = "EXIT";

    public Room room;

    public boolean validInput = true;

    private static final String AdventuresJSON = Data.getFileContentsAsString("AdventuresJSON");

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
            }
        }
        return room;
    }


    /**
     * checks if the inputted direction is null
     * @param inputtedDirection
     * @return
     */
    public String checkIfNull(String inputtedDirection) {
        if (inputtedDirection == null) {
            return null;
        }
        return inputtedDirection;
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
            System.out.println(room.getDescription());
            validInput = false;
            return false;
        } else if (!tracker && equalsKeyWordIndicator) {
            System.out.println("I can't " + input);
            System.out.println(room.getDescription());
            validInput = false;
            return false;
        }
        return true;
    }


    /**
     * if the user inputs an invalid direction then it prints
     * it cannot go that direction or it does not understand that input
     * @param inputtedDirection
     * @return tracker which indicates if the input is valid (true) or not valid(false)
     */
    public boolean checkIfInputIsAValidDirection(String inputtedDirection) {
        if (inputtedDirection == null) {
            return false;
        }
        String keyword = "go";
        boolean tracker = false;
        String[] x = inputtedDirection.split(" ");

        if (x.length == 2) {
            String actualDirection = x[1].toLowerCase();
            for (int i = 0; i < directions.length; i++) {
                if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                    tracker = true;
                }
            }
            if (!(declareInputIsValid(tracker, x[0].toLowerCase().equals(keyword), inputtedDirection))) {
                tracker = false;
            }
        } else if (x[0].toLowerCase().equals(keyword)) {
            System.out.println("I can't " + inputtedDirection);
            System.out.println(room.getDescription());
            validInput = false;
            tracker = false;
        } else {
            System.out.println("I don't understand " + "'" + inputtedDirection + "'");
            System.out.println(room.getDescription());
            validInput = false;
            tracker = false;
        }
        return tracker;
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
        String[] x = inputtedDirection.split(" ");
        String actualDirection = x[1].toLowerCase();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();

                System.out.println(setCurrentRoomObject().getDescription());
                printPossibleDirectionsBasedOnInput();
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
    public String printPossibleDirectionsBasedOnInput() {
        setCurrentRoomObject();
        String directionsFromInput = "";
        System.out.println("From here you can go: ");
        for (int i = 0; i < directions.length; i++) {
            System.out.println(directions[i].getDirectionName());
        }
        for (int j = 0; j < directions.length; j++) {
            directionsFromInput += directions[j].getDirectionName() + " ";
        }
        return directionsFromInput.trim();
    }


    /**
     * starts the game by setting the current room to the starting room
     * and prints this room's description and directions you can go from this room
     */
    public void startGame() {
        startRoom = parsedJson.getStartingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here.");
        System.out.println(arrayRooms[0].getDescription());

        printPossibleDirectionsBasedOnInput();
    }


    /**
     * returns true or false based on if the user's inputted direction takes you to the ending room
     * @param inputtedDirection
     * @return true if it does not take you to the ending room so the game can continue
     * or false if it does take you to the ending room so it can end the game
     */
    public boolean indicateHavingReachedEnd(String inputtedDirection) {
        if (inputtedDirection == null) {
            return false;
        }
        String[] x = inputtedDirection.split(" ");
        if (x.length == 2) {
            String actualDirection = x[1].toLowerCase();
            for (int i = 0; i < directions.length; i++) {
                if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                    currentDirection = directions[i];
                    currentRoom = currentDirection.getRoom();
                }
            }
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


    /**
     * runs the game when user inputs a valid url or types no
     * @return true if the game should continue
     * and false if the user reached the final destination or quit
     */
    public boolean runGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a direction: ");
        String direction = scanner.nextLine();
        if((direction.toLowerCase().equals(quitWord.toLowerCase())) || (direction.toLowerCase().equals(exitWord.toLowerCase()))) {
            System.out.println("EXIT");
            return false;
        } else if (game.indicateHavingReachedEnd(direction)) {
            System.out.println("You have reached your final destination");
            System.out.println("EXIT");
            return false;
        } else {
            if (checkIfNull(direction) != null) {
                checkIfInputIsAValidDirection(direction);
                if (validInput) {
                    printRoomDescriptionBasedOnDirection(direction);
                } else {
                    validInput = true;
                }
            }
        }
        return true;
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
//        game.url =  "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
        game.parsingJson();
        game.startGame();
        while (loop) {
            if (!(game.runGame())) {
                loop = false;
            }
        }
    }
}
