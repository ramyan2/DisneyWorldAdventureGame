package com.example;

import com.example.Direction;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;

    public Room(String roomName, String roomDescription, Direction[] roomDirections) {
        name = roomName;
        description = roomDescription;
        directions = roomDirections;
    }
}
