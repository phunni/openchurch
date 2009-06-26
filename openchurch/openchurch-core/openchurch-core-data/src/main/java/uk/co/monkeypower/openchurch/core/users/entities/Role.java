package uk.co.monkeypower.openchurch.core.users.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * The Class Role. Represents a role in the site - such as an admin, member etc.
 */
@Entity
@Table(name="openchurch_roles")
@NamedQuery(query="select r from Role r where r.title=?1", name = "selectRoleBytitle")
public class Role {
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String title;
    @ManyToMany(mappedBy="roles")
    private List<User> users;
    
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
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the user.
     * 
     * @param id the id
     * 
     * @return the user
     */
    public User getUser(long id){
	User user = null;
	for(User currentUser : users){
	    if (currentUser.getId() == id){
		user = currentUser;
	    }
	}
	return user;
    }
    
    /**
     * Gets the user.
     * 
     * @param username the username
     * 
     * @return the user
     */
    public User getUser(String username){
	User user = null;
	for(User currentUser : users){
	    if (currentUser.getUsername().equalsIgnoreCase(username)){
		user = currentUser;
	    }
	}
	return user;
    }
    
    /**
     * Gets the users.
     * 
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }
    
    /**
     * Sets the users.
     * 
     * @param users the new users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    

}
