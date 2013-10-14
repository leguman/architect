package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseInstanceBean;
import com.hildeberto.architect.business.DatabaseSchemaBean;
import com.hildeberto.architect.business.DatabaseTableBean;
import com.hildeberto.architect.business.DatabaseViewBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.DatabaseView;
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
public class SchemaMBean {

    @EJB
    private DatabaseSchemaBean databaseSchemaBean;

    @EJB
    private DatabaseInstanceBean databaseInstanceBean;

    @EJB
    private DatabaseTableBean databaseTableBean;

    @EJB
    private DatabaseViewBean databaseViewBean;

    private List<DatabaseSchema> schemas;
    private List<DatabaseInstance> databases;
    private List<DatabaseTable> relatedTables;
    private List<DatabaseView> relatedViews;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbId}")
    private Integer selectedDatabase;

    private DatabaseSchema schema;

    public List<DatabaseInstance> getDatabases() {
        if(this.databases == null) {
            this.databases = databaseInstanceBean.findAll();
        }
        return this.databases;
    }

    public List<DatabaseSchema> getSchemas() {
        if(schemas == null && selectedDatabase != null) {
            DatabaseInstance databaseInstance = new DatabaseInstance(selectedDatabase);
            schemas = databaseSchemaBean.findByDatabaseInstance(databaseInstance);
        }
        return schemas;
    }

    public List<DatabaseTable> getRelatedTables() {
        if(relatedTables == null && schema != null) {
            relatedTables = databaseTableBean.findByDatabaseSchema(schema);
        }
        return relatedTables;
    }

    public List<DatabaseView> getRelatedViews() {
        if(relatedViews == null && schema != null) {
            relatedViews = databaseViewBean.findByDatabaseSchema(schema);
        }
        return relatedViews;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DatabaseSchema getSchema() {
        return this.schema;
    }

    public void setDbId(Integer dbId) {
        this.selectedDatabase = dbId;
    }

    public Integer getSelectedDatabase() {
        return selectedDatabase;
    }

    public void setSelectedDatabase(Integer selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.schema = databaseSchemaBean.find(id);
            this.selectedDatabase = this.schema.getDatabaseInstance().getId();
        }
        else {
            this.schema = new DatabaseSchema();
            if(selectedDatabase != null) {
                this.schema.setDatabaseInstance(databaseInstanceBean.find(selectedDatabase));
            }
        }
    }

    public String save() {
        DatabaseInstance database = databaseInstanceBean.find(selectedDatabase);
        this.schema.setDatabaseInstance(database);

        databaseSchemaBean.save(this.schema);
        return "schemas";
    }
}