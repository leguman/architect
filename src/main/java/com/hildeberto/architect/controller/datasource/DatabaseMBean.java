package com.hildeberto.architect.controller.datasource;

import com.hildeberto.architect.business.datasource.DatabaseInstanceBean;
import com.hildeberto.architect.business.datasource.DatabaseSchemaBean;
import com.hildeberto.architect.business.datasource.DatabaseTableBean;
import com.hildeberto.architect.business.datasource.DatabaseViewBean;
import com.hildeberto.architect.domain.datasource.DatabaseInstance;
import com.hildeberto.architect.domain.datasource.DatabaseSchema;
import com.hildeberto.architect.domain.datasource.DatabaseTable;
import com.hildeberto.architect.domain.datasource.DatabaseView;
import com.hildeberto.architect.domain.LifecycleState;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author Hildeberto Mendonca
 */
@Named
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