package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Remote;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.UserManagementException;
import uk.co.monkeypower.openchurch.core.users.entities.Address;
import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

@Remote
public interface UserManager {
    
    public User createUser(String username, String preferredNames, String surname, String emailAddress,
				String homeTelNumber, String workTelNumber, String mobileTelNumber, 
				List<Address> addresses, List<Role> roles) throws UserManagementException;
    public User findUser(String username);
    public User editUser(User userToBeEdited);
    public User removeUser(User userToBeRemoved);

}
