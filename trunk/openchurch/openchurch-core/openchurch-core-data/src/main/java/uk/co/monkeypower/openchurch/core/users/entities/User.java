package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.jasypt.digest.StandardStringDigester;

import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;

/**
 * The Class User. Simple entity which represents a user.
 */
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
    private String homeTelNumber;
    private String workTelNumber;
    private String mobileTelNumber;
    private String password;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="openchurch_user_addresses", joinColumns=@JoinColumn(name="parent"), inverseJoinColumns=@JoinColumn(name="child"))
    private List<Address> addresses;
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name="openchurch_user_roles", joinColumns=@JoinColumn(name="parent"), inverseJoinColumns=@JoinColumn(name="child"))
    private List<Role> roles;
    
    @Transient
    private boolean authenticated = false;
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }
    
    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username.
     * 
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the preferred names.
     * 
     * @return the preferred names
     */
    public String getPreferredNames() {
        return preferredNames;
    }
    
    /**
     * Sets the preferred names.
     * 
     * @param preferredNames the new preferred names
     */
    public void setPreferredNames(String preferredNames) {
        this.preferredNames = preferredNames;
    }
    
    /**
     * Gets the surname.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Sets the surname.
     * 
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * Gets the email address.
     * 
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    
    /**
     * Sets the email address.
     * 
     * @param emailAddress the new email address
     * 
     * @throws UserAttributeValidationException the user attribute validation exception
     */
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
    
    /**
     * Gets the home tel number.
     * 
     * @return the home tel number
     */
    public String getHomeTelNumber() {
        return homeTelNumber;
    }
    
    /**
     * Sets the home tel number.
     * 
     * @param homeTelNumber the new home tel number
     */
    public void setHomeTelNumber(String homeTelNumber) {
        this.homeTelNumber = homeTelNumber;
    }
    
    /**
     * Gets the work tel number.
     * 
     * @return the work tel number
     */
    public String getWorkTelNumber() {
        return workTelNumber;
    }
    
    /**
     * Sets the work tel number.
     * 
     * @param workTelNumber the new work tel number
     */
    public void setWorkTelNumber(String workTelNumber) {
        this.workTelNumber = workTelNumber;
    }
    
    /**
     * Gets the mobile tel number.
     * 
     * @return the mobile tel number
     */
    public String getMobileTelNumber() {
        return mobileTelNumber;
    }
    
    /**
     * Sets the mobile tel number.
     * 
     * @param mobileTelNumber the new mobile tel number
     */
    public void setMobileTelNumber(String mobileTelNumber) {
        this.mobileTelNumber = mobileTelNumber;
    }
    
    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
    	StandardStringDigester digester = new StandardStringDigester();
    	digester.setAlgorithm("SHA-256");
    	digester.setIterations(5000);
        this.password = digester.digest(password);
    }
    
    /**
     * Gets the addresses.
     * 
     * @return the addresses
     */
    public List<Address> getAddresses() {
        return addresses;
    }
    
    /**
     * Sets the addresses.
     * 
     * @param addresses the new addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
    /**
     * Gets the roles.
     * 
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }
    
    /**
     * Sets the roles.
     * 
     * @param roles the new roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
	
	/**
	 * Authenticate.
	 * 
	 * @param password the password
	 */
	public void authenticate(String password) {
		StandardStringDigester digester = new StandardStringDigester();
    	digester.setAlgorithm("SHA-256");
    	digester.setIterations(5000);
    	authenticated = digester.matches(password, this.password);
	}
	
	/**
	 * Checks if is authenticated.
	 * 
	 * @return true, if is authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}
    
    
}
