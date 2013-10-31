package com.hildeberto.architect.controller.datasource;

import com.hildeberto.architect.business.datasource.DatabaseViewBean;
import com.hildeberto.architect.business.system.EntityClassBean;
import com.hildeberto.architect.business.LifecycleBean;
import com.hildeberto.architect.domain.datasource.DatabaseInstance;
import com.hildeberto.architect.domain.datasource.DatabaseSchema;
import com.hildeberto.architect.domain.datasource.DatabaseView;
import com.hildeberto.architect.domain.system.EntityClass;
import com.hildeberto.architect.domain.datasource.LifecycleView;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Hildeberto Mendonca
 */
@Named
@RequestScoped
public class ViewMBean {

    @EJB
    private DatabaseViewBean databaseViewBean;

    @EJB
    private LifecycleBean lifecycleBean;

    @EJB
    private EntityClassBean entityClassBean;

    private List<DatabaseView> views;
    private List<LifecycleView> lifecycleView;

    @Inject
    private DatabaseFilterMBean databaseFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.dbiId}")
    private Integer dbiId;

    @ManagedProperty(value="#{param.schId}")
    private Integer schId;

    private DatabaseView view;
    private EntityClass entityClass;

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

    public List<LifecycleView> getLifecycleView() {
        if(this.lifecycleView == null && this.view != null && this.view.getId() != null) {
            this.lifecycleView = lifecycleBean.findLifecycleView(view);
        }
        return this.lifecycleView;
    }

    public EntityClass getEntityClass() {
        if(this.entityClass == null && this.view != null && this.view.getId() != null) {
            this.entityClass = entityClassBean.findByMappedDatabaseElement(this.view);
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