/*************************************************************************

 File: ArcadeGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Abstract class

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import arcade.GameType;
import exceptions.InvalidGameIdException;


public abstract class ArcadeGame {
    // Member Variables
    private String id;
    private String name;
    private int price;


    // Constructor
    public ArcadeGame(String id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
        validateId(id);
    }

    // Protected method for validating game id
    protected void validateId(String id){
        if (id == null || id.length() != 10/*Check if alphanumeric 10*/){
            throw new InvalidGameIdException("Invalid game ID. Must be 10 alphanumeric characters.");
        }
    }

    public abstract int calculatePrice(boolean peak);



//    @Override
//    public String toString() {
//        return
//    }

    // Unit tests
//    public static void main(String[] args) {
//    }
}
