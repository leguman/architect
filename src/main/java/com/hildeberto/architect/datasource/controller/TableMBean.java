package com.hildeberto.architect.datasource.controller;

import com.hildeberto.architect.datasource.business.DatabaseInstanceBean;
import com.hildeberto.architect.datasource.business.DatabaseSchemaBean;
import com.hildeberto.architect.datasource.business.DatabaseTableBean;
import com.hildeberto.architect.datasource.domain.*;
import com.hildeberto.architect.system.business.EntityClassBean;
import com.hildeberto.architect.business.LifecycleBean;
import com.hildeberto.architect.system.domain.EntityClass;
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
public class TableMBean {

    @EJB
    private DatabaseTableBean databaseTableBean;
    
    @EJB
    private DatabaseSchemaBean databaseSchemaBean;

    @EJB
    private DatabaseInstanceBean databaseInstanceBean;
    
    @EJB
    private EntityClassBean entityClassBean;

    @EJB
    private LifecycleBean lifecycleBean;

    private List<DatabaseTable> tables;
    private List<DatabaseTable> referencesTo;
    private List<DatabaseTable> referencesFrom;
    private List<LifecycleTable> lifecycleTable;
    private List<ElementColumn> columns;

    @ManagedProperty(value="#{databaseFilterMBean}")
    private DatabaseFilterMBean databaseFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;

    @ManagedProperty(value="#{param.schId}")
    private Integer schId;

    @ManagedProperty(value="#{param.schema}")
    private String schemaName;

    @ManagedProperty(value = "#{param.name}")
    private String tableName;
    
    @ManagedProperty(value="#{param.state}")
    private String state;

    private DatabaseTable table;
    private EntityClass entityClass;
    private LifecycleState selectedState;

    public List<DatabaseTable> getTables() {
        if(tables == null) {
            if(databaseFilterMBean.getSelectedDatabaseInstance() != null && databaseFilterMBean.getSelectedDatabaseSchema() == null) {
                DatabaseInstance databaseInstance = databaseFilterMBean.getDatabaseInstance();

                tables = databaseTableBean.findByDatabaseInstance(databaseInstance, selectedState);
            }
            else if(databaseFilterMBean.getSelectedDatabaseSchema() != null) {
                DatabaseSchema databaseSchema = databaseFilterMBean.getDatabaseSchema();
                tables = databaseTableBean.findByDatabaseSchema(databaseSchema, selectedState);
            }
        }
        return tables;
    }

    public List<LifecycleTable> getLifecycleTable() {
        if(this.lifecycleTable == null && this.table != null && this.table.getId() != null) {
            this.lifecycleTable = lifecycleBean.findLifecycleTable(table);
        }
        return this.lifecycleTable;
    }

    public List<ElementColumn> getColumns() {
        if(this.columns == null && this.table != null && this.table.getId() != null) {
            this.columns = databaseTableBean.findPhysicalColumns(this.table);
        }
        return this.columns;
    }

    public List<DatabaseTable> getReferencesTo() {
        if(this.referencesTo == null &&  this.table != null && this.table.getId() != null) {
            this.referencesTo = databaseTableBean.findReferencesTo(this.table);
        }
        return this.referencesTo;
    }

    public List<DatabaseTable> getReferencesFrom() {
        if(this.referencesFrom == null &&  this.table != null && this.table.getId() != null) {
            this.referencesFrom = databaseTableBean.findReferencesFrom(this.table);
        }
        return this.referencesFrom;
    }

    public EntityClass getEntityClass() {
        if(this.entityClass == null && this.table != null && this.table.getId() != null) {
            this.entityClass = entityClassBean.findByMappedDatabaseElement(this.table);
        }
        return this.entityClass;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDbiId(Integer dbiId) {
        this.dbiId = dbiId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public DatabaseTable getTable() {
        return this.table;
    }

    public void setDatabaseFilterMBean(DatabaseFilterMBean databaseFilterMBean) {
        this.databaseFilterMBean = databaseFilterMBean;
    }

    public List<DatabaseInstance> getDatabaseInstances() {
        return this.databaseFilterMBean.getDatabaseInstances();
    }

    public List<DatabaseSchema> getDatabaseSchemas() {
        return this.databaseFilterMBean.getDatabaseSchemas();
    }

    public Integer getSelectedDatabaseInstance() {
        return this.databaseFilterMBean.getSelectedDatabaseInstance();
    }

    public void setSelectedDatabaseInstance(Integer selectedDatabaseInstance) {
        this.databaseFilterMBean.setSelectedDatabaseInstance(selectedDatabaseInstance);
    }

    public Integer getSelectedDatabaseSchema() {
        return this.databaseFilterMBean.getSelectedDatabaseSchema();
    }

    public void setSelectedDatabaseSchema(Integer selectedDatabaseSchema) {
        this.databaseFilterMBean.setSelectedDatabaseSchema(selectedDatabaseSchema);
    }

    public LifecycleState getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(LifecycleState selectedState) {
        this.selectedState = selectedState;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.table = databaseTableBean.find(id);
            this.databaseFilterMBean.setSelectedDatabaseInstance(this.table.getDatabaseInstance().getId());
            if(this.table.getDatabaseSchema() != null) {
                this.databaseFilterMBean.setSelectedDatabaseSchema(this.table.getDatabaseSchema().getId());
            }
        }
        else if(tableName != null && dbiId != null) {
            DatabaseInstance database = databaseInstanceBean.find(dbiId);
            if(schemaName != null) {
                DatabaseSchema schema = databaseSchemaBean.findByName(database, schemaName);
                this.table = databaseTableBean.findByName(database, schema, tableName);
            }
            else {
                this.table = databaseTableBean.findByName(database, null, tableName);
            }
            this.databaseFilterMBean.setSelectedDatabaseInstance(this.table.getDatabaseInstance().getId());
            if(this.table.getDatabaseSchema() != null) {
                this.databaseFilterMBean.setSelectedDatabaseSchema(this.table.getDatabaseSchema().getId());
            }
        }
        else {
            this.table = new DatabaseTable();
            if(dbiId != null) {
                databaseFilterMBean.setSelectedDatabaseInstance(dbiId);
                this.table.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
            }
            if(schId != null) {
                databaseFilterMBean.setSelectedDatabaseSchema(schId);
                this.table.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());
            }
            if(state != null) {
                this.selectedState = LifecycleState.valueOf(state);
            }
        }
    }

    public String save() {
        this.table.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseTableBean.save(this.table);
        return "elements?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() +
                (this.table.getDatabaseSchema() != null ? "&schId=" + this.table.getDatabaseSchema().getId(): "");
    }

    public String saveAndCreateNew() {
        this.table.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseTableBean.save(this.table);
        return "table_form?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() +
                (this.table.getDatabaseSchema() != null ? "&schId=" + this.table.getDatabaseSchema().getId(): "");
    }

    public String remove() {
        databaseTableBean.remove(this.id);
        return "elements?faces-redirect=true&dbiId=" + this.dbiId + "&schId=" + this.schId;
    }
}