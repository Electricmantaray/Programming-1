/*************************************************************************

 File: AgeLimitException.java

 Author: Hayden Jones

 Date started: 04/05/2025

 Description:
 EXCEPTION - Returns an error message


 History: 04/05/2025 v 1.00

 *************************************************************************/
package exceptions;

public class AgeLimitException extends RuntimeException {
    // Default message
    public AgeLimitException() {
        super("Default Error: Incorrect Age Limit");
    }

    public AgeLimitException(String message) {
        super(message);
    }
}
