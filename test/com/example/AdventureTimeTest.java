package com.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdventureTimeTest {

    private Adventure game = new Adventure();
    private Layout parsedJSon;


    @Before
    public void setUpGame() {
        parsedJSon = game.parsingJson();
    }

    //test JSON


    //------tests setCurrentRoomObject method------//

    @Test
    public void returnsStartingRoomName() {
        game.currentRoom = "DisneyWorldEntrance";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsStartingRoomDescription() {
        game.currentRoom = "DisneyWorldEntrance";
        assertEquals("You are at the Disney World Magical Kingdom entrance", game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsAnotherRoomName() {
        game.currentRoom = "Cafe";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsAnotherRoomDescription() {
        game.currentRoom = "LibertySquare";
        assertEquals("You are in Liberty Square.  You can see Cinderella's Castle",game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsEndingRoomName() {
        game.currentRoom = "CinderellasCastle";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsEndingRoomDescription() {
        game.currentRoom = "CinderellasCastle";
        assertEquals("You are in Cinderella's Castle. You can eat dinner with many princes and princesses.",game.setCurrentRoomObject().getDescription());
    }

//------Checks Invalid Inputs-------//

    @Test
    public void testWhenStartingInputDoesNotMatchAnyPossibleItems() {
        game.currentRoom = "DisneyWorldEntrance";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[0].getItems();
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(false,game.checkIfInputIsAValidItem("piCKup robot"));
    }

    @Test
    public void testWhenRandomInputDoesNotMatchAnyPossibleItems() {
        game.currentRoom = "TomorrowLand";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[3].getItems();
        game.directions = game.arrayRooms[3].getDirections();
        game.room = game.arrayRooms[3];
        assertEquals(false,game.checkIfInputIsAValidItem("piCkuP chips"));
    }

    @Test
    public void testWhenEndingInputDoesNotMatchAnyPossibleItems() {
        game.currentRoom = "LibertySquare";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[5].getItems();
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[5];
        assertEquals(false,game.checkIfInputIsAValidItem("PICKUP slippers"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesOnlyTheItemName() {
        game.currentRoom = "LibertySquare";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[5].getItems();
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[5];
        assertEquals(false,game.checkIfInputIsAValidItem("glassslippers"));
    }

    @Test
    public void testWhenUserInputsWrongItemString() {
        game.currentRoom = "DisneyWorldEntrance";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[0].getItems();
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(false,game.checkIfInputIsAValidItem("pickedup admisSionstiCKet"));
    }

    @Test
    public void testWhenUserInputsPickupThenRandomString() {
        game.currentRoom = "MainStreet";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[1].getItems();
        game.directions = game.arrayRooms[1].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidItem("pickup HOME!!"));
    }

    @Test
    public void testWhenUserInputsOnlyARandomString() {
        game.currentRoom = "TomorrowLand";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[3].getItems();
        game.directions = game.arrayRooms[3].getDirections();
        game.room = game.arrayRooms[3];
        assertEquals(false,game.checkIfInputIsAValidItem("picking up things are nice!!"));
    }

    @Test
    public void testWhenUserInputsRandomCharacters() {
        game.currentRoom = "FantasyLand";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[4].getItems();
        game.directions = game.arrayRooms[4].getDirections();
        game.room = game.arrayRooms[4];
        assertEquals(false,game.checkIfInputIsAValidItem("sdf53w^$$*2   yuas55"));
    }

    @Test
    public void testWhenUserInputsNothing() {
        game.currentRoom = "LibertySquare";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[5].getItems();
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[5];
        assertEquals(false,game.checkIfInputIsAValidItem(""));
    }

    //------Test null cases------//

    @Test
    public void returnRightOutputWhenUserInputIsNull() {
        assertEquals(null,game.checkIfNull(null));
    }

    @Test
    public void testWhenInputIsNull() {
        assertEquals(false,game.checkIfInputIsAValidItem(null));
    }

    @Test
    public void testWhenInputIsNullForPrintDescriptionMethod() {
        assertEquals(null,game.printRoomDescriptionBasedOnDirection(null));
    }

    @Test
    public void testWhenInputIsNullForHavingReachedEndMethod() {
        assertEquals(false,game.indicateHavingReachedEnd(null));
    }

    //-----Test InvalidInputMethod for valid cases------//

    @Test
    public void testInvalidInputMethodWhenUserTypesInValidInput() {
        game.currentRoom = "DisneyWorldEntrance";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[0].getItems();
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(true,game.checkIfInputIsAValidItem("pickUP admIsSionsticKET"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesInValidInput2() {
        game.currentRoom = "LibertySquare";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[5].getItems();
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[5];
        assertEquals(true,game.checkIfInputIsAValidItem("piCKup glassSLIPPERS"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesInValidInput3() {
        game.currentRoom = "AdventureLand";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[6].getItems();
        game.directions = game.arrayRooms[6].getDirections();
        game.room = game.arrayRooms[6];
        assertEquals(true,game.checkIfInputIsAValidItem("pickup eyePATch"));
    }

//--------Tests printRoomDescriptionBasedOnDirection method---------//
    //start//

//    @Test
//    public void testPrintNextRoomDescriptionMethodForRoom() {
//        game.currentRoom = "MainStreet";
//        game.endRoom = "CinderellasCastle";
//        game.directions = game.arrayRooms[1].getDirections();
//        assertEquals("You are on MainStreet U.S.A. You can see the Cafe to the East and AdventureLand to the NorthWest and TomorrowLand to the NorthEast.",
//                game.printRoomDescriptionBasedOnDirection("use ticket with south"));
//    }
//
//    @Test
//    public void testPrintNextRoomDescriptionMethodForRoom2() {
//        game.currentRoom = "TomorrowLand";
//        game.endRoom = "CinderellasCastle";
//        game.directions = game.arrayRooms[3].getDirections();
//        assertEquals("You are in TomorrowLand.  You can see FantasyLand and Cinderella's Castle.",
//                game.printRoomDescriptionBasedOnDirection("go northeast"));
//    }
//
//    @Test
//    public void testPrintNextRoomDescriptionMethodForLastRoom() {
//        game.currentRoom = "SiebelEastHallway";
//        game.endRoom = "Siebel1314";
//        game.directions = game.arrayRooms[5].getDirections();
//        assertEquals("You are in Siebel 1314.  There are happy CS 126 students doing a code review.",
//                game.printRoomDescriptionBasedOnDirection("go south"));
//    }

    //--------Tests printPossibleDirectionsBasedOnInputDirection method---------//
//
//    @Test
//    public void testPrintPossibleDirectionsFromStartingRoom() {
//        game.currentRoom = "MatthewsStreet";
//        game.endRoom = "Siebel1314";
//        game.directions = game.arrayRooms[0].getDirections();
//        assertEquals("East", game.printPossibleDirectionsBasedOnInput());
//    }
//
//    @Test
//    public void testPrintPossibleDirectionsFromRandomRoom() {
//        game.currentRoom = "AcmOffice";
//        game.endRoom = "Siebel1314";
//        game.directions = game.arrayRooms[2].getDirections();
//        assertEquals("South", game.printPossibleDirectionsBasedOnInput());
//    }
//
//    @Test
//    public void testPrintPossibleDirectionsForMoreThanOneOutputtedDirection() {
//        game.currentRoom = "SiebelEastHallway";
//        game.endRoom = "Siebel1314";
//        game.directions = game.arrayRooms[5].getDirections();
//        assertEquals("West South Down", game.printPossibleDirectionsBasedOnInput());
//    }

    //------Test Cases for indicateHavingReachedEnd method-------//

//    @Test
//    public void testWhenReachedEnd() {
//        game.endRoom = "CinderellasCastle";
//        game.directions = game.arrayRooms[5].getDirections();
//        game.currentDirection = game.directions[1];
//        game.currentRoom = game.currentDirection.getRoom();
//        game.setCurrentRoomObject();
//        System.out.println(game.currentRoom);
//        assertEquals(true, game.indicateHavingReachedEnd("use glassslippers with south"));
//    }
//
//    @Test
//    public void testWhenNotReachedEnd() {
//        game.currentRoom = "SiebelEastHallway";
//        game.endRoom = "Siebel1314";
//        game.directions = game.arrayRooms[5].getDirections();
//        game.currentDirection = game.directions[0];
//        assertEquals(false, game.indicateHavingReachedEnd("WEST"));
//    }


    //-----check url------//
}