/*************************************************************************

 File: Customer.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 Models an individual customer account, each
 customer has a unique ID, name and balance
 representing their funds for playing arcade games
 utilises an overdraft for students allowing their
 balance to go to -500.

 History: 04/05/2025 v 1.00

 *************************************************************************/
package customer;

import arcade.*;

import customer.DiscountType;

import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;

public class Customer {

    // Member Variables //
    private final String id;
    private final String name;
    private final int age;
    private final DiscountType discountType;
    private int balance;

    // Constructors //
    public Customer(String id, String name, int age, DiscountType discountType) throws InvalidCustomerException{
        validateId(id);
        this.id = id;
        this.name = name;
        this.age = age;
        this.discountType = discountType;
        // Initialise as 0
        this.balance = 0;
    }

    public Customer(String id, String name, int age, DiscountType discountType, int balance) throws InvalidCustomerException{
        validateId(id);
        // Specified balance equals to or greater than 0
        if (balance < 0){
            throw new InvalidCustomerException();
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.discountType = discountType;
        this.balance = balance;
    }

    // Functions //

    // Validates if ID is 6 alphanumeric characters
    private void validateId(String id) throws InvalidCustomerException {
        if (id == null || id.length() != 6 || !id.matches("[a-zA-Z0-9]{6}")){
            throw new InvalidCustomerException("Invalid customer ID: \nMust be 6 alphanumeric characters.");
        }
    }

    // Adds positive funds to balance in pence
    public void addFunds(int amount){
        if (amount > 0){
            balance += amount;
        }
    }

    // Charges funds per game play in pence
    public int chargeAccount(ArcadeGame arcadeGame, boolean peak) throws InsufficientBalanceException, AgeLimitException{
        // Checks if activeGame or a subclass
        if (arcadeGame instanceof ActiveGame activeGame) {
            // Age check
            if (this.age < activeGame.getMinAge()) {
                throw new AgeLimitException("Customer is not old enough to play game.");
            }
        }

        // Price has local discounts depending on if peak or not
        int price = arcadeGame.calculatePrice(peak);
        // initialise overdraft
        int overdraft = 0;

        // Additional discount
        // only if off-peak
        if (!peak){
            // Replaces price with discounted
            switch(discountType) {
                // 10% discount
                case STAFF -> price = (int) Math.floor(price * 0.9);
                // 5% discount
                case STUDENT -> {
                    price = (int) Math.floor(price * 0.95);
                    overdraft = -500;
                }
                // 0% discount
                case NONE -> {}
            }
        // Adds overdraft regardless of if peak
        } else {
            if (discountType == DiscountType.STUDENT){
                overdraft = -500;
            }
        }

        // Checking balance
        // If balance after game less than 0/-500
        if ((this.balance - price) < overdraft){
            throw new InsufficientBalanceException("Insufficient funds to play game.");
        }

        // Deducts cost of game from balance
        this.balance -= price;
        return price;
    }

    // Accessors //

    public String getId() {return id;}

    public String getName() {return name;}

    public int getAge() {return age;}

    public DiscountType getDiscountType() {return discountType;}

    public int getBalance() {return balance;}


    @Override
    public String toString() {
        return String.format(
                "CustomerID = %s, Name = %s, Age = %d, Discount Type = %s, Balance = %.2f",
                id, name, age, discountType.name(), balance/100.00
        );
    }

    // Test harness
    public static void main(String[] args) {
        System.out.println("\nCustomer Tests:");


        // Normal test - expected format

        try{
            Customer c1 = new Customer("A00001", "Customer 1", 27, DiscountType.STAFF);
            Customer c2 = new Customer("A00002", "Customer 2", 22, DiscountType.STUDENT, 2245);
            System.out.println("Test 1 passed: " + c1 + "\n" + c2);
        } catch (Exception e) {
            System.err.println("Test 1 failed: " + e.getMessage());
        }

        // Invalid Customer ID

        try {
            Customer c3 = new Customer("321", "Customer 3", 99, DiscountType.NONE);
            System.err.println("Test 2 passed : " + c3);
        } catch (InvalidCustomerException e) {
            System.out.println("Test 2 failed: " + e.getMessage());
        }

        // Invalid Balance

        try {
            Customer c4 = new Customer("A00003", "Customer 4", 25, DiscountType.STUDENT, -100);
            System.err.println("Test 3 passed : " + c4);
        } catch (Exception e) {
            System.out.println("Test 3 failed: " + e.getMessage());
        }

        // Off-peak discounts

        try {
            CabinetGame c5 = new CabinetGame("CBAcba1234", "Test cabinet game 1", 100, true);

            Customer STAFF = new Customer("Test01", "Customer 1", 25, DiscountType.STAFF, 1000);
            Customer STUDENT = new Customer("Test02", "Customer 2", 27, DiscountType.STUDENT, 800);
            Customer NONE = new Customer("Test03", "Customer 3", 31, DiscountType.NONE, 400);



            System.out.printf("STAFF before = £%.2f\n", STAFF.getBalance()/100.00);
            STAFF.chargeAccount(c5, false);
            System.out.printf("STAFF after = £%.2f\n", STAFF.getBalance()/100.00);

            System.out.printf("STUDENT before = £%.2f\n", STUDENT.getBalance()/100.00);
            STUDENT.chargeAccount(c5, false);
            System.out.printf("STUDENT after = £%.2f\n", STUDENT.getBalance()/100.00);

            System.out.printf("NONE before = £%.2f\n", NONE.getBalance()/100.00);
            NONE.chargeAccount(c5, false);
            System.out.printf("NONE after = £%.2f\n", NONE.getBalance()/100.00);

        } catch (Exception e) {
            System.err.println("Test 4 failed: " + e.getMessage());
        }

        // Peak

        try {
            CabinetGame c6 = new CabinetGame("CBBBBBBBBB", "Test cabinet game 2", 50, true);

            Customer NONE1 = new Customer("Test04", "Customer 4", 22, DiscountType.NONE, 555);

            System.out.printf("NONE1 before = £%.2f\n", NONE1.getBalance()/100.00);
            NONE1.chargeAccount(c6, true);
            System.out.printf("NONE1 after = £%.2f\n", NONE1.getBalance()/100.00);

        } catch (Exception e) {
            System.err.println("Test 5 failed: " + e.getMessage());
        }


        // Age Restriction
        try {
            VirtualRealityGame virtualRealityGame = new VirtualRealityGame("AV87654321", "Test virtual reality game 1", 465, 17, EquipmentType.HEADSET_ONLY);

            Customer NONE = new Customer("Test05", "Customer 5", 6, DiscountType.NONE, 5555);
            NONE.chargeAccount((virtualRealityGame), false);
        } catch (AgeLimitException e) {
            System.out.println("Correctly blocked underage customer " + e);

        } catch (Exception e) {
            System.err.println("Test 6 failed.");
        }

        // Overdraft
        try {
            CabinetGame c7 = new CabinetGame("CBBBBBBBBC", "Test Cabinet game 3", 222,false);

            Customer STUDENT1 = new Customer("Test06", "Customer 6", 65, DiscountType.STUDENT, 100);
            STUDENT1.chargeAccount(c7, false);
            System.out.printf("STUDENT1 balance = £%.2f\n", STUDENT1.getBalance()/100.00);

        } catch (Exception e) {
            System.err.println("Test 7 failed. " + e.getMessage());
        }

        try {
            ArcadeGame c8 = new CabinetGame("CBBBBBBBBD", "Test Cabinet game 4", 300,false);

            Customer STUDENT2 = new Customer("Test07", "Customer 7", 34, DiscountType.STUDENT);
            STUDENT2.chargeAccount(c8, true);
            STUDENT2.chargeAccount(c8, true);
            System.out.printf("STUDENT2 balance = £%.2f\n", STUDENT2.getBalance()/100.00);
            System.err.println("Test 7 failed: Should throw InsufficientBalanceException");
        } catch (InsufficientBalanceException e) {
            System.out.println("Test 7 passed.");
        } catch (Exception e) {
            System.err.println("Test 7 failed");
        }

        try {
            Customer NONE3 = new Customer("Test08", "Customer 8", 55, DiscountType.NONE);
            System.out.printf("NONE3 before = £%.2f\n", NONE3.getBalance()/100.00);
            NONE3.addFunds(1560);
            System.out.printf("NONE3 after = £%.2f\n", NONE3.getBalance()/100.00);
        } catch (Exception e) {
            System.err.println("Test 9 failed.");
        }


    }
}
