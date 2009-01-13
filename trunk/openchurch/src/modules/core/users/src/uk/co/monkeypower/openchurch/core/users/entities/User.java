package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;

@Entity
@Table(name="openchurch_users")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String username;
    private String preferredNames;
    private String surname;
    private String emailAddress;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
