package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Local;

import uk.co.monkeypower.openchurch.core.users.entities.Role;

@Local
public interface RoleManager {
    
    public void createRole(Role roleToBeCreated);
    public void removeRole(Role role);
    public Role getRole(String roleTitle);
    public List<Role> getAllRoles();
    
}
