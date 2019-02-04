package com.example;

import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Scanner;


public class Adventure implements Output {
    public String currentRoom;
    public Layout parsedJson;
    public Room[] arrayRooms;
    public ArrayList<Room> roomsList;
    public String startRoom;
    public String endRoom;
    public static Adventure game = new Adventure();
    public Direction[] directions;
    public Direction currentDirection;

    private static final String readJson = Data.getFileContentsAsString("AdventuresJSON");


    public Layout parsingJson() {
        Gson gson = new Gson();
        parsedJson = gson.fromJson(readJson, Layout.class);
        arrayRooms = parsedJson.getRooms();
        roomsList = new ArrayList<>(Arrays.asList(arrayRooms));

        return parsedJson;
    }


    public void setCurrentRoomObject() {
        for (int i = 0; i < arrayRooms.length; i++) {
            if (arrayRooms[i].getName().equals(currentRoom)) {
                Room room = arrayRooms[i];
                directions = room.getDirections();
            }
        }
    }

    public String getDescription(){
        return currentRoom;
    }

    public void getRoomDescriptionBasedOnDirection(String inputtedDirection) {
        String actualDirection = inputtedDirection.toLowerCase();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].getDirectionName().toLowerCase().equals(actualDirection)) {
                currentDirection = directions[i];
            } else {
                System.out.println("accounts for null");
                currentDirection = directions[0];
            }
        }
        for(int i = 0; i < arrayRooms.length; i++) {
            if (arrayRooms[i].getName().equals(currentDirection.getRoom())) {
                System.out.println(arrayRooms[i].getDescription());
            }
        }

    }



    public void startGame() {
        startRoom = parsedJson.getStartingRoom();
        endRoom = parsedJson.getEndingRoom();
        currentRoom = startRoom;
        game.setCurrentRoomObject();

        System.out.println("Your journey begins here.");
        System.out.println(roomsList.get(0).getDescription());
        System.out.println("From here you can go: ");

        for (int i = 0; i < directions.length; i++) {
            System.out.println(directions[i].getDirectionName());
        }

    }

    public void indicateHavingReachedEnd() {
        if (currentRoom.equals(endRoom)) {
            System.out.println("You have reached your final destination");
            System.out.println("EXIT");
        }
    }



    public static void main (String[] args) {
        game.parsingJson();
        game.startGame();
        game.indicateHavingReachedEnd();
        Scanner scanner = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a direction: ");
        String direction = scanner.nextLine();
        game.getRoomDescriptionBasedOnDirection(direction);
    }


}
