package com.example;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;

    public Layout(String givenStartingRoom, String givenEndingRoom, Room[] givenRooms) {
        startingRoom = givenStartingRoom;
        endingRoom = givenEndingRoom;
        rooms = givenRooms;
    }

    public void checkIfStartingOrEndingRoom(int position) {
        if (rooms[position].name.equals(startingRoom)) {
            System.out.println("Your journey begins here");
            System.out.print("");
        } else if (rooms[position].name.equals(endingRoom)) {
            System.out.println("You have reached your final destination");
            System.out.print("EXIT");
        }
    }
}
