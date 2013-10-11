package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseSchemaBean;
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
public class DatabaseSchemaMBean {
 
    @EJB
    private DatabaseSchemaBean databaseSchemaBean;
        
    private List<DatabaseSchema> schemas;
    
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    private DatabaseSchema schema;
    
    public List<DatabaseSchema> getSchemas() {
        if(schemas == null) {
            schemas = databaseSchemaBean.findAll();
        }
        return schemas;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public DatabaseSchema getSchema() {
        return this.schema;
    }
    
    @PostConstruct
    public void load() {
        if(id != null) {
            this.schema = databaseSchemaBean.find(id);
        }
        else {
            this.schema = new DatabaseSchema();
        }
    }
    
    public String save() {
        databaseSchemaBean.save(this.schema);
        return "schemas";
    }
}