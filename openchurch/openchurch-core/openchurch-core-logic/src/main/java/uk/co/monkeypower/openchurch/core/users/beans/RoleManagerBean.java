package uk.co.monkeypower.openchurch.core.users.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import uk.co.monkeypower.openchurch.core.users.entities.Role;

@Stateless
public class RoleManagerBean implements RoleManager {
    
    @PersistenceContext(unitName="openchurch_users")
    private EntityManager entityManager;
    
    public void createRole(Role roleToBeCreated) {
	entityManager.persist(roleToBeCreated);
    }

    public List<Role> getAllRoles() {
	Query getAllRolesQuery = entityManager.createQuery("select r from Role r");
	return getAllRolesQuery.getResultList();
    }

    public Role getRole(String roleTitle) {
	Query getRoleByTitleQuery = entityManager.createQuery("select r from Role r where r.title = ?1");
	getRoleByTitleQuery.setParameter(1, roleTitle);
	return (Role) getRoleByTitleQuery.getSingleResult();
    }

    public void removeRole(Role role) {
	entityManager.remove(role);
    }

}
