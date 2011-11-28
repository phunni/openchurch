package uk.co.monkeypower.openchurch.core.users.beans.exceptions;

public class PropertiesManagerException extends Exception {

	private static final long serialVersionUID = -7013540939124356725L;

	public PropertiesManagerException() {
		super();
	}

	public PropertiesManagerException(String message) {
		super(message);
	}

	public PropertiesManagerException(Throwable cause) {
		super(cause);
	}

	public PropertiesManagerException(String message, Throwable cause) {
		super(message, cause);
	}

}
