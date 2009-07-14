package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import uk.co.monkeypower.openchurch.core.users.entities.Role;

/**
 * The Class RoleManagerBean. This class acts as an entity manager for the Role
 * Entity
 */
@Stateless
public class RoleManagerBean implements RoleManager {
    
    @PersistenceContext(unitName="openchurch_users")
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.RoleManager#createRole(uk.co.monkeypower.openchurch.core.users.entities.Role)
     */
    public void createRole(Role roleToBeCreated) {
	entityManager.persist(roleToBeCreated);
    }


    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.RoleManager#getAllRoles()
     */
    public List<Role> getAllRoles() {
	Query getAllRolesQuery = entityManager.createQuery("select r from Role r");
	return getAllRolesQuery.getResultList();
    }


    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.RoleManager#getRole(java.lang.String)
     */
    public Role getRole(String roleTitle) {
	Query getRoleByTitleQuery = entityManager.createQuery("select r from Role r where r.title = ?1");
	getRoleByTitleQuery.setParameter(1, roleTitle);
	return (Role) getRoleByTitleQuery.getSingleResult();
    }


    /* (non-Javadoc)
     * @see uk.co.monkeypower.openchurch.core.users.beans.RoleManager#removeRole(uk.co.monkeypower.openchurch.core.users.entities.Role)
     */
    public void removeRole(Role role) {
	entityManager.remove(role);
    }

}
