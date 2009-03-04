package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Remote;

import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

@Remote
public interface RoleManager {
    
    public void createRole(Role roleToBeCreated);
    public void removeRole(Role role);
    public Role getRole(String roleTitle);
    public List<Role> getAllRoles();
    
}
