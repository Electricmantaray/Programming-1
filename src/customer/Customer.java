/*************************************************************************

 File: Customer.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:


 History: 04/05/2025 v 1.00

 *************************************************************************/
package customer;

import arcade.ArcadeGame;

public class Customer {

    // Member Variables
    private String id;
    private String name;
    private int age;
    private DiscountType discount;
    private int balance;

    // Constructors
    public Customer(String id, String name, int age, DiscountType discount){
        // fill
    }

    public Customer(String id, String name, int age, DiscountType discount, int balance){
        // fill
    }

    public void addFunds(int amount){
        // fill
    }

    public void chargeAccount(ArcadeGame game, boolean peak){
        // fill discounts
    }






    @Override
    public String toString() {
        return "";
    }

    // Unit tests
    public static void main(String[] args) {
    }
}
