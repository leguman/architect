package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseInstanceBean;
import com.hildeberto.architect.business.DatabaseSchemaBean;
import com.hildeberto.architect.business.DatabaseTableBean;
import com.hildeberto.architect.business.DatabaseViewBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.DatabaseView;
import com.hildeberto.architect.domain.LifecycleState;
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

    @EJB
    private DatabaseTableBean databaseTableBean;

    @EJB
    private DatabaseViewBean databaseViewBean;

    private List<DatabaseInstance> databases;
    private List<DatabaseSchema> relatedSchemas;
    private List<DatabaseTable> relatedTables;
    private List<DatabaseView> relatedViews;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    private DatabaseInstance database;

    public List<DatabaseInstance> getDatabases() {
        if(databases == null) {
            databases = databaseInstanceBean.findAll();
        }
        return databases;
    }

    public List<DatabaseSchema> getRelatedSchemas() {
        if(relatedSchemas == null && database != null) {
            relatedSchemas = databaseSchemaBean.findByDatabaseInstance(database);
        }
        return relatedSchemas;
    }

    public List<DatabaseTable> getRelatedTables() {
        if(relatedTables == null && database != null) {
            relatedTables = databaseTableBean.findByDatabaseInstance(database, LifecycleState.INUSE);
        }
        return relatedTables;
    }

    public List<DatabaseView> getRelatedViews() {
        if(relatedViews == null && database != null) {
            relatedViews = databaseViewBean.findByDatabaseInstance(database);
        }
        return relatedViews;
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