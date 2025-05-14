/*************************************************************************

 File: InvalidGameIdException.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 EXCEPTION - Returns an error message


 History: 04/05/2025 v 1.00

 *************************************************************************/
package exceptions;

public class InvalidGameIdException extends RuntimeException {
    // Default message
    public InvalidGameIdException() {
        super("Default Error: Invalid Game Id.");
    }

    public InvalidGameIdException(String message) {super(message);}
}

