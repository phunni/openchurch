package uk.co.monkeypower.openchurch.core.users.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;

@Entity
@Table(name="openchurch_users")
public class User {
    
    @Id
    private int id;
    private String username;
    private String preferredNames;
    private String surname;
    private String emailAddress;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPreferredNames() {
        return preferredNames;
    }
    public void setPreferredNames(String preferredNames) {
        this.preferredNames = preferredNames;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) throws UserAttributeValidationException {
	if (emailAddress.contains("mailinator")) {
	    throw new UserAttributeValidationException("Tried to use a cheeky spam trap...");
	}
	int atSymbol = emailAddress.indexOf("@");
	int dotCharacter = emailAddress.lastIndexOf(".");
	if (atSymbol == -1 || dotCharacter == -1 || dotCharacter < atSymbol){
	    throw new UserAttributeValidationException("Invalid email address provided...");
	}
        this.emailAddress = emailAddress;
    }
    
    

}
