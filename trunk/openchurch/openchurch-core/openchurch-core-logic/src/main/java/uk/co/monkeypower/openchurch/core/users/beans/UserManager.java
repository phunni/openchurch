package uk.co.monkeypower.openchurch.core.users.beans;


import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.users.entities.User;

/**
 * The Interface UserManager. This is the Local EJB interface.
 */
@Local
public interface UserManager {
    
    /**
     * Creates the user.
     */
    public void createUser();
    
    /**
     * Find user.
     * 
     * @param username the username
     * 
     * @return the user
     */
    public User getUser(String username);
    
    /**
     * Edits the user.
     */
    public void editUser();
    
    /**
     * Removes the user.
     */
    public void removeUser();
    
    /**
     * Sets the user.
     * 
     * @param user the new user
     */
    public void setUser(User user);
    
    /**
     * Gets the user.
     * 
     * @return the user
     */
    public User getUser();
    
    /**
     * Checks if is admin.
     * 
     * @return true, if is admin
     */
    public boolean isAdmin();

}
