package com.example;

public class Direction {
    private String directionName;

    private String room;
    //if direction can be used or not, false not usable true usable
    private String enabled;

    private String[] validKeyNames;

    /**
     * gets name of the direction
     * @return directionName
     */
    public String getDirectionName() {
        return directionName;
    }

    /**
     * gets room the direction points to
     * @return room
     */
    public String getRoom() {
        return room;
    }

    /**
     * get enabled
     * @return
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * get the valid key names
     * @return
     */
    public String[] getValidKeyNames() {
        return validKeyNames;
    }
}
