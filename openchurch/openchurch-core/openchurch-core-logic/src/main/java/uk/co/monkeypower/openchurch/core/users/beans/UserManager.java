package uk.co.monkeypower.openchurch.core.users.beans;


import javax.ejb.Remote;

import uk.co.monkeypower.openchurch.core.users.entities.User;

@Remote
public interface UserManager {
    
    public void createUser();
    public User findUser(String username);
    public void editUser();
    public void removeUser();
    public void setUser(User user);
    public User getUser();
    public boolean isAdmin();

}
