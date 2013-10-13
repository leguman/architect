package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseInstanceBean;
import com.hildeberto.architect.business.DatabaseSchemaBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mendoncafilh
 */
@ManagedBean
@ViewScoped
public class ElementFilterMBean {
    
    @EJB
    private DatabaseInstanceBean databaseInstanceBean;
    
    @EJB
    private DatabaseSchemaBean databaseSchemaBean;
    
    private List<DatabaseInstance> databaseInstances;
    private List<DatabaseSchema> databaseSchemas;
    
    private Integer selectedDatabaseInstance;
    private Integer selectedDatabaseSchema;
            
    public List<DatabaseInstance> getDatabaseInstances() {
        if(this.databaseInstances == null) {
            this.databaseInstances = databaseInstanceBean.findAll();
        }
        return this.databaseInstances;
    }

    public List<DatabaseSchema> getDatabaseSchemas() {
        if(this.selectedDatabaseInstance != null) {
            DatabaseInstance databaseInstance = new DatabaseInstance(this.selectedDatabaseInstance);
            this.databaseSchemas = databaseSchemaBean.findByDatabaseInstance(databaseInstance);
        }
        return this.databaseSchemas;
    }

    public Integer getSelectedDatabaseInstance() {
        return selectedDatabaseInstance;
    }

    public void setSelectedDatabaseInstance(Integer selectedDatabaseInstance) {
        this.selectedDatabaseInstance = selectedDatabaseInstance;
    }
    
    public Integer getSelectedDatabaseSchema() {
        return selectedDatabaseSchema;
    }

    public void setSelectedDatabaseSchema(Integer selectedDatabaseSchema) {
        this.selectedDatabaseSchema = selectedDatabaseSchema;
    }
    
    public DatabaseInstance getDatabaseInstance() {
        if(this.selectedDatabaseInstance != null) {
            return databaseInstanceBean.find(this.selectedDatabaseInstance);
        }
        else {
            return null;
        }
    }
    
    public DatabaseSchema getDatabaseSchema() {
        if(this.selectedDatabaseSchema != null) {
            return databaseSchemaBean.find(this.selectedDatabaseSchema);
        }
        else {
            return null;
        }
    }
}