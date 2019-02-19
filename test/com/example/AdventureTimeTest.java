package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class AdventureTimeTest {

    private Adventure game = new Adventure();
    private Layout parsedJSon;


    @Before
    public void setUpGame() {
        parsedJSon = game.parsingJson();
    }

    //------test JSON-------//

    @Test
    public void testparsedJson() {
        game.parsingJson();
        assertEquals("DisneyWorldEntrance",game.startRoom);
    }

    @Test
    public void testparsedJson2() {
        game.parsingJson();
        assertEquals("CinderellasCastle",game.endRoom);
    }

    //------tests setCurrentRoomObject method------//

    @Test
    public void returnsStartingRoomName() {
        game.currentRoom = "DisneyWorldEntrance";
        assertEquals("DisneyWorldEntrance",game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsStartingRoomDescription() {
        game.currentRoom = "DisneyWorldEntrance";
        assertEquals("You are at the Disney World Magical Kingdom entrance", game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsAnotherRoomName() {
        game.currentRoom = "Cafe";
        assertEquals("Cafe",game.setCurrentRoomObject().getName());
    }

    @Test
    public void returnsAnotherRoomDescription() {
        game.currentRoom = "LibertySquare";
        assertEquals("You are in Liberty Square.  You can see Cinderella's Castle",game.setCurrentRoomObject().getDescription());
    }

    @Test
    public void returnsEndingRoomName() {
        game.currentRoom = "CinderellasCastle";
        assertEquals("CinderellasCastle", game.setCurrentRoomObject().getName());
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

//--------Tests printRoomDescriptionBasedOnInputtedDirection method---------//

    @Test
    public void testPrintNextRoomDescriptionMethodForRoom() {
        game.currentRoom = "MainStreet";
        game.endRoom = "CinderellasCastle";
        game.directions = game.arrayRooms[1].getDirections();
        assertEquals("You are on MainStreet U.S.A. You can see the Cafe to the East and AdventureLand to the NorthWest and TomorrowLand to the NorthEast.",
                game.printRoomDescriptionBasedOnDirection("use ticket with south"));
    }

    @Test
    public void testPrintNextRoomDescriptionMethodForRoom2() {
        game.currentRoom = "TomorrowLand";
        game.endRoom = "CinderellasCastle";
        game.directions = game.arrayRooms[3].getDirections();
        assertEquals("You are in TomorrowLand.  You can see FantasyLand and Cinderella's Castle.",
                game.printRoomDescriptionBasedOnDirection("use sodabottle with north"));
    }

    @Test
    public void testPrintNextRoomDescriptionMethodForLastRoom() {
        game.currentRoom = "CinderellasCastle";
        game.endRoom = "CinderellasCastle";
        game.directions = game.arrayRooms[5].getDirections();
        assertEquals("You are in Cinderella's Castle. You can eat dinner with many princes and princesses.",
                game.printRoomDescriptionBasedOnDirection("use glassSLIppers with SOUth"));
    }

    //--------Tests printPossibleDirectionsBasedOnInputDirection method---------//

    @Test
    public void testPrintPossibleItemsFromStartingRoom() {
        game.currentRoom = "DisneyWorldEntrance";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[0].getItems();
        assertEquals("AdmissionsTicket", game.printPossibleItemsBasedOnInput());
    }

    @Test
    public void testPrintPossibleItemsFromRandomRoom() {
        game.currentRoom = "Cafe";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[2].getItems();
        assertEquals("SodaBottle", game.printPossibleItemsBasedOnInput());
    }

    @Test
    public void testPrintPossibleItemsForMoreThanOneOutputtedDirection() {
        game.currentRoom = "MainStreet";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[1].getItems();
        assertEquals("MickeysEars MagicCarpet SpaceShip", game.printPossibleItemsBasedOnInput());
    }

    //----test printDirectionsBasedOnItem method----//

    @Test
    public void testPrintPossibleDirectionsFromInput() {
        game.currentRoom = "MainStreet";
        game.setCurrentRoomObject();
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[1].getItems();
        assertEquals("EastNorthEastNorthWestSouth", game.printPossibleDirectionsPlayerCanGo("pickup mickeysears"));
    }

    @Test
    public void testPrintPossibleDirectionsFromInput2() {
        game.currentRoom = "LibertySquare";
        game.setCurrentRoomObject();
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[5].getItems();
        assertEquals("SouthEast", game.printPossibleDirectionsPlayerCanGo("pickup glassslippers"));
    }

    //--check if runs game properly-----//

    @Test
    public void testRunGameWhenTrue() {
        String data = "pickup admissionsticket" +
                "\n use admissionsticket with north";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        game.startRoom = "DisneyWorldEntrance";
        game.endRoom = "CinderellasCastle";
        game.items = game.arrayRooms[0].getItems();
        game.currentRoom = "MainStreet";
        game.setCurrentRoomObject();
        assertEquals(true, game.runGame());
    }

    //-------check enabler-----//


    //-----check if game has reached end properly----//

    @Test
    public void testWhenReachedEnd() {
        game.parsingJson();
        game.endRoom = "CinderellasCastle";
        game.currentRoom = "CinderellasCastle";
        game.room = game.arrayRooms[6];
        assertEquals(true, game.indicateHavingReachedEnd("use GLASsslippers with south"));
    }

    @Test
    public void testWhenNotReachedEnd() {
        game.parsingJson();
        game.endRoom = "CinderellasCastle";
        game.currentRoom = "MainStreet";
        game.room = game.arrayRooms[1];
        assertEquals(false, game.indicateHavingReachedEnd("use mickeysears with east"));
    }


    //-----checks good and bad URLs-----//

    //returns false if it is a valid url and true if it is not a valid game url
    @Test
    public void testGoodUrlScanner() {
        String data = "https://pastebin.com/raw/Vze8b20f";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(false, game.urlRunner());
    }

    //if user inputs a bad website it indicates its a bad url and defaults to disney url
    @Test
    public void testBadUrlScanner() {
        String data = "https://randomWebsite.com";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        System.out.println(game.startRoom);
        assertEquals("DisneyWorldEntrance", game.startRoom);
    }

}