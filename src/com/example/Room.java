package com.example;



public class Room {
    public String name;
    public String description;
    public Direction[] directions;

    /**
     * gets room name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets rooms description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets array of directions
     * @return directions
     */
    public Direction[] getDirections() {
        return directions;
    }


}
