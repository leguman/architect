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
    
    @ManagedProperty(value="#{elementFilterMBean}")
    private ElementFilterMBean elementFilterMBean;
        
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;
    
    @ManagedProperty(value="#{param.schId}")
    private Integer schId;
        
    private DatabaseTable table;
          
    public List<DatabaseTable> getTables() {
        if(tables == null) {
            if(elementFilterMBean.getSelectedDatabaseInstance() != null && elementFilterMBean.getSelectedDatabaseSchema() == null) {
                DatabaseInstance databaseInstance = elementFilterMBean.getDatabaseInstance();
                tables = databaseTableBean.findByDatabaseInstance(databaseInstance);
            }
            else if(elementFilterMBean.getSelectedDatabaseSchema() != null) {
                DatabaseSchema databaseSchema = elementFilterMBean.getDatabaseSchema();
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
    
    public void setElementFilterMBean(ElementFilterMBean elementFilterMBean) {
        this.elementFilterMBean = elementFilterMBean;
    }
    
    public List<DatabaseInstance> getDatabaseInstances() {
        return this.elementFilterMBean.getDatabaseInstances();
    }
    
    public List<DatabaseSchema> getDatabaseSchemas() {
        return this.elementFilterMBean.getDatabaseSchemas();
    }
    
    public Integer getSelectedDatabaseInstance() {
        return this.elementFilterMBean.getSelectedDatabaseInstance();
    }
    
    public void setSelectedDatabaseInstance(Integer selectedDatabaseInstance) {
        this.elementFilterMBean.setSelectedDatabaseInstance(selectedDatabaseInstance);
    }
    
    public Integer getSelectedDatabaseSchema() {
        return this.elementFilterMBean.getSelectedDatabaseSchema();
    }
    
    public void setSelectedDatabaseSchema(Integer selectedDatabaseSchema) {
        this.elementFilterMBean.setSelectedDatabaseSchema(selectedDatabaseSchema);
    }
            
    @PostConstruct
    public void load() {
        if(id != null) {
            this.table = databaseTableBean.find(id);
            this.elementFilterMBean.setSelectedDatabaseInstance(this.table.getDatabaseInstance().getId());
            if(this.table.getDatabaseSchema() != null) {
                this.elementFilterMBean.setSelectedDatabaseSchema(this.table.getDatabaseSchema().getId());
            }
        }
        else {
            this.table = new DatabaseTable();
            if(dbiId != null) {
                elementFilterMBean.setSelectedDatabaseInstance(dbiId);
                this.table.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
            }
            if(schId != null) {
                elementFilterMBean.setSelectedDatabaseSchema(schId);
                this.table.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
            }
        }
    }
    
    public String save() {
        this.table.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
        
        databaseTableBean.save(this.table);
        return "elements?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() + "&schId=" + this.table.getDatabaseSchema().getId();
    }
    
    public String saveAndCreateNew() {
        this.table.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
        this.table.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
        
        databaseTableBean.save(this.table);
        return "table_form?faces-redirect=true&dbiId=" + this.table.getDatabaseInstance().getId() + "&schId=" + this.table.getDatabaseSchema().getId();
    }
    
    public String remove() {
        databaseTableBean.remove(this.id);
        return "elements?faces-redirect=true&dbiId=" + this.dbiId + "&schId=" + this.schId;
    }
}