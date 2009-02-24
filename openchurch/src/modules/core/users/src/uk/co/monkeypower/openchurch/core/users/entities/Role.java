package uk.co.monkeypower.openchurch.core.users.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="openchurch_roles")
@NamedQuery(query="select r from Role r where r.title=?1", name = "selectRoleBytitle")
public class Role {
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String title;
    public long getId() {
        return id;
    }
    @ManyToMany(mappedBy="roles")
    public List<User> users;
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public User getUser(long id){
	User user = null;
	for(User currentUser : users){
	    if (currentUser.getId() == id){
		user = currentUser;
	    }
	}
	return user;
    }
    
    public User getUser(String username){
	User user = null;
	for(User currentUser : users){
	    if (currentUser.getUsername().equalsIgnoreCase(username)){
		user = currentUser;
	    }
	}
	return user;
    }

}
