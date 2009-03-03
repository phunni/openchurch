package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.UserManagementException;
import uk.co.monkeypower.openchurch.core.users.entities.Address;
import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;
import uk.co.monkeypower.openchurch.core.users.exception.UserAttributeValidationException;

@Stateful
public class UserManagerBean implements UserManager {
    
    @PersistenceContext
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User createUser(String username, String preferredNames, String surname, String emailAddress,
	String homeTelNumber, String workTelNumber, String mobileTelNumber, List<Address> addresses, List<Role> roles) throws UserManagementException {
	User createdUser = new User();
	createdUser.setUsername(username);
	createdUser.setPreferredNames(preferredNames);
	createdUser.setSurname(surname);
	try {
	    createdUser.setEmailAddress(emailAddress);
	} catch(UserAttributeValidationException e){
	    throw new UserManagementException("Tried to create a user with an invalid email address", e);
	}
	createdUser.setHomeTelNumber(homeTelNumber);
	createdUser.setWorkTelNumber(workTelNumber);
	createdUser.setMobileTelNumber(mobileTelNumber);
	createdUser.setAddresses(addresses);
	createdUser.setRoles(roles);
	entityManager.persist(createdUser);
	return createdUser;
    }

    public User editUser(User userToBeEdited) {
	entityManager.merge(userToBeEdited);
	return userToBeEdited;
    }

    public User findUser(String username) {
	Query findUserQuery = entityManager.createQuery("select u from User u where u.username = ?1");
	findUserQuery.setParameter(1, username);
	User returnedUser = (User) findUserQuery.getSingleResult();
	return returnedUser;
    }

    public User removeUser(User userToBeRemoved) {
	entityManager.remove(userToBeRemoved);
	return userToBeRemoved;
    }
    
    

}
