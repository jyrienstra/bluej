import java.util.Iterator;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
import java.util.*;
public class Game 
{

    private Parser parser;
    //private Room currentRoom;
    private Stack<Room> previousRooms;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
        previousRooms = new Stack<Room>(); //a stack containing the previousrooms
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExits(null, theater, lab, pub);
        theater.setExits(null, null, null, outside);
        pub.setExits(null, outside, null, null);
        lab.setExits(outside, office, null, null);
        office.setExits(null, null, null, lab);

        //Declare items
        Item Weightbooster = new Item("Weightbooster", "+5 Kilograms of weight your backpack can hold", 0);
        //add items in rooms
        outside.addItem(Weightbooster);

        player.setCurrentRoom(outside);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Print the current location and possible exits
     */
    private void printLocationInfo(){
        //Print info about the current room description
        System.out.println("You are " + player.getCurrentRoom().getDescription());

        //Print the exits
        System.out.print("Exits: ");
        player.getCurrentRoom().printExits();
        System.out.println();

        //Print the current items that are in the room
        System.out.print("Items: ");
        player.getCurrentRoom().printItems();
        System.out.println();

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord(); //The first word of the command
        String secondWord = command.getSecondWord(); //The second word of the command
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("eat")) {
            eat();
        }else if (commandWord.equals("look")) {
            look();    
        }else if (commandWord.equals("back")) {
            back(); 
        }else if (commandWord.equals("takeItem")){
            player.takeItem(secondWord); //take an item from the room
        }else if (commandWord.equals("dropItem")){
            player.dropItem(secondWord); //drop an item from the backpack
        }else if (commandWord.equals("drink")){
            player.drink(secondWord); //drink an item from the backpack
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.print( parser.showCommands() );
        System.out.println();
    }

    /** 
     * Go one room back
     */
    private void back()
    {
        if(!previousRooms.empty()){
            player.setCurrentRoom( previousRooms.peek() );//take the top value from the stack and make it the current room
            previousRooms.pop(); //remove the top object because we are currently in the previous room.
            printLocationInfo();
        }else{
            System.out.println("You can't go back because there is no previous location");
        }
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {

            previousRooms.push( player.getCurrentRoom() );
            player.setCurrentRoom(nextRoom); //the nextroom is now the currentroom

            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Look around
     * Print where we currently are and the exits
     */
    private void look(){
        // System.out.println(currentRoom.getDescription());
        printLocationInfo();
    }

    /**
     * Eat some food
     */
    private void eat(){
        System.out.println("You have eaten now and are not hungry anymore");
    }
}