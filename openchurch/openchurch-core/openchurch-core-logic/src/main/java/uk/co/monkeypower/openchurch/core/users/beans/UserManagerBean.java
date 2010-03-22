package uk.co.monkeypower.openchurch.core.users.beans;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import uk.co.monkeypower.openchurch.core.users.beans.exceptions.UserManagementException;
import uk.co.monkeypower.openchurch.core.users.entities.Role;
import uk.co.monkeypower.openchurch.core.users.entities.User;

/**
 * The Class UserManagerBean. This class acts as a manager for the User Entity.
 */
@Stateful
public class UserManagerBean implements UserManager {

	private final Logger LOG = Logger.getLogger(UserManagerBean.class);

	@PersistenceContext(unitName = "openchurch_users")
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
	 * @param entityManager
	 *            the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void createUser() {
		if (checkUserNotNull()) {
			entityManager.persist(user);
		} else {
			LOG.warn("An attempt was made to persist a null user");
		}
	}

	public void editUser() {
		if (checkUserNotNull()) {
			entityManager.merge(user);
		} else {
			LOG.warn("An attempt was made to modify a null user");
		}
	}

	public User getUser(String username) throws UserManagementException {
		Query findUserQuery = entityManager
				.createQuery("select u from User u where u.username = ?1");
		findUserQuery.setParameter(1, username);
		try {
			user = (User) findUserQuery.getSingleResult();
		} catch (NoResultException e) {
			throw new UserManagementException(
					"No user found for supplied credentials: " + username, e);
		}
		return user;
	}

	public void removeUser() {
		if (checkUserNotNull()) {
			entityManager.remove(entityManager.merge(user));
		} else {
			LOG.warn("An attempt was made to remove a null user");
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private boolean checkUserNotNull() {
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isAdmin() {
		for (Role currentRole : user.getRoles()) {
			if (currentRole.getTitle().equals("admin")) {
				return true;
			}
		}
		return false;
	}

}
