package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseTableBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseTable;
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

    private List<DatabaseTable> tables;

    @ManagedProperty(value="#{databaseFilterMBean}")
    private DatabaseFilterMBean databaseFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;

    @ManagedProperty(value="#{param.schId}")
    private Integer schId;

    private DatabaseTable table;

    public List<DatabaseTable> getTables() {
        if(tables == null) {
            if(databaseFilterMBean.getSelectedDatabaseInstance() != null && databaseFilterMBean.getSelectedDatabaseSchema() == null) {
                DatabaseInstance databaseInstance = databaseFilterMBean.getDatabaseInstance();
                tables = databaseTableBean.findByDatabaseInstance(databaseInstance);
            }
            else if(databaseFilterMBean.getSelectedDatabaseSchema() != null) {
                DatabaseSchema databaseSchema = databaseFilterMBean.getDatabaseSchema();
                tables = databaseTableBean.findByDatabaseSchema(databaseSchema);
            }
        }
        return tables;
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

    @PostConstruct
    public void load() {
        if(id != null) {
            this.table = databaseTableBean.find(id);
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
        }
    }

    public String save() {
        this.table.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseTableBean.save(this.table);
        return "elements?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() + "&schId=" + this.table.getDatabaseSchema().getId();
    }

    public String saveAndCreateNew() {
        this.table.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseTableBean.save(this.table);
        return "table_form?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() + "&schId=" + this.table.getDatabaseSchema().getId();
    }

    public String remove() {
        databaseTableBean.remove(this.id);
        return "elements?faces-redirect=true&dbiId=" + this.dbiId + "&schId=" + this.schId;
    }
}