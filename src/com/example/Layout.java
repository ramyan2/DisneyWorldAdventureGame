package com.example;

public class Layout {

    public String startingRoom;

    public String endingRoom;

    public Room[] rooms;

    public Player player;

    /**
     * gets starting room
     * @return startingroom
     */
    public String getStartingRoom() {
        return startingRoom;
    }

    /**
     * gets ending room
     * @return endingroom
     */
    public String getEndingRoom() {
        return endingRoom;
    }

    /**
     * gets array of rooms
     * @return rooms
     */
    public Room[] getRooms() {
        return rooms;
    }

    /**
     * gets player
     */
    public Player getPlayer() {
        return player;
    }
}
