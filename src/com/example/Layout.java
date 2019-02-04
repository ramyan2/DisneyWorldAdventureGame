package com.example;

public class Layout {

    public String startingRoom;
    public String endingRoom;
    public Room[] rooms;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }

    //public

    public int countRoom() {
        return rooms.length;
    }

}
