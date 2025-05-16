/*************************************************************************

 File: Customer.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:


 History: 04/05/2025 v 1.00

 *************************************************************************/
package customer;

import arcade.*;

import customer.DiscountType;

import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;
import exceptions.InvalidGameIdException;

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
                case NONE -> {
                    break;
                }
            }
        // Adds overdraft regardless of if peak
        } else {
            if (discountType == DiscountType.STUDENT){
                overdraft = -500;
            }
        }

        // Checking balance
        // If balance after game less than 0/-500
        if ((balance - price) < overdraft){
            throw new InsufficientBalanceException("Insufficient funds to play game.");
        }

        // Deducts cost of game from balance
        balance -= price;
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
                "CustomerID = %s, Name = %s, Age = %d, Discount Type = %s Balance = %.2f",
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
            System.out.println("Test 2 passed : " + c3);
        } catch (InvalidCustomerException e) {
            System.err.println("Test 2 failed: " + e.getMessage());
        }










        // Initial customers and example classes

        Customer STAFF = new Customer("Test01", "Customer 1", 25, DiscountType.STAFF, 1000);
        Customer STUDENT = new Customer("Test02", "Customer 2", 27, DiscountType.STUDENT, 800);
        Customer NONE = new Customer("Test03", "Customer 3", 31, DiscountType.NONE, 400);

        // Games for testing
        ActiveGame activeGame = new ActiveGame("A123456789", "Test active game 1", 400, 18);
        CabinetGame cabinetGame = new CabinetGame("CBAcba1234", "Test cabinet game 1", 100, true);
        VirtualRealityGame virtualRealityGame = new VirtualRealityGame("AV87654321", "Test virtual reality game 1", 465, 17, EquipmentType.HEADSET_ONLY);


        // Off-peak price
        try {





            System.out.println("Test  passed: ");
        } catch (InvalidGameIdException e) {
            System.err.println("Test 4 failed: " + e.getMessage());
        }

    }
}
