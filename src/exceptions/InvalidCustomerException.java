/*************************************************************************

 File: InvalidCustomerException.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 EXCEPTION - Returns an error message


 History: 04/05/2025 v 1.00

 *************************************************************************/
package exceptions;

public class InvalidCustomerException extends RuntimeException {
    // Default message
    public InvalidCustomerException() {
        super("Default Error: Invalid Customer");
    }

    public InvalidCustomerException(String message) {
        super(message);
    }
}
