/*************************************************************************

 File: Customer.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:


 History: 04/05/2025 v 1.00

 *************************************************************************/
package customer;

import arcade.ArcadeGame;

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
    public void chargeAccount(ArcadeGame arcadeGame, boolean peak) throws InsufficientBalanceException, AgeLimitException{

    }



    // Accessors //


    @Override
    public String toString() {
        return "";
    }

    // Test harness
    public static void main(String[] args) {
    }
}
