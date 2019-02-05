package com.example;

import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Scanner;


public class Adventure {
    public String currentRoom;
    public Layout parsedJson;
    public Room[] arrayRooms;
    public ArrayList<Room> roomsList;
    public String startRoom;
    public String endRoom;
    public static Adventure game = new Adventure();
    public Direction[] directions;
    public Direction currentDirection;
    public static String quitWord = "QUIT";
    public static String exitWord = "EXIT";
    public Room room;
    public boolean validInput = true;


    private static final String readJson = Data.getFileContentsAsString("AdventuresJSON");


    public Layout parsingJson() {
        Gson gson = new Gson();
        parsedJson = gson.fromJson(readJson, Layout.class);
        arrayRooms = parsedJson.getRooms();
        endRoom = parsedJson.getEndingRoom();
        roomsList = new ArrayList<>(Arrays.asList(arrayRooms));

        return parsedJson;
    }

    /**
     * goes through the array of rooms in my json until it finds the current room and sets it to a Room object and sets the possible directions you can go from this room to a directions array
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

    public String checkIfNull(String inputtedDirection) {
        if (inputtedDirection == null) {
            return null;
        }
        return inputtedDirection;
    }

    /**
     * if the user inputs an invalid direction then it prints it cannot go that direction or it does not understand that input
     * @param inputtedDirection
     */


    public boolean checkIfInputIsAValidDirection(String inputtedDirection) {
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
            if (tracker && !x[0].toLowerCase().equals(keyword)) {
                System.out.println("I don't understand " + "'" + inputtedDirection + "'");
                System.out.println(room.getDescription());
                validInput = false;
                tracker = false;
            } else if (!tracker && x[0].toLowerCase().equals(keyword)) {
                System.out.println("I can't " + inputtedDirection);
                System.out.println(room.getDescription());
                validInput = false;
                tracker = false;
            } else if (!tracker && !x[0].toLowerCase().equals(keyword)) {
                System.out.println("I don't understand " + "'" + inputtedDirection + "'");
                System.out.println(room.getDescription());
                validInput = false;
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
     * prints the room description based on the direction inputted by the user
     * @param inputtedDirection
     */

    public String printRoomDescriptionBasedOnDirection(String inputtedDirection) {
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
     * prints the directions you can possibly go from the room based on the input
     */
    public void printPossibleDirectionsBasedOnInput() {
        setCurrentRoomObject();
        System.out.println("From here you can go: ");
        for (int i = 0; i < directions.length; i++) {
            System.out.println(directions[i].getDirectionName());
        }
    }


    /**
     * starts the game by setting the current room to the starting room and prints this room's description and directions you can go from this room
     */
    public void startGame() {
        startRoom = parsedJson.getStartingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here.");
        System.out.println(roomsList.get(0).getDescription());

        printPossibleDirectionsBasedOnInput();
    }

    /**
     * returns true or false based on if the user's inputted direction takes you to the ending room
     * @param inputtedDirection
     * @return true if it does not take you to the ending room so the game can continue or false if it does take you to the ending room so it can end the game
     */
    public boolean indicateHavingReachedEnd(String inputtedDirection) {
        String[] x = inputtedDirection.split(" ");
        if (x.length == 2) {
            String actualDirection = x[1].toLowerCase();
            for (int i = 0; i < directions.length; i++) {
                if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                    currentDirection = directions[i];
                    currentRoom = currentDirection.getRoom();
                }
            }
            if (currentRoom.equals(endRoom)) {
                return true;
            }
        }
        return false;
    }


    public static void main (String[] args) {
        boolean loop = true;
        game.parsingJson();
        game.startGame();
        while (loop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a direction: ");
            String direction = scanner.nextLine();
            if((direction.toLowerCase().equals(quitWord.toLowerCase())) || (direction.toLowerCase().equals(exitWord.toLowerCase()))) {
                System.out.println("EXIT");
                loop = false;
            } else if (game.indicateHavingReachedEnd(direction)) {
                System.out.println("You have reached your final destination");
                System.out.println("EXIT");
                loop = false;
            } else {
                if (game.checkIfNull(direction) != null) {
                    game.checkIfInputIsAValidDirection(direction);
                    if (game.validInput) {
                        game.printRoomDescriptionBasedOnDirection(direction);
                    } else {
                        game.validInput = true;
                    }
                }
            }
        }
    }
}
