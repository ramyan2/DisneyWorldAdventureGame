package com.example;

import com.example.Direction;


public class Room implements Output {
    public String name;
    private String description;
    private Direction[] directions;

    public Room(String roomName, String roomDescription, Direction[] roomDirections) {
        name = roomName;
        description = roomDescription;
        directions = roomDirections;
    }

    public void getDescription() {
        System.out.println(description);
        System.out.print("");
    }
}
