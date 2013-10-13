package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.DatabaseViewBean;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
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
public class ViewMBean {
 
    @EJB
    private DatabaseViewBean databaseViewBean;
            
    private List<DatabaseView> views;
    
    @ManagedProperty(value="#{elementFilterMBean}")
    private ElementFilterMBean elementFilterMBean;
        
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;
    
    @ManagedProperty(value="#{param.schId}")
    private Integer schId;
        
    private DatabaseView view;
          
    public List<DatabaseView> getViews() {
        if(views == null) {
            if(elementFilterMBean.getSelectedDatabaseInstance() != null && elementFilterMBean.getSelectedDatabaseSchema() == null) {
                DatabaseInstance databaseInstance = elementFilterMBean.getDatabaseInstance();
                views = databaseViewBean.findByDatabaseInstance(databaseInstance);
            }
            else if(elementFilterMBean.getSelectedDatabaseSchema() != null) {
                DatabaseSchema databaseSchema = elementFilterMBean.getDatabaseSchema();
                views = databaseViewBean.findByDatabaseSchema(databaseSchema);
            }
        }
        return views;
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
    
    public DatabaseView getView() {
        return this.view;
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
            this.view = databaseViewBean.find(id);
            this.elementFilterMBean.setSelectedDatabaseInstance(this.view.getDatabaseInstance().getId());
            if(this.view.getDatabaseSchema() != null) {
                this.elementFilterMBean.setSelectedDatabaseSchema(this.view.getDatabaseSchema().getId());
            }
        }
        else {
            this.view = new DatabaseView();
            if(dbiId != null) {
                elementFilterMBean.setSelectedDatabaseInstance(dbiId);
                this.view.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
            }
            if(schId != null) {
                elementFilterMBean.setSelectedDatabaseSchema(schId);
                this.view.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
            }
        }
    }
    
    public String save() {
        this.view.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
        this.view.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
        
        databaseViewBean.save(this.view);
        return "elements?faces-redirect=true&dbiId=" + this.view.getDatabaseInstance().getId() + "&schId=" + this.view.getDatabaseSchema().getId() + "&tab=1";
    }
    
    public String saveAndCreateNew() {
        this.view.setDatabaseInstance(elementFilterMBean.getDatabaseInstance());
        this.view.setDatabaseSchema(elementFilterMBean.getDatabaseSchema());
        
        databaseViewBean.save(this.view);
        return "view_form?faces-redirect=true&dbiId=" + this.view.getDatabaseInstance().getId() + "&schId=" + this.view.getDatabaseSchema().getId();
    }
}