package com.hildeberto.architect.controller.system;

import com.hildeberto.architect.business.datasource.DatabaseElementBean;
import com.hildeberto.architect.business.system.EntityClassBean;
import com.hildeberto.architect.domain.system.Application;
import com.hildeberto.architect.domain.datasource.DatabaseElement;
import com.hildeberto.architect.domain.system.EntityClass;
import com.hildeberto.architect.domain.system.Module;
import com.hildeberto.architect.domain.system.Package;
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
public class EntityClassMBean {

    @EJB
    private EntityClassBean entityClassBean;

    @EJB
    private DatabaseElementBean databaseElementBean;

    private List<EntityClass> entityClasses;
    private List<DatabaseElement> unmappedDatabaseElements;

    private Integer selectedDatabaseElement;

    @ManagedProperty(value="#{applicationFilterMBean}")
    private ApplicationFilterMBean applicationFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.appId}")
    private Integer appId;

    @ManagedProperty(value="#{param.modId}")
    private Integer modId;

    @ManagedProperty(value="#{param.pkgId}")
    private Integer pkgId;

    private EntityClass entityClass;

    public List<EntityClass> getEntityClasses() {
        if(entityClasses == null) {
            if(applicationFilterMBean.getSelectedApplication() != null && applicationFilterMBean.getSelectedModule() == null && applicationFilterMBean.getSelectedPackage() == null) {
                Application application = applicationFilterMBean.getApplication();
                entityClasses = entityClassBean.findByApplication(application);
            }
            else if(applicationFilterMBean.getSelectedModule() != null && applicationFilterMBean.getSelectedPackage() == null) {
                Module module = applicationFilterMBean.getModule();
                entityClasses = entityClassBean.findByModule(module);
            }
            else if(applicationFilterMBean.getSelectedPackage() != null) {
                Package pack = applicationFilterMBean.getPackage();
                entityClasses = entityClassBean.findByPackage(pack);
            }
        }
        return entityClasses;
    }

    public List<? extends DatabaseElement> getUnmappedDatabaseElements() {
        if(this.unmappedDatabaseElements == null) {
            this.unmappedDatabaseElements = databaseElementBean.findNotMappedElements(this.entityClass.getDatabaseElement());
        }
        return this.unmappedDatabaseElements;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public void setModId(Integer modId) {
        this.modId = modId;
    }

    public void setPkgId(Integer pkgId) {
        this.pkgId = pkgId;
    }

    public EntityClass getEntityClass() {
        return this.entityClass;
    }

    public void setApplicationFilterMBean(ApplicationFilterMBean applicationFilterMBean) {
        this.applicationFilterMBean = applicationFilterMBean;
    }

    public List<Application> getApplications() {
        return this.applicationFilterMBean.getApplications();
    }

    public List<Module> getModules() {
        return this.applicationFilterMBean.getModules();
    }

    public List<Package> getPackages() {
        return this.applicationFilterMBean.getPackages();
    }

    public Integer getSelectedApplication() {
        return this.applicationFilterMBean.getSelectedApplication();
    }

    public void setSelectedApplication(Integer selectedApplication) {
        this.applicationFilterMBean.setSelectedApplication(selectedApplication);
    }

    public Integer getSelectedModule() {
        return this.applicationFilterMBean.getSelectedModule();
    }

    public void setSelectedModule(Integer selectedModule) {
        this.applicationFilterMBean.setSelectedModule(selectedModule);
    }

    public Integer getSelectedPackage() {
        return this.applicationFilterMBean.getSelectedPackage();
    }

    public void setSelectedPackage(Integer selectedPackage) {
        this.applicationFilterMBean.setSelectedPackage(selectedPackage);
    }

    public Integer getSelectedDatabaseElement() {
        if(!this.entityClass.suggestedElementName().isEmpty()) {
            DatabaseElement databaseElement = databaseElementBean.findByName(this.entityClass.suggestedElementName());
            if(databaseElement != null) {
                this.selectedDatabaseElement = databaseElement.getId();
            }
        }
        return selectedDatabaseElement;
    }

    public void setSelectedDatabaseElement(Integer selectedDatabaseElement) {
        this.selectedDatabaseElement = selectedDatabaseElement;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.entityClass = entityClassBean.find(id);
            this.applicationFilterMBean.setSelectedApplication(this.entityClass.getApplication().getId());
            if(this.entityClass.getModule() != null) {
                this.applicationFilterMBean.setSelectedModule(this.entityClass.getModule().getId());
            }
            if(this.entityClass.getPackage() != null) {
                this.applicationFilterMBean.setSelectedPackage(this.entityClass.getPackage().getId());
            }
            if(this.entityClass.getDatabaseElement() != null) {
                this.selectedDatabaseElement = this.entityClass.getDatabaseElement().getId();
            }
        }
        else {
            this.entityClass = new EntityClass();
            if(appId != null) {
                applicationFilterMBean.setSelectedApplication(appId);
                this.entityClass.setApplication(applicationFilterMBean.getApplication());
            }
            if(modId != null) {
                applicationFilterMBean.setSelectedModule(modId);
                this.entityClass.setModule(applicationFilterMBean.getModule());
            }
            if(pkgId != null) {
                applicationFilterMBean.setSelectedPackage(pkgId);
                this.entityClass.setPackage(applicationFilterMBean.getPackage());
            }
        }
    }

    public String save() {
        this.entityClass.setApplication(applicationFilterMBean.getApplication());
        this.entityClass.setModule(applicationFilterMBean.getModule());
        this.entityClass.setPackage(applicationFilterMBean.getPackage());

        if(this.selectedDatabaseElement != null) {
            DatabaseElement databaseElement = databaseElementBean.find(this.selectedDatabaseElement);
            this.entityClass.setDatabaseElement(databaseElement);
        }

        entityClassBean.save(this.entityClass);
        return "artifacts?faces-redirect=true&appId=" + this.entityClass.getApplication().getId() +
                                            "&modId=" + this.entityClass.getModule().getId() +
                                            "&pkgId=" + this.entityClass.getPackage().getId();
    }
    
    public String saveAndCreateNew() {
        this.entityClass.setApplication(applicationFilterMBean.getApplication());
        this.entityClass.setModule(applicationFilterMBean.getModule());
        this.entityClass.setPackage(applicationFilterMBean.getPackage());

        if(this.selectedDatabaseElement != null) {
            DatabaseElement databaseElement = databaseElementBean.find(this.selectedDatabaseElement);
            this.entityClass.setDatabaseElement(databaseElement);
        }

        entityClassBean.save(this.entityClass);
        return "entity_class_form?faces-redirect=true&appId=" + this.entityClass.getApplication().getId() +
                                                    "&modId=" + this.entityClass.getModule().getId() +
                                                    "&pkgId=" + this.entityClass.getPackage().getId();
    }
}