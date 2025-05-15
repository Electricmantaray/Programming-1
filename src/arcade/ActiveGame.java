/*************************************************************************

 File: ActiveGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Concrete subclass of ArcadeGame
 If Exception, throws InvalidGameIdException
 Provides functionality for games to set an age minimum limit
 validate id has a unique bonus condition of starting with 'A'


 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import exceptions.InvalidGameIdException;



public class ActiveGame extends ArcadeGame {

    // Member Variables //
    // Storing integer for the minimum age in years
    private final int minAge;
    // ID start of string condition
    private static final String idStringCondition = "A";

    // Constructor //
    public ActiveGame(String id, String name, int price, int minAge) throws InvalidGameIdException{
        super(id, name, price);
        validateId(id);
        this.minAge = minAge;
    }

    // Functions //

    // Validates id with new condition of
    // Start with 'A'
    @Override
    protected void validateId(String id) throws InvalidGameIdException {
        super.validateId(id);
        // New condition
        // exception message changes to specify id issue
        if(!id.startsWith(idStringCondition)) {
            throw new InvalidGameIdException("Invalid game ID: \nActiveGame must start with: " + idStringCondition);
        }
    }

    // Get price, changes depending on peak and minAge
    @Override
    public int calculatePrice(boolean peak) {
        int price = getPrice();
        if (peak){
            return price;
        } else {
            return (int) (Math.floor(price * 0.8));
        }
    }

    // Accessors //
    public int getMinAge(){
        return minAge;
    }

    // Overrides the toString method
    // Providing a formatted ActiveGame representation
    @Override
    public String toString() {
        return "\nActive Game: " +
                super.toString() +
                ", Minimum age = "
                + minAge;
    }

    // Test harness //
    public static void main(String[] args) {
        System.out.println("\nActiveGame Tests:");

        // Normal test - expected format
        try {
            ActiveGame t1 = new ActiveGame("A123456789", "Test active game 1", 400, 18);
            System.out.println("Test 1 passed: " + t1);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 1 failed: " + e.getMessage());
        }

        // Invalid ID (Doesn't start with a 'A')
        try {
            ActiveGame t2 = new ActiveGame("1111111111", "Test active game 2", 270, 16);
            System.out.println("Test 2 passed : " + t2);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 2 failed: " + e.getMessage());
        }

        // Peak price
        // Expected outcome: 777
        try {
            ActiveGame t3 = new ActiveGame("AAAAAAAAAA", "Test active game 3", 777, 21);
            int updatedPrice = t3.calculatePrice(true);
            System.out.println("Test 3 passed: " + t3 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 3 failed: " + e.getMessage());
        }

        // Off-peak price
        // Expected outcome: 355
        try {
            ActiveGame t4 = new ActiveGame("AHHHHHHHHH", "Test active game 4", 444, 13);
            int updatedPrice = t4.calculatePrice(false);
            System.out.println("Test 4 passed: " + t4 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 4 failed: " + e.getMessage());
        }
   }
}

