import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits; //hashmap containing a room with its exist
    private ArrayList<Item> items; //arraylist containing all the items in aroom

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        items = new ArrayList<Item>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.   
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            exits.put("north", north); //insert the room with its corresponding exit in the hashmap
        if(east != null)
            exits.put("east", east); //insert the room with its corresponding exit in the hashmap
        if(south != null)
            exits.put("south", south); //insert the room with its corresponding exit in the hashmap
        if(west != null)
            exits.put("west", west); //insert the room with its corresponding exit in the hashmap
    }

    /**
     * Get the current possible exits based on the location
     */
    public Room getExit(String direction){
        return exits.get(direction); //get the current exit based on the "key" input example north returns exit north
        
    }
    
   
    /**
     * Print all exits
     * Make a collection of the exits hashmap
     * Print every "value" exit
     */
    public void printExits(){
        for(String exit : exits.keySet() )
        {
            System.out.print(exit);
            System.out.print(" ");
        }
    }
    
   /**
    * Add an item to a room
    */
   public void addItem(Item item){
       items.add(item);
    }
   
   /**
    * Get all items in a room
    */
   public ArrayList<Item> getItems(){
       return items;
   }
   
   /**
     * Find an item object by name(string)
     * @param item The item that has to be searched
     * @return Item The first item that matches in the backpack
     * @return null If there are no matches
     */
    public Item getItemByName(String itemName){
        if(itemName!=null){
            for(Item item : items){
                if(item.getName().equals(itemName)){
                    return item;
                }
            }
        }
        return null;
    }
   
   /**
    * Print all items in a room
    */
   public void printItems(){
       for(Item test : items){
           System.out.println(test.getName()); 
        }
    }
   
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
}
