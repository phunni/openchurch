package uk.co.monkeypower.openchurch.core.users.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="openchurch_users")
public class User {
    
    @Id
    private int id;
    private String username;
    private String preferredNames;
    private String surname;
    
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
    
    

}
