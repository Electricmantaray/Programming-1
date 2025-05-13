/*************************************************************************

 File: CabinetGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Concrete subclass of ArcadeGame
 If Exception, throws InvalidGameIdException
 Provides functionality for games that have a reward that pays out
 validate id has a unique bonus condition of starting with 'C'

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import exceptions.InvalidGameIdException;

public class CabinetGame extends ArcadeGame {

    // Member Variables //
    // Storing boolean if the game gives a reward
    private final boolean hasRewardPayout;

    // Constructor //
    public CabinetGame(String id, String name, int price, boolean hasRewardPayout) throws InvalidGameIdException{
        super(id, name, price);
        validateId(id);
        this.hasRewardPayout = hasRewardPayout;
    }

    // Functions //

    // Validates id with new condition of
    // Start with 'C'
    @Override
    protected void validateId(String id) throws InvalidGameIdException {
        super.validateId(id);
        // New condition
        if(!id.startsWith("C")) {
            throw new InvalidGameIdException("Invalid game ID: \nMust be 10 alphanumeric characters. \nCabinetGame must start with 'C'.");
        }
    }

    // Get price, changes depending on peak and reward payout
    @Override
    public int calculatePrice(boolean peak) {
        int price = getPrice();
        // Full price during peak
        if (peak){
            return price;
        } else {
            // Gives out reward, discount = 20%
            if (hasRewardPayout) {
                return (int) (Math.floor(price * 0.8));
            } else {
                // Does not give out reward, discount = 50%
                return (int) (Math.floor(price * 0.5));
            }
        }
    }

    // Accessors //
    public boolean hasRewardPayout(){
        return hasRewardPayout;
    }

    // Overrides the toString method
    // Providing a formatted CabinetGame representation
    @Override
    public String toString() {
        return "\nCabinet Game: " +
                super.toString() +
                ", Payout reward = "
                + hasRewardPayout;

    }

    // Test harness //
    public static void main(String[] args) {
        System.out.println("\nCabinetGame Tests:");

        // Normal test - expected format
        try {
            CabinetGame t1 = new CabinetGame("CBAcba1234", "Test cabinet game 1", 100, true);
            System.out.println("Test 1 passed: " + t1);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 1 failed: " + e.getMessage());
        }

        // Invalid ID (Doesn't start with a 'C')
        try {
            CabinetGame t2 = new CabinetGame("abcABC1234", "Test cabinet game 2", 250, true);
            System.out.println("Test 2 passed : " + t2);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 2 failed: " + e.getMessage());
        }

        // Peak price
        // Expected outcome: 213
        try {
            CabinetGame t3 = new CabinetGame("CCCCCCCCCC", "Test cabinet game 3", 213, true);
            int updatedPrice = t3.calculatePrice(true);
            System.out.println("Test 3 passed: " + t3 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 3 failed: " + e.getMessage());
        }

        // Off-peak price with reward
        // Expected outcome: 256
        try {
            CabinetGame t4 = new CabinetGame("C234567890", "Test cabinet game 4", 321, true);
            int updatedPrice = t4.calculatePrice(false);
            System.out.println("Test 4 passed: " + t4 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 4 failed: " + e.getMessage());
        }

        // Off-peak price without reward
        // Expected outcome: 61
        try {
            CabinetGame t5 = new CabinetGame("C987654321", "Test cabinet game 5", 123, false);
            int updatedPrice = t5.calculatePrice(false);
            System.out.println("Test 5 passed: " + t5 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 5 failed: " + e.getMessage());
        }
    }
}
