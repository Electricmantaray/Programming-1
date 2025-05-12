/*************************************************************************

 File: ActiveGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 ---

 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import arcade.GameType;
import exceptions.InvalidGameIdException;

import java.util.ArrayList;


public class ActiveGame extends ArcadeGame {

    // Member Variables
    private int minAge;

    // Constructor
    public ActiveGame(String id, String name, int price, int minAge){
        super(id, name, price);
        // Validate first letter A
    }

    // Add get prices
    @Override
    public int calculatePrice(boolean peak) {
        if (peak){
            return 100;
        }
        else{
            return 20;
        }
    }

    @Override
    public String toString() {
       return "";
   }

    // Unit tests
    public static void main(String[] args) {
   }
}

