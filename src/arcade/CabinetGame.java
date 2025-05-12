/*************************************************************************

 File: CabinetGame.java

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

public class CabinetGame extends ArcadeGame {

    // Member Variables
    private boolean hasRewardPayout;

    // Constructor
    public CabinetGame(String id, String name, int price, boolean hasRewardPayout){
        super(id, name, price);
        // Validate first letter C
    }

    // get price
    @Override
    public int calculatePrice(boolean peak) {
        if (peak){
            return 100;
        }
        else {
            if (hasRewardPayout) {
                return 20;
            }
            return 50;
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
