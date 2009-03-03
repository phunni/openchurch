package uk.co.monkeypower.openchurch.core.users.beans.exceptions;

public class UserManagementException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserManagementException() {
	super();
    }

    public UserManagementException(String message) {
	super(message);
    }

    public UserManagementException(Throwable cause) {
	super(cause);
    }

    public UserManagementException(String message, Throwable cause) {
	super(message, cause);
    }

}
