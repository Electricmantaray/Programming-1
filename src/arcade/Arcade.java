/*************************************************************************

 File: Arcade.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Represents a collection of games and customers
 manages customers accounts, games and transactions


 Data Structure reasoning:
 Using HashMaps, for O(1) retrieval
 of a customer or game using their ID
 rather than ArrayList O(n) for time efficiency
 as data collection scales

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

import customer.Customer;
import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;
import exceptions.InvalidGameIdException;

import java.util.*;

public class Arcade {

    // Member Variables //
    private final String name;
    private int revenue;
    private Map<String, ArcadeGame> games;
    private Map<String, Customer> customers;

    // Constructor //
    public Arcade(String name) {
        this.name = name;
        this.revenue = 0;
        this.games = new HashMap<>();
        this.customers = new HashMap<>();
    }

    // Functions //

    // ADD //
    // Allows system to add customer
    // Enforces uniqueness
    public void addCustomer(Customer customer) throws InvalidCustomerException {
         if (customers.containsKey(customer.getId())) {
         throw new InvalidCustomerException("Customer ID already exists.");
         }
        customers.put(customer.getId(), customer);
    }

    // Allows system to add game
    // Puts it within the hashmap
    // Enforces uniqueness
    public void addArcadeGame(ArcadeGame game) throws InvalidGameIdException {
        if (games.containsKey(game.getId())) {
            throw new InvalidGameIdException("Game ID already exists.");
        }
        games.put(game.getId(), game);
    }

    // GET //
    // Retrieves customer from the Hashmap
    public Customer getCustomer(String customerID) throws InvalidCustomerException {
        if (!customers.containsKey(customerID)) {
            throw new InvalidCustomerException("Customer ID doesn't exist.");
        }
        return customers.get(customerID);
    }

    // Retrieves game from the Hashmap
    public ArcadeGame getArcadeGame(String gameID) throws InvalidGameIdException {
        if (!games.containsKey(gameID)) {
            throw new InvalidGameIdException("Game ID doesn't exist.");
        }
        return games.get(gameID);
    }

    // Processes a transaction between a customer and
    // Their selected game
    public boolean processTransaction(String customerID, String gameID, boolean peak)
            throws InvalidCustomerException, InvalidGameIdException, AgeLimitException, InsufficientBalanceException {
        // Gets customer and game for transaction
        Customer customer = getCustomer(customerID);
        ArcadeGame game = getArcadeGame(gameID);

        // if no errors thrown, charges account, adding
        // cost to revenue
        int amount = customer.chargeAccount(game, peak);
        revenue += amount;
        return true;
    }

    // Searches customers to find the highest balance
    public Customer findRichestCustomer(){
        // Checks for customer objects before looping
        if (customers.isEmpty()) {
            return null;
        }

        // Replaces customer with richest customer until it checks all customers
        Customer richest = null;
        for (Customer customer : customers.values()) {
            if (richest == null || customer.getBalance() > richest.getBalance()){
                richest = customer;
            }
        }

        return richest;
    }

    // Finds the average price across all games
    public int getMedianGamePrice(){
        if (games.isEmpty()){
            // Checks for games objects before looping
            return 0;
        }

        // Place all prices into list
        List<Integer> priceList = new ArrayList<>();
        for (ArcadeGame game : games.values()){
            priceList.add(game.getPrice());
        }
        Collections.sort(priceList);

        // Finding the average (middle values)
        if (priceList.size() % 2 == 1){
            // if theres an exact value (odd size)
            return priceList.get(priceList.size()/2);
        } else {
            // if theres 2 middle values (even size)
            int lowerMid = priceList.get((priceList.size()/2)-1);
            int upperMid = priceList.get(priceList.size()/2);
            return (lowerMid + upperMid)/2;
        }

    }

    // Prints out generic liability statement
    public static void printCorporateJargon(){
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    // Returns the number of cabinet games,
    // active games and virtual games
    public int[] countArcadeGames(){
        // Counters
        int virtualrealityCounter = 0;
        int activeCounter = 0;
        int cabinetCounter = 0;

        // Checks which game, adds 1 to counter
        for (ArcadeGame game : games.values()){
            if (game instanceof VirtualRealityGame){
                virtualrealityCounter += 1;
            } else if (game instanceof ActiveGame){
                activeCounter += 1;
            } else if (game instanceof CabinetGame){
                cabinetCounter += 1;
            }
        }

        return new int[]{cabinetCounter, activeCounter, virtualrealityCounter};
    }


    // Accessors //

    public String getName() {
        return name;
    }

    public int getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return String.format(
                "Arcade name = %s, Revenue = £%.2f",
                name, revenue/100.00);
    }

    // Test harness
    public static void main(String[] args) {
        System.out.println("\nArcade Tests:");

        Arcade arcade = new Arcade("GameCo");

        Customer ct1 = new Customer("Test01", "Customer 1", 52,);
        Customer ct2 = new Customer("Test02", "Customer 2", 2400);
        Customer ct3 = new Customer("Test03", "Customer 3", 1800);







    }
}
