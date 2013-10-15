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

    @ManagedProperty(value="#{databaseFilterMBean}")
    private DatabaseFilterMBean databaseFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;

    @ManagedProperty(value="#{param.schId}")
    private Integer schId;

    private DatabaseView view;

    public List<DatabaseView> getViews() {
        if(views == null) {
            if(databaseFilterMBean.getSelectedDatabaseInstance() != null && databaseFilterMBean.getSelectedDatabaseSchema() == null) {
                DatabaseInstance databaseInstance = databaseFilterMBean.getDatabaseInstance();
                views = databaseViewBean.findByDatabaseInstance(databaseInstance);
            }
            else if(databaseFilterMBean.getSelectedDatabaseSchema() != null) {
                DatabaseSchema databaseSchema = databaseFilterMBean.getDatabaseSchema();
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
            this.view = databaseViewBean.find(id);
            this.databaseFilterMBean.setSelectedDatabaseInstance(this.view.getDatabaseInstance().getId());
            if(this.view.getDatabaseSchema() != null) {
                this.databaseFilterMBean.setSelectedDatabaseSchema(this.view.getDatabaseSchema().getId());
            }
        }
        else {
            this.view = new DatabaseView();
            if(dbiId != null) {
                databaseFilterMBean.setSelectedDatabaseInstance(dbiId);
                this.view.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
            }
            if(schId != null) {
                databaseFilterMBean.setSelectedDatabaseSchema(schId);
                this.view.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());
            }
        }
    }

    public String save() {
        this.view.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.view.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseViewBean.save(this.view);
        return "elements?faces-redirect=true&dbiId=" + this.view.getDatabaseInstance().getId() + "&schId=" + this.view.getDatabaseSchema().getId() + "&tab=1";
    }

    public String saveAndCreateNew() {
        this.view.setDatabaseInstance(databaseFilterMBean.getDatabaseInstance());
        this.view.setDatabaseSchema(databaseFilterMBean.getDatabaseSchema());

        databaseViewBean.save(this.view);
        return "view_form?faces-redirect=true&dbiId=" + this.view.getDatabaseInstance().getId() + "&schId=" + this.view.getDatabaseSchema().getId();
    }
}