/*************************************************************************

 File: InsufficientBalanceException.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 EXCEPTION - Returns an error message


 History: 04/05/2025 v 1.00

 *************************************************************************/
package exceptions;

public class InsufficientBalanceException extends RuntimeException {
    // Default message
    public InsufficientBalanceException() {
        super("Default Error: Insufficient Balance");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
