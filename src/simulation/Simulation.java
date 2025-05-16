/*************************************************************************

 File: simulation.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Responsible for simulating the arcade, handles the
 files and inputs them into the appropriate classes,
 simulates a sequence of transactions, utilising the data
 from games and customers text document, achieves a range of
 actions such as adding customers, adding funds
 handles exceptions.
 allows for the bonus functionality, such as :
 findRichestCustomer
 getMedianGamePrice


 History: 04/05/2025 v 1.00

 *************************************************************************/
package simulation;

import arcade.*;

import customer.Customer;

import customer.DiscountType;
import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;
import exceptions.InvalidGameIdException;

import java.io.*;
import java.util.*;

import java.io.File;

public class Simulation {

    // File reading and simulation
    public static void main(String[] args) throws FileNotFoundException {
        File customers = new File("customers.txt");
        File games = new File("games.txt");
        File transactions = new File("transactions.txt");

        Arcade arcade = initialiseArcade("GameCo Arcade", games, customers);
        simulateFun(arcade, transactions);
    }


    // Initialise an instance of arcade
    public static Arcade initialiseArcade(String arcadeName, File gamesFile, File customerFile) throws FileNotFoundException {
        // Initialising Arcade
        Arcade arcade = new Arcade(arcadeName);

        // --- GAMES PARSING --- //
        // Inputs file and its separating symbol
        List<String[]> gameRows = readFileWithSeparators(gamesFile, "@");
        // loops through all game rooms dynamically assigning
        // Row values to their fields
        for (String[] gameRow : gameRows){
            if (gameRow.length == 0){
                continue;
            }

            try {

                String id = gameRow[0];
                String name = gameRow[1];

                String gameType = gameRow[2].toUpperCase();

                // gameType decides what additional fields get used
                switch (gameType) {
                    // Cabinet needs rewardpayout and price
                    case "CABINET":
                        int cPrice = Integer.parseInt(gameRow[3]);
                        boolean hasRewardPayout = Boolean.parseBoolean(gameRow[4]);

                        CabinetGame cabinetGame = new CabinetGame(id, name, cPrice, hasRewardPayout);
                        arcade.addArcadeGame(cabinetGame);
                        break;


                    // Active needs minimum age and price
                    case "ACTIVE":
                        int aPrice = Integer.parseInt(gameRow[3]);
                        int aMinAge = Integer.parseInt(gameRow[4]);

                        ActiveGame activeGame = new ActiveGame(id, name, aPrice, aMinAge);
                        arcade.addArcadeGame(activeGame);
                        break;


                    // VR needs equipment type, minimum age and price
                    case "VIRTUALREALITY":
                        int vrPrice = Integer.parseInt(gameRow[3]);
                        int vrMinAge = Integer.parseInt(gameRow[4]);

                        EquipmentType equipmentType = parseEquipmentType(gameRow[5]);

                        VirtualRealityGame virtualRealityGame = new VirtualRealityGame(id, name, vrPrice, vrMinAge, equipmentType);
                        arcade.addArcadeGame(virtualRealityGame);
                        break;
                }
            // Throws exceptions
            } catch (InvalidGameIdException e) {
                System.out.println("Invalid game ID");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid equipment type" + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error parsing game row: " + Arrays.toString(gameRow));
            }
        }


        // --- CUSTOMER PARSING --- //
        List<String[]> customerRows = readFileWithSeparators(customerFile, "#");
        for (String[] customerRow : customerRows){
            if (customerRow.length == 0){
                continue;
            }
            try {

                String id = customerRow[0];
                String name = customerRow[1];
                int balance = Integer.parseInt(customerRow[2]);
                int age = Integer.parseInt(customerRow[3]);
                // Initialises DiscountType
                DiscountType discountType = DiscountType.NONE;
                // Checks if there is a 5 cell, if so, checks if blank
                // blank => NONE, if not it checks for disocunt type
                if ( customerRow.length >=5 && !customerRow[4].isBlank()){
                    switch (customerRow[4]){
                        case "STAFF":
                            discountType = DiscountType.STAFF;
                            break;


                        case "STUDENT":
                            discountType = DiscountType.STUDENT;
                            break;

                    }
                }

                // Constructor to add customer
                Customer customer = new Customer(id, name, age, discountType, balance);
                arcade.addCustomer(customer);

                // Throws exceptions
            } catch (InvalidCustomerException e) {
                System.out.println("Invalid customer ID");
            } catch (Exception e) {
                System.err.println("Error parsing Customer row: " + Arrays.toString(customerRow));
            }
        }

        return arcade;
    }


    public static void simulateFun(Arcade arcade, File transactionFile) throws FileNotFoundException {

        // --- TRANSACTIONS PARSING --- //
        List<String[]> transactionRows = readFileWithSeparators(transactionFile, ",");
        for (String[] transactionRow : transactionRows){
            if (transactionRow.length == 0){
                continue;
            }
            // Simulation action
            String action = transactionRow[0].toUpperCase();


            try {
                // Goes through the 3 simulation actions
                // recording a summary as it executes
                switch (action){
                    case "NEW_CUSTOMER":{
                        if (transactionRow.length < 5){
                            System.out.println("Transactional failure: invalid format");
                            continue;
                        }


                        String id = transactionRow[1];
                        String name = transactionRow[2];
                        int balance;
                        int age;
                        DiscountType discountType = DiscountType.NONE;

                        String discountInput = transactionRow[3].trim().toUpperCase();
                        try{
                            if (transactionRow.length == 6) {
                                switch (discountInput){
                                    case "STAFF":{
                                        discountType = DiscountType.STAFF;
                                    }
                                    case "STUDENT": {
                                        discountType = DiscountType.STUDENT;
                                        break;
                                    }
                                    case "NONE": {
                                        discountType = DiscountType.NONE;
                                        break;
                                    }
                                    default:
                                        System.out.println("Invalid discount type");
                                        break;
                                }

                                balance = Integer.parseInt(transactionRow[4]);
                                age = Integer.parseInt(transactionRow[5]);

                            } else {
                                balance = Integer.parseInt(transactionRow[3]);
                                age = Integer.parseInt(transactionRow[4]);
                            }

                            Customer customer = new Customer(id, name, age, discountType, balance);
                            arcade.addCustomer(customer);

                            // Informative summary
                            System.out.printf("New Customer: ID = %s, Name: %s, Age =%d , Discount = %s, Balance = £%.2f\n",
                                    id, name, age, discountType.name(), balance/100.00
                            );

                        } catch (NumberFormatException e) {
                            System.out.println("Transaction failed: invalid format");
                        }
                        break;
                    }

                    case "ADD_FUNDS": {
                        String id = transactionRow[1];
                        int amount = Integer.parseInt(transactionRow[2]);

                        Customer customer = arcade.getCustomer(id);
                        customer.addFunds(amount);

                        // Informative summary
                        System.out.printf("Add Funds: ID = %s, Amount = £%.2f, Name = %s , Balance = £%.2f\n",
                                id, amount/100.00, customer.getName(), customer.getBalance()/100.00
                                );

                        break;
                    }

                    case "PLAY":{
                        String customerID = transactionRow[1];
                        String gameID = transactionRow[2];
                        boolean isPeak = transactionRow[3].equalsIgnoreCase("PEAK");

                        arcade.processTransaction(customerID,gameID,isPeak);

                        // Informative summary
                        System.out.printf("Process Transaction: Customer ID = %s, Game ID = %s, Peak = %B\n",
                                customerID, gameID, isPeak
                                );
                        break;
                    }

                    default:
                        System.out.printf("Invalid Transaction type: %s\n",
                                action
                                );
                }

            } catch (InvalidGameIdException e) {
                System.out.println("Transaction failed: game  not found" );
            } catch (InvalidCustomerException e) {
                System.out.println("Transaction failed: customer  not found");
            } catch (AgeLimitException e) {
                System.out.println("Transaction failed: age is below minimum for game");
            }  catch (InsufficientBalanceException e) {
                System.out.println("Transaction failed: lack of funds");
            } catch (Exception e) {
                System.out.println("Transaction failed: invalid:" + e.getMessage());
            }
        }

        // --- ARCADE FUNCTIONS --- //

        System.out.println("\n--- ARCADE FUNCTIONS ---");

        // Richest Customer //
        Customer richest = arcade.findRichestCustomer();

        if (richest == null){
            System.out.println("No Customers");
        } else {
            System.out.printf("Richest Customer: ID = %s, Name = %s, Balance = £%.2f\n",
                    richest.getId(), richest.getName(), richest.getBalance()/100.00
                    );
        }

        // Median Game Price //
        int medianPrice = arcade.getMedianGamePrice();
        System.out.printf("Median price of game: £%.2f\n",
                medianPrice/100.00
                );

        // Count of Each Type of Game //

        int[] counts = arcade.countArcadeGames();
        System.out.printf("Counts: Cabinet = %d, Active = %d, VR = %d\n", counts[0], counts[1], counts[2]);



        // Corporate jargon //
        Arcade.printCorporateJargon();


        // Total Revenue //
        System.out.printf("Current revenue = £%.2f",
                arcade.getRevenue()/100.00
        );


    }


    // Helper Functions //
    private static List<String[]> readFileWithSeparators(File file, String separator) throws FileNotFoundException {
        // Split file into Array list of rows
        List<String[]> rows = new ArrayList<>();
        // In case of exceptions
        try (Scanner scanner = new Scanner(file)) {
            // Iterated through all rows
            while (scanner.hasNextLine()){
                String row = scanner.nextLine().trim();

                // Skip empty rows
                if (!row.isEmpty()){
                    // Separates the elements
                    String[] cells = row.split(separator);
                    rows.add(cells);
                }
            }
        }
        return rows;
    }

    private static EquipmentType parseEquipmentType(String input){
        return EquipmentType.valueOf(input.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase());

    }



}


