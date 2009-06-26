package uk.co.monkeypower.openchurch.core.users.exception;

/**
 * The Class UserAttributeValidationException. This exception is thrown when
 * there is an attempt to set invalid data in the user object e.g. a 
 * malformed email address.
 */
public class UserAttributeValidationException extends Exception {

    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new user attribute validation exception.
     */
    public UserAttributeValidationException(){
	super();
    }

    /**
     * Instantiates a new user attribute validation exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public UserAttributeValidationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Instantiates a new user attribute validation exception.
     * 
     * @param message the message
     */
    public UserAttributeValidationException(String message) {
	super(message);
    }

    /**
     * Instantiates a new user attribute validation exception.
     * 
     * @param cause the cause
     */
    public UserAttributeValidationException(Throwable cause) {
	super(cause);
    }
    
    

}
