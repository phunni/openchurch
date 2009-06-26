package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import uk.co.monkeypower.openchurch.core.users.exception.AddressAttributeValidationException;

/**
 * The Class Address.  A simple entity which represents a users's address
 */
@Entity
@Table(name="openchurch_addresses")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String firstLine;
    private String secondLine;
    private String thirdLine;
    private String town;
    private String county;
    private String postCode;
    
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
     * Gets the first line.
     * 
     * @return the first line
     */
    public String getFirstLine() {
        return firstLine;
    }
    
    /**
     * Sets the first line.
     * 
     * @param firstLine the new first line
     */
    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }
    
    /**
     * Gets the second line.
     * 
     * @return the second line
     */
    public String getSecondLine() {
        return secondLine;
    }
    
    /**
     * Sets the second line.
     * 
     * @param secondLine the new second line
     */
    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }
    
    /**
     * Gets the third line.
     * 
     * @return the third line
     */
    public String getThirdLine() {
        return thirdLine;
    }
    
    /**
     * Sets the third line.
     * 
     * @param thirdLine the new third line
     */
    public void setThirdLine(String thirdLine) {
        this.thirdLine = thirdLine;
    }
    
    /**
     * Gets the town.
     * 
     * @return the town
     */
    public String getTown() {
        return town;
    }
    
    /**
     * Sets the town.
     * 
     * @param town the new town
     */
    public void setTown(String town) {
        this.town = town;
    }
    
    /**
     * Gets the county.
     * 
     * @return the county
     */
    public String getCounty() {
        return county;
    }
    
    /**
     * Sets the county.
     * 
     * @param county the new county
     */
    public void setCounty(String county) {
        this.county = county;
    }
    
    /**
     * Gets the post code.
     * 
     * @return the post code
     */
    public String getPostCode() {
        return postCode;
    }
    
    /**
     * Sets the post code.
     * 
     * @param postCode the new post code
     * 
     * @throws AddressAttributeValidationException the address attribute validation exception
     */
    public void setPostCode(String postCode) throws AddressAttributeValidationException {
	if (postCode.matches("[a-zA-Z]{1,2}[0-9][0-9A-Za-z]? [0-9][a-zA-Z]{2}")) {
	    this.postCode = postCode;
	} else {
	    throw new AddressAttributeValidationException("An attempt was made to set an invalid post code");
	}
    }
    
    

}
