package com.hildeberto.architect.controller.datasource;

import com.hildeberto.architect.business.datasource.DatabaseInstanceBean;
import com.hildeberto.architect.business.datasource.DatabaseSchemaBean;
import com.hildeberto.architect.domain.datasource.DatabaseInstance;
import com.hildeberto.architect.domain.datasource.DatabaseSchema;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Hildeberto Mendonca
 */
@Named
@ViewScoped
public class DatabaseFilterMBean {
    
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