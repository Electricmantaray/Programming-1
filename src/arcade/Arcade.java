/*************************************************************************

 File: Arcade.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Represents a collection of games and customers
 manages customers accounts, games and transactions
 utilises efficient data structures like HashMaps
 allows the system to add and retrieve customers and games.
 Has functionality such as finding the richest customer,
 the calculating median price of a game, and counting
 different types of arcade games


 Data Structure reasoning:
 Using HashMaps, for O(1) retrieval
 of a customer or game using their ID
 rather than ArrayList O(n) for time efficiency
 as data collection scales

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

import customer.Customer;
import customer.DiscountType;
import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;
import exceptions.InvalidGameIdException;

import java.util.*;

public class Arcade {

    // Member Variables //
    private final String name;
    private int revenue;
    private final Map<String, ArcadeGame> games;
    private final Map<String, Customer> customers;

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
    public Customer findRichestCustomer() {
        // Checks for customer objects before looping
        if (customers.isEmpty()) {
            return null;
        }

        // Replaces customer with richest customer until it checks all customers
        Customer richest = null;
        for (Customer customer : customers.values()) {
            if (richest == null || customer.getBalance() > richest.getBalance()) {
                richest = customer;
            }
        }

        return richest;
    }

    // Finds the average price across all games
    public int getMedianGamePrice() {
        if (games.isEmpty()) {
            // Checks for games objects before looping
            return 0;
        }

        // Place all prices into list
        List<Integer> priceList = new ArrayList<>();
        for (ArcadeGame game : games.values()) {
            priceList.add(game.getPrice());
        }
        Collections.sort(priceList);

        // Finding the average (middle values)
        if (priceList.size() % 2 == 1) {
            // if theres an exact value (odd size)
            return priceList.get(priceList.size() / 2);
        } else {
            // if theres 2 middle values (even size)
            int lowerMid = priceList.get((priceList.size() / 2) - 1);
            int upperMid = priceList.get(priceList.size() / 2);
            return (lowerMid + upperMid) / 2;
        }

    }

    // Prints out generic liability statement
    public static void printCorporateJargon() {
        System.out.println("GamesCo does not take responsibility for any " +
                "accidents or fits of rage that occur on the premises");
    }

    // Returns the number of cabinet games,
    // active games and virtual games
    public int[] countArcadeGames() {
        // Counters
        int virtualrealityCounter = 0;
        int activeCounter = 0;
        int cabinetCounter = 0;

        // Checks which game, adds 1 to counter
        for (ArcadeGame game : games.values()) {
            if (game instanceof VirtualRealityGame) {
                virtualrealityCounter += 1;
            } else if (game instanceof ActiveGame) {
                activeCounter += 1;
            } else if (game instanceof CabinetGame) {
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
                name, revenue / 100.00);
    }

    // Test harness
    public static void main(String[] args) {
        System.out.println("\nArcade Tests:");

        Arcade arcade = new Arcade("GameCo");

        // Adding and retrieving customer
        try {
            Customer c1 = new Customer("Test01", "Customer 1", 52, DiscountType.NONE);
            arcade.addCustomer(c1);
            Customer retrieved = arcade.getCustomer("Test01");
            System.out.println("Test 1 passed: " + retrieved);
        } catch (Exception e) {
            System.err.println("Test 1 failed: " + e.getMessage());
        }

        // Duplicate customer
        try {
            Customer c2 = new Customer("Test02", "hii", 22, DiscountType.NONE);
            Customer c3 = new Customer("Test02", "hii", 22, DiscountType.NONE);
            arcade.addCustomer(c2);
            arcade.addCustomer(c3);
            Customer retrieved = arcade.getCustomer("Test02");
            System.err.println("Test 2 passed: " + retrieved);
        } catch (InvalidCustomerException e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        // Adding and retrieving games
        try {
            ArcadeGame a1 = new CabinetGame("CBAcba1234", "Test cabinet game 1", 100, true);
            arcade.addArcadeGame(a1);
            ArcadeGame retrieved = arcade.getArcadeGame("CBAcba1234");
            System.out.println("Test 3 passed: " + retrieved);
        } catch (Exception e) {
            System.err.println("Test 3 failed: " + e.getMessage());
        }

        // Duplicate game
        try {
            ArcadeGame a2 = new CabinetGame("CB55555555", "Test cabinet game 2", 777, true);
            ArcadeGame a3 = new CabinetGame("CB55555555", "Test cabinet game 1", 555, true);
            arcade.addArcadeGame(a2);
            arcade.addArcadeGame(a3);
            ArcadeGame retrieved = arcade.getArcadeGame("CB55555555");
            System.err.println("Test 4 passed: " + retrieved);
        } catch (InvalidGameIdException e) {
            System.out.println("Test 4 failed: " + e.getMessage());
        }

        // Valid transaction
        try {
            ArcadeGame a4 = new CabinetGame("CB66666666", "Test cabinet game 5", 444, true);
            Customer c4 = new Customer("Test05", "hii", 22, DiscountType.NONE, 1000);

            arcade.addArcadeGame(a4);
            arcade.addCustomer(c4);

            boolean success = arcade.processTransaction("Test05", "CB66666666", true);
            if (success) {
                System.out.println("Test 5 passed");
            } else {
                System.err.println("Test 5 failed:");
            }
        } catch (Exception e) {
            System.err.println("Test 5 failed");
        }

        // Process transaction, invalid customer

        try {
            ArcadeGame a6 = new CabinetGame("CB88888888", "Test cabinet game 5", 222, true);
            Customer c6 = new Customer("failureeeee", "hii", 22, DiscountType.NONE, 1000);

            arcade.addArcadeGame(a6);
            arcade.addCustomer(c6);

            arcade.processTransaction("failureeeee", "CB88888888", true);
            System.err.println("Test 6 failed: Invalid customer");
        } catch (InvalidCustomerException e) {
            System.out.println("Test 6 passed: Caught invalid customer");
        } catch (Exception e) {
            System.err.println("Test 6 failed");
        }

        // Process transaction, invalid game

        try {
            ArcadeGame a7 = new CabinetGame("B8", "Test cabinet game 5", 777, true);
            Customer c7 = new Customer("Test07", "hii", 22, DiscountType.NONE, 1000);

            arcade.addArcadeGame(a7);
            arcade.addCustomer(c7);

            arcade.processTransaction("Test07", "B8", true);
            System.err.println("Test 6 failed: Invalid game");
        } catch (InvalidGameIdException e) {
            System.out.println("Test 6 passed: Caught invalid game");
        } catch (Exception e) {
            System.err.println("Test 6 failed");
        }

        // Extra methods

        // Find richest customer
        try {
            Customer richest = arcade.findRichestCustomer();
            System.out.println("Test 7 passed: " + richest);
        } catch (Exception e) {
            System.err.println("Task 7 failed " + e.getMessage());
        }

        // Median game price
        try {
            int average = arcade.getMedianGamePrice();
            System.out.println("Test 8 passed: " + average);
        } catch (Exception e) {
            System.err.println("Task 8 failed " + e.getMessage());
        }

        // count arcades
        try {
            int[] counts = arcade.countArcadeGames();
            System.out.printf("Test 9 passed: Cabinet = %d, Active = %d, VR = %d\n", counts[0], counts[1], counts[2]);
        } catch (Exception e) {
            System.err.println("Task 9 failed " + e.getMessage());
        }

        // Jargon
        Arcade.printCorporateJargon();

        // Revenue check
        try {
            int[] counts = arcade.countArcadeGames();
            System.out.println("Test 10 passed: current revenue = " + arcade.getRevenue());
        } catch (Exception e) {
            System.err.println("Task 10 failed " + e.getMessage());
        }
    }
}