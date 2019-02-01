public class Layout {
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;

    public Layout(String givenStartingRoom, String givenEndingRoom, Room[] givenRooms) {
        startingRoom = givenStartingRoom;
        endingRoom = givenEndingRoom;
        rooms = givenRooms;
    }
}
