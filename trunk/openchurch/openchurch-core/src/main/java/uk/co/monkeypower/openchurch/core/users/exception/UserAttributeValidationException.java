package uk.co.monkeypower.openchurch.core.users.exception;

public class UserAttributeValidationException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public UserAttributeValidationException(){
	super();
    }

    public UserAttributeValidationException(String message, Throwable cause) {
	super(message, cause);
    }

    public UserAttributeValidationException(String message) {
	super(message);
    }

    public UserAttributeValidationException(Throwable cause) {
	super(cause);
    }
    
    

}
