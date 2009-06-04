package uk.co.monkeypower.openchurch.core.layout.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="openchurch_content_modules")
public class ContentModule {
	
    @Id
    @GeneratedValue(generator = "IdTable")
    @TableGenerator(name = "IdTable", allocationSize = 2, table = "id_generator", pkColumnName = "id_name", valueColumnName = "id_value", pkColumnValue = "INV_GEN")
    private long id;
    private String moduleClassName;
    private boolean deleteable;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getModuleClassName() {
	return moduleClassName;
    }

    public void setModuleClassName(String moduleClassName) {
	this.moduleClassName = moduleClassName;
    }

    public boolean isDeleteable() {
	return deleteable;
    }

    public void setDeleteable(boolean deleteable) {
	this.deleteable = deleteable;
    }

}
