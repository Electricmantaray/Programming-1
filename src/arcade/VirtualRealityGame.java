/*************************************************************************

 File: VirtualRealityGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:


 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import arcade.GameType;
import arcade.EquipmentType;
import exceptions.InvalidGameIdException;

public class VirtualRealityGame extends ActiveGame{

    // Method Variables
    private EquipmentType equipment;

    // Constructor
    public VirtualRealityGame(String id, String name, int price, int minAge, EquipmentType equipment){
        super(/*blah blah blah*/);
        // Validate first letter AV
    }

    // get price
    @Override
    public int calculatePrice(boolean peak) {
        if (peak){
            return 100;
        }
        else {
            return switch (equipment) {
                case HEADSET_ONLY -> 90;
                case HEADSET_AND_CONTROLLERS -> 95;
                case FULL_BODY_TRACKING -> 100;
            };
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
