package com.hildeberto.architect.datasource.controller;

import com.hildeberto.architect.datasource.business.DatabaseSchemaBean;
import com.hildeberto.architect.datasource.business.DatabaseTableBean;
import com.hildeberto.architect.datasource.business.DatabaseViewBean;
import com.hildeberto.architect.datasource.domain.DatabaseInstance;
import com.hildeberto.architect.datasource.domain.DatabaseSchema;
import com.hildeberto.architect.datasource.domain.DatabaseTable;
import com.hildeberto.architect.datasource.domain.DatabaseView;
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
public class SchemaMBean {

    @EJB
    private DatabaseSchemaBean databaseSchemaBean;

    @EJB
    private DatabaseTableBean databaseTableBean;

    @EJB
    private DatabaseViewBean databaseViewBean;

    private List<DatabaseSchema> schemas;
    private List<DatabaseTable> relatedTables;
    private List<DatabaseView> relatedViews;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbId}")
    private Integer dbId;

    @ManagedProperty(value="#{databaseFilterMBean}")
    private DatabaseFilterMBean databaseFilterMBean;

    private DatabaseSchema schema;

    public List<DatabaseInstance> getDatabases() {
        return this.databaseFilterMBean.getDatabaseInstances();
    }

    public List<DatabaseSchema> getSchemas() {
        if(schemas == null && this.databaseFilterMBean.getDatabaseInstance() != null) {
            schemas = databaseSchemaBean.findByDatabaseInstance(this.databaseFilterMBean.getDatabaseInstance());
        }
        return schemas;
    }

    public List<DatabaseTable> getRelatedTables() {
        if(relatedTables == null && schema != null) {
            relatedTables = databaseTableBean.findByDatabaseSchema(schema, LifecycleState.INUSE);
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
        this.dbId = dbId;
    }

    public Integer getSelectedDatabase() {
        return this.databaseFilterMBean.getSelectedDatabaseInstance();
    }

    public void setSelectedDatabase(Integer selectedDatabase) {
        this.databaseFilterMBean.setSelectedDatabaseInstance(selectedDatabase);
    }

    public void setDatabaseFilterMBean(DatabaseFilterMBean databaseFilterMBean) {
        this.databaseFilterMBean = databaseFilterMBean;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.schema = databaseSchemaBean.find(id);
            this.databaseFilterMBean.setSelectedDatabaseInstance(this.schema.getDatabaseInstance().getId());
        }
        else {
            this.schema = new DatabaseSchema();
            if(this.databaseFilterMBean.getDatabaseInstance() != null) {
                this.schema.setDatabaseInstance(this.databaseFilterMBean.getDatabaseInstance());
            }
        }
    }

    public String save() {
        this.schema.setDatabaseInstance(this.databaseFilterMBean.getDatabaseInstance());

        databaseSchemaBean.save(this.schema);
        return "schemas";
    }
}