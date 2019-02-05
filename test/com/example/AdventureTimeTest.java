package com.example;

import com.example.Adventure;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdventureTimeTest {

    private Adventure game = new Adventure();
    private Layout parsedJSon;
    private Room[] roomsList;


    @Before
    public void setUpGame() {
        parsedJSon = game.parsingJson();
        roomsList = parsedJSon.getRooms();
    }


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

//------Checks Invalid Inputs and Null Cases-------//

    @Test
    public void testWhenStartingInputDoesNotMatchAnyPossibleDirections() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[0].getDirections();
        game.room = game.arrayRooms[0];
        assertEquals(false,game.checkIfInputIsAValidDirection("go north"));
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
        assertEquals(false,game.checkIfInputIsAValidDirection("go down"));
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
    public void testRightOutputWhenUserInputsGoThenRandomString() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[1].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidDirection("go HOME!!"));
    }

    @Test
    public void testRightOutputWhenUserInputsOnlyARandomString() {
        game.currentRoom = "SiebelNorthHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[3].getDirections();
        game.room = game.arrayRooms[3];
        assertEquals(false,game.checkIfInputIsAValidDirection("gophers are tasty!!"));
    }

    @Test
    public void testRightOutputWhenUserInputsRandomCharacters() {
        game.currentRoom = "SiebelEntry";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[1].getDirections();
        game.room = game.arrayRooms[1];
        assertEquals(false,game.checkIfInputIsAValidDirection("sdf53w^$$*2   yuas55"));
    }

    @Test
    public void testRightOutputWhenUserInputsNothing() {
        game.currentRoom = "SiebelBasement";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[7].getDirections();
        game.room = game.arrayRooms[7];
        assertEquals(false,game.checkIfInputIsAValidDirection(""));
    }

    @Test
    public void returnRightOutputWhenUserInputIsNull() {
        game.currentRoom = "SiebelBasement";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[7].getDirections();
        game.room = game.arrayRooms[7];
        assertEquals(false,game.checkIfInputIsAValidDirection(""));
    }




    @Test
    public void indicateNotHavingReachedEndTest() {
        game.currentRoom = "MatthewsStreet";
        game.endRoom = "Siebel1314";
     //   game.directions = game.arrayRooms[roomsList.length - 1].getDirections();
        assertEquals(false, game.indicateHavingReachedEnd("east"));
    }

    @Test
    public void indicateHavingReachedEndTest() {
        game.currentRoom = "SiebelNorthHallway";
        game.endRoom = "Siebel1314";
        game.directions = game.arrayRooms[roomsList.length - 1].getDirections();

        assertEquals(true, game.indicateHavingReachedEnd("south"));
    }


    @Test
    public void returnTrueIfReachedEnd() {
        //assertEquals("", rAndM.getName());
    }








}