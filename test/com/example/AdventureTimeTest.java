package com.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdventureTimeTest {

    private Adventure game = new Adventure();
    private Layout parsedJSon;


    @Before
    public void setUpGame() throws Exception {
        parsedJSon = game.parsingJson();
    }

    //------tests setCurrentRoomObject method------//

    @Test
    public void returnsStartingRoomName() {
        game.currentRoom = "MatthewsStreet";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsStartingRoomDescription() {
        game.currentRoom = "MatthewsStreet";
        assertEquals("You are on Matthews, outside the Siebel Center", game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsAnotherRoomName() {
        game.currentRoom = "AcmOffice";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsAnotherRoomDescription() {
        game.currentRoom = "AcmOffice";
        assertEquals("You are in the ACM office.  There are lots of friendly ACM people.",game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsEndingRoomName() {
        game.currentRoom = "Siebel1314";
        assertEquals(game.currentRoom,game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsEndingRoomDescription() {
        game.currentRoom = "Siebel1314";
        assertEquals("You are in Siebel 1314.  There are happy CS 126 students doing a code review.",game.setCurrentRoomObject().getDescription());
    }

//------Checks Invalid Inputs-------//

    @Test
    public void testWhenStartingInputDoesNotMatchAnyPossibleDirections() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(false,game.checkIfInputIsAValidDirection("Go noRth"));
    }

    @Test
    public void testWhenRandomInputDoesNotMatchAnyPossibleDirections() {
        game.currentRoom = "AcmOffice";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[2].getDirections();
        game.room = game.arrayRooms[2];
        assertEquals(false,game.checkIfInputIsAValidDirection("go north"));
    }

    @Test
    public void testWhenEndingInputDoesNotMatchAnyPossibleDirections() {
        game.currentRoom = "SiebelBasement";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[7].getDirections();
        game.room = game.arrayRooms[7];
        assertEquals(false,game.checkIfInputIsAValidDirection("go DOWN"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesOnlyTheDirectionName() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidDirection("south"));
    }

    @Test
    public void testWhenUserInputsWrongDirectionString() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(false,game.checkIfInputIsAValidDirection("goat east"));
    }

    @Test
    public void testWhenUserInputsGoThenRandomString() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[1].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidDirection("go HOME!!"));
    }

    @Test
    public void testWhenUserInputsOnlyARandomString() {
        game.currentRoom = "SiebelNorthHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[3].getDirections();
        game.room = game.arrayRooms[3];
        assertEquals(false,game.checkIfInputIsAValidDirection("gophers are tasty!!"));
    }

    @Test
    public void testWhenUserInputsRandomCharacters() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[1].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidDirection("sdf53w^$$*2   yuas55"));
    }

    @Test
    public void testWhenUserInputsNothing() {
        game.currentRoom = "SiebelBasement";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[7].getDirections();
        game.room = game.arrayRooms[7];
        assertEquals(false,game.checkIfInputIsAValidDirection(""));
    }

    //------Test null cases------//

    @Test
    public void returnRightOutputWhenUserInputIsNull() {
        assertEquals(null,game.checkIfNull(null));
    }

    @Test
    public void testWhenInputIsNull() {
        assertEquals(false,game.checkIfInputIsAValidDirection(null));
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
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(true,game.checkIfInputIsAValidDirection("go sOUth"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesInValidInput2() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(true,game.checkIfInputIsAValidDirection("GO east"));
    }

    @Test
    public void testInvalidInputMethodWhenUserTypesInValidInput3() {
        game.currentRoom = "SiebelNorthHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[3].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(true,game.checkIfInputIsAValidDirection("go nORthEAst"));
    }

//--------Tests printRoomDescriptionBasedOnDirection method---------//

    @Test
    public void testPrintNextRoomDescriptionMethodForStartingRoom() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        assertEquals("You are in the west entry of Siebel Center.  You can see the elevator, the ACM office, and hallways to the north and east.",
                game.printRoomDescriptionBasedOnDirection("go east"));
    }

    @Test
    public void testPrintNextRoomDescriptionMethodForRandomRoom() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[1].getDirections();
        assertEquals("You are in the ACM office.  There are lots of friendly ACM people.", game.printRoomDescriptionBasedOnDirection("go northeast"));
    }

    @Test
    public void testPrintNextRoomDescriptionMethodForLastRoom() {
        game.currentRoom = "SiebelEastHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        assertEquals("You are in Siebel 1314.  There are happy CS 126 students doing a code review.", game.printRoomDescriptionBasedOnDirection("go south"));
    }

    //--------Tests printPossibleDirectionsBasedOnInputDirection method---------//

    @Test
    public void testPrintPossibleDirectionsFromStartingRoom() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        assertEquals("East", game.printPossibleDirectionsBasedOnInput());
    }

    @Test
    public void testPrintPossibleDirectionsFromRandomRoom() {
        game.currentRoom = "AcmOffice";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[2].getDirections();
        assertEquals("South", game.printPossibleDirectionsBasedOnInput());
    }

    @Test
    public void testPrintPossibleDirectionsForMoreThanOneOutputtedDirection() {
        game.currentRoom = "SiebelEastHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        assertEquals("West South Down", game.printPossibleDirectionsBasedOnInput());
    }

    //------Test Cases for indicateHavingReachedEnd method-------//

    @Test
    public void testWhenReachedEnd() {
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        game.currentDirection = game.directions[1];
        game.currentRoom = game.currentDirection.getRoom();
        System.out.println(game.currentRoom);
        assertEquals(true, game.indicateHavingReachedEnd("SoUTh"));
    }

    @Test
    public void testWhenNotReachedEnd() {
        game.currentRoom = "SiebelEastHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[5].getDirections();
        game.currentDirection = game.directions[0];
        assertEquals(false, game.indicateHavingReachedEnd("WEST"));
    }
}