package uk.co.monkeypower.openchurch.core.users.beans.exceptions;

/**
 * The Class UserManagementException.
 */
public class UserManagementException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new user management exception.
     */
    public UserManagementException() {
	super();
    }

    /**
     * Instantiates a new user management exception.
     * 
     * @param message the message
     */
    public UserManagementException(String message) {
	super(message);
    }

    /**
     * Instantiates a new user management exception.
     * 
     * @param cause the cause
     */
    public UserManagementException(Throwable cause) {
	super(cause);
    }

    /**
     * Instantiates a new user management exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public UserManagementException(String message, Throwable cause) {
	super(message, cause);
    }

}
