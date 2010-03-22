package uk.co.monkeypower.openchurch.core.layout.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="openchurch_pages")
public class Page {
    
    @Id
    @GeneratedValue(generator="IdTable")
    @TableGenerator(name="IdTable", allocationSize=2, table = "id_generator", pkColumnName="id_name", valueColumnName="id_value", pkColumnValue="INV_GEN")
    private long id;
    private String name;
    private String title;
    @OneToMany
    @JoinTable(name="openchurch_page_modules", joinColumns=@JoinColumn(name="parent"), inverseJoinColumns=@JoinColumn(name="child"))
    private List<ContentModule> modules;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<ContentModule> getModules() {
        return modules;
    }
    public void setModules(List<ContentModule> modules) {
        this.modules = modules;
    }
    

}
