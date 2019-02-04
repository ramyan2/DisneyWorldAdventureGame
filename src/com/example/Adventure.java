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


    private static final String readJson = Data.getFileContentsAsString("AdventuresJSON");


    public Layout parsingJson() {
        Gson gson = new Gson();
        parsedJson = gson.fromJson(readJson, Layout.class);
        arrayRooms = parsedJson.getRooms();
        endRoom = parsedJson.getEndingRoom();
        roomsList = new ArrayList<>(Arrays.asList(arrayRooms));

        return parsedJson;
    }


    public void setCurrentRoomObject() {
        for (int i = 0; i < arrayRooms.length; i++) {
            if (arrayRooms[i].getName().equals(currentRoom)) {
                room = arrayRooms[i];
                directions = room.getDirections();
            }
        }
    }

    /**
     * gets the room description based on the direction inputted by the user
     * @param inputtedDirection
     */

    public void printRoomDescriptionBasedOnDirection(String inputtedDirection) {
        String actualDirection = inputtedDirection.toLowerCase();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();
                setCurrentRoomObject();

                System.out.println(room.getDescription());
                printRoomDirectionsBasedOnInput();
                break;
            }
        }
    }


    public void printRoomDirectionsBasedOnInput() {
        setCurrentRoomObject();
        System.out.println("From here you can go: ");
        for (int i = 0; i < directions.length; i++) {
            System.out.println(directions[i].getDirectionName());
        }
    }



    public void startGame() {
        startRoom = parsedJson.getStartingRoom();
        endRoom = parsedJson.getEndingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here.");
        System.out.println(roomsList.get(0).getDescription());

        printRoomDirectionsBasedOnInput();

    }

    public boolean indicateHavingReachedEnd(String inputtedDirection) {
        String actualDirection = inputtedDirection.toLowerCase();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                currentDirection = directions[i];
                currentRoom = currentDirection.getRoom();
            }
        }
        if (currentRoom.equals(endRoom)) {
            return false;
        }
        return true;
    }



    public static void main (String[] args) {
        boolean loop = true;
        game.parsingJson();
        game.startGame();
        while (loop) {
            Scanner scanner = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter a direction: ");
            String direction = scanner.nextLine();
            if((direction.toLowerCase().equals(quitWord.toLowerCase())) || (direction.toLowerCase().equals(exitWord.toLowerCase())) ||
                    !game.indicateHavingReachedEnd(direction)) {
                System.out.println("EXIT");
                loop = false;
            } else {
                game.printRoomDescriptionBasedOnDirection(direction);
            }
        }



    }


}
