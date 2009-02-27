package uk.co.monkeypower.openchurch.core.users.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstLine() {
        return firstLine;
    }
    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }
    public String getSecondLine() {
        return secondLine;
    }
    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }
    public String getThirdLine() {
        return thirdLine;
    }
    public void setThirdLine(String thirdLine) {
        this.thirdLine = thirdLine;
    }
    public String getTown() {
        return town;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) throws AddressAttributeValidationException {
	if (postCode.matches("[a-zA-Z]{1,2}[0-9][0-9A-Za-z]? [0-9][a-zA-Z]{2}")) {
	    this.postCode = postCode;
	} else {
	    throw new AddressAttributeValidationException("An attempt was made to set an invalid post code");
	}
    }
    
    

}
