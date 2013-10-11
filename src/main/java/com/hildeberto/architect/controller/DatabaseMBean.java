package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseInstanceBean;
import com.hildeberto.architect.business.DatabaseSchemaBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Hildeberto Mendonca
 */
@ManagedBean
@RequestScoped
public class DatabaseMBean {
 
    @EJB
    private DatabaseInstanceBean databaseInstanceBean;
    
    @EJB
    private DatabaseSchemaBean databaseSchemaBean;
        
    private List<DatabaseInstance> databases;
    private List<DatabaseSchema> schemas;
    
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    private DatabaseInstance database;
    
    public List<DatabaseInstance> getDatabases() {
        if(databases == null) {
            databases = databaseInstanceBean.findAll();
        }
        return databases;
    }
    
    public List<DatabaseSchema> getSchemas() {
        if(schemas == null && database != null) {
            schemas = databaseSchemaBean.findByDatabaseInstance(database);
        }
        return schemas;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public DatabaseInstance getDatabase() {
        return this.database;
    }
    
    @PostConstruct
    public void load() {
        if(id != null) {
            this.database = databaseInstanceBean.find(id);
        }
        else {
            this.database = new DatabaseInstance();
        }
    }
    
    public String save() {
        databaseInstanceBean.save(this.database);
        return "databases";
    }
}