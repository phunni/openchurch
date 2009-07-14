package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.users.entities.Role;

/**
 * The Interface RoleManager. This is the Local EJB interface.
 */
@Local
public interface RoleManager {
    
    /**
     * Creates the role.
     * 
     * @param roleToBeCreated the role to be created
     */
    public void createRole(Role roleToBeCreated);
    
    /**
     * Removes the role.
     * 
     * @param role the role
     */
    public void removeRole(Role role);
    
    /**
     * Gets the role.
     * 
     * @param roleTitle the role title
     * 
     * @return the role
     */
    public Role getRole(String roleTitle);
    
    /**
     * Gets the all roles.
     * 
     * @return the all roles
     */
    public List<Role> getAllRoles();
    
}
