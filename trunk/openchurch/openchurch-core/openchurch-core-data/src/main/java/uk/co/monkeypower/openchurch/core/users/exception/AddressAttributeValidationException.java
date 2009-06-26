package uk.co.monkeypower.openchurch.core.users.exception;

/**
 * The Class AddressAttributeValidationException. This exception is thrown
 * whenever there is an attempt to set invalid data in the address object.
 */
public class AddressAttributeValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new address attribute validation exception.
     */
    public AddressAttributeValidationException() {
	super();
    }

    /**
     * Instantiates a new address attribute validation exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public AddressAttributeValidationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Instantiates a new address attribute validation exception.
     * 
     * @param message the message
     */
    public AddressAttributeValidationException(String message) {
	super(message);
    }

    /**
     * Instantiates a new address attribute validation exception.
     * 
     * @param cause the cause
     */
    public AddressAttributeValidationException(Throwable cause) {
	super(cause);
    }

    
    
}
