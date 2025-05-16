/*************************************************************************

 File: simulation.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 ---

 History: 04/05/2025 v 1.00

 *************************************************************************/
package simulation;

import arcade.*;

import customer.Customer;

import exceptions.AgeLimitException;
import exceptions.InsufficientBalanceException;
import exceptions.InvalidCustomerException;
import exceptions.InvalidGameIdException;

import java.io.*;
import java.util.*;

import java.io.File;

public class Simulation {

    // File reading and simulation
    public static void main(String[] args) {
        File customers = new File("customers.txt");
        File games = new File("games.txt");
        File transactions = new File("transactions.txt");

        Arcade arcade = initialiseArcade("GameCo Arcade", games, customers);
        simulateFun(arcade, transactions);
    }

    public static Arcade initialiseArcade(String arcadeName, File gamesFile, File customerFile){
        Arcade arcade = new Arcade(arcadeName);

        
    }

    public static void simulateFun(Arcade arcade, File transactionFile){

    }

}
