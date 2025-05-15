/*************************************************************************

 File: VirtualRealityGame.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:


 History: 04/05/2025 v 1.00

 *************************************************************************/
package arcade;

//Imports
import exceptions.InvalidGameIdException;

public class VirtualRealityGame extends ActiveGame{

    // Method Variables //
    private final EquipmentType equipmentType;
    // ID start of string condition
    private static final String idStringCondition = "AV";

    // Constructor //
    public VirtualRealityGame(String id, String name, int price, int minAge, EquipmentType equipmentType) throws InvalidGameIdException{
        super(id, name, price, minAge);
        validateId(id);
        this.equipmentType = equipmentType;
    }

    // Functions //

    // Validates id with new condition of
    // Start with 'AV'
    @Override
    protected void validateId(String id) throws InvalidGameIdException {
        super.validateId(id);
        // New condition
        // exception message changes to specify id issue
        if(!id.startsWith(idStringCondition)) {
            throw new InvalidGameIdException("Invalid game ID: \nVirtualReality must start with: " + idStringCondition);
        }
    }

    // Get price, changes depending on peak and equipment
    @Override
    public int calculatePrice(boolean peak) {
        int price = getPrice();
        if (peak){
            return price;
        }
        else {
            return switch (equipmentType) {
                case HEADSET_ONLY -> (int) (Math.floor(price * 0.9));
                case HEADSET_AND_CONTROLLERS -> (int) (Math.floor(price * 0.95));
                case FULL_BODY_TRACKING -> price;
            };
        }
    }


    // Accessors //
    public EquipmentType getEquipmentType(){
        return equipmentType;
    }

    // Overrides the toString method
    // Providing a formatted VirtualRealityGame representation
    @Override
    public String toString() {
        return "\nVirtual Reality Game: " +
                super.toString() +
                ", Equipment = "
                + equipmentType.name();
    }

    // Test harness
    public static void main(String[] args) {
        System.out.println("\nVirtualRealityGame Tests:");

        // Normal test - expected format
        try {
            VirtualRealityGame t1 = new VirtualRealityGame("AV87654321", "Test virtual reality game 1", 465, 17, EquipmentType.HEADSET_ONLY);
            System.out.println("Test 1 passed: " + t1);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 1 failed: " + e.getMessage());
        }

        // Invalid ID (Doesn't start with a 'AV')
        try {
            VirtualRealityGame t2 = new VirtualRealityGame("A123456789", "Test virtual reality game 2", 875, 8, EquipmentType.HEADSET_ONLY);
            System.out.println("Test 2 passed : " + t2);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 2 failed: " + e.getMessage());
        }

        // Peak price
        // Expected outcome: 232
        try {
            VirtualRealityGame t3 = new VirtualRealityGame("AVVVVVVVVV", "Test virtual reality game 3", 232, 21, EquipmentType.FULL_BODY_TRACKING);
            int updatedPrice = t3.calculatePrice(true);
            System.out.println("Test 3 passed: " + t3 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 3 failed: " + e.getMessage());
        }

        // Off-peak price
        // Expected outcome: 106
        try {
            VirtualRealityGame t4 = new VirtualRealityGame("AV23123123", "Test virtual reality game 4", 112, 70, EquipmentType.HEADSET_AND_CONTROLLERS);
            int updatedPrice = t4.calculatePrice(false);
            System.out.println("Test 4 passed: " + t4 + ", updated price = " + updatedPrice);
        } catch (InvalidGameIdException e) {
            System.err.println("Test 4 failed: " + e.getMessage());
        }
    }
}
