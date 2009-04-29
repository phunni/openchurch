package uk.co.monkeypower.openchurch.core.layout.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="openchurch_menus")
public class Menu {
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String name;
    @ManyToMany()
    @JoinTable(name="openchurch_menu_menutitems", joinColumns=@JoinColumn(name="parent"), inverseJoinColumns=@JoinColumn(name="child"))
    private List<MenuItem> items;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<MenuItem> getItems() {
        return items;
    }
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
    
    

}
