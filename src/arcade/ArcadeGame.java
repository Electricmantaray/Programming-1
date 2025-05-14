/*************************************************************************

 File: ArcadeGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 ArcadeGame is an abstract superclass that defines the common properties
 shared by all arcade games in the system
 This class is extended by CabinetGame, ActiveGame and further down
 VirtualRealityGame, which overwrite the calculatePrice and add
 further conditions to validateId

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import exceptions.InvalidGameIdException;


public abstract class ArcadeGame {

    // Member Variables //
    private final String id;
    private final String name;
    private final int price;


    // Constructor //
    public ArcadeGame(String id, String name, int price) {
        validateId(id);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Functions //
    // Protected method for validating game id
    protected void validateId(String id) throws InvalidGameIdException{
        if (id == null || id.length() != 10 || !id.matches("[a-zA-Z0-9]{10}")){
            // Default Exception message for string:
            // Alphanumeric 10 characters
            throw new InvalidGameIdException("Invalid game ID: \nMust be 10 alphanumeric characters.");
        }
    }

    // Implemented by subclasses, Stored in pence
    // Calculate price based on peak or off peak hours
    public abstract int calculatePrice(boolean peak);

    // Accessor Methods //
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }

    // Overrides the toString method
    // Providing a formatted ArcadeGame representation
    @Override
    public String toString() {
        return String.format(
                "ID = %s, Name = %s, Price = %d",
                id, name, price
        );
    }
}
