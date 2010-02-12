package uk.co.monkeypower.openchurch.core.users.beans;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;


/**
 * The Class UserManagerBean. This class acts as a manager for the User Entity.
 */
@Stateful
public class UserManagerBean implements UserManager {
    
    private final Log LOG = LogFactory.getLog(UserManagerBean.class);
    
    @PersistenceContext(unitName="openchurch_users")
    private EntityManager entityManager;
    
    private User user;

    /**
     * Gets the entity manager.
     * 
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the entity manager.
     * 
     * @param entityManager the new entity manager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#createUser()
     */
    public void createUser() {
	if(checkUserNotNull()){
	    entityManager.persist(user);
	} else {
	    LOG.warn("An attempt was made to persist a null user");
	}
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#editUser()
     */
    public void editUser() {
	if(checkUserNotNull()){
	    entityManager.merge(user);
	} else {
	    LOG.warn("An attempt was made to modify a null user");
	}
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#findUser(java.lang.String)
     */
    public User getUser(String username) {
	Query findUserQuery = entityManager.createQuery("select u from User u where u.username = ?1");
	findUserQuery.setParameter(1, username);
	user = (User) findUserQuery.getSingleResult();
	return user;
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#removeUser()
     */
    public void removeUser() {
	if(checkUserNotNull()){
	    entityManager.remove(entityManager.merge(user));
	} else {
	    LOG.warn("An attempt was made to remove a null user");
	}
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#getUser()
     */
    public User getUser() {
	return user;
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#setUser(uk.co.monkeypower.openchurch.core.users.entities.User)
     */
    public void setUser(User user) {
	this.user = user;
    }

    private boolean checkUserNotNull(){
	if (user == null){
	    return false;
	} else {
	    return true;
	}
    }

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.UserManager#isAdmin()
     */
    public boolean isAdmin() {
	for (Role currentRole : user.getRoles()){
	    if (currentRole.getTitle().equals("admin")){
		return true;
	    }
	}
	return false;
    }
    
    

}
