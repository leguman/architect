package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.EntityClassBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.EntityClass;
import com.hildeberto.architect.domain.Module;
import com.hildeberto.architect.domain.Package;
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
        
    private List<EntityClass> entityClasses;
    
    @ManagedProperty(value="#{packageFilterMBean}")
    private PackageFilterMBean packageFilterMBean;
        
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
            if(packageFilterMBean.getSelectedApplication() != null && packageFilterMBean.getSelectedModule() == null && packageFilterMBean.getSelectedPackage() == null) {
                Application application = packageFilterMBean.getApplication();
                entityClasses = entityClassBean.findByApplication(application);
            }
            else if(packageFilterMBean.getSelectedModule() != null && packageFilterMBean.getSelectedPackage() == null) {
                Module module = packageFilterMBean.getModule();
                entityClasses = entityClassBean.findByModule(module);
            }
            else if(packageFilterMBean.getSelectedPackage() != null) {
                Package pack = packageFilterMBean.getPackage();
                entityClasses = entityClassBean.findByPackage(pack);
            }
        }
        return entityClasses;
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
    
    public void setPackageFilterMBean(PackageFilterMBean packageFilterMBean) {
        this.packageFilterMBean = packageFilterMBean;
    }
    
    public List<Application> getApplications() {
        return this.packageFilterMBean.getApplications();
    }
    
    public List<Module> getModules() {
        return this.packageFilterMBean.getModules();
    }
    
    public List<Package> getPackages() {
        return this.packageFilterMBean.getPackages();
    }
        
    public Integer getSelectedApplication() {
        return this.packageFilterMBean.getSelectedApplication();
    }
    
    public void setSelectedApplication(Integer selectedApplication) {
        this.packageFilterMBean.setSelectedApplication(selectedApplication);
    }
    
    public Integer getSelectedModule() {
        return this.packageFilterMBean.getSelectedModule();
    }
    
    public void setSelectedModule(Integer selectedModule) {
        this.packageFilterMBean.setSelectedModule(selectedModule);
    }
    
    public Integer getSelectedPackage() {
        return this.packageFilterMBean.getSelectedPackage();
    }
    
    public void setSelectedPackage(Integer selectedPackage) {
        this.packageFilterMBean.setSelectedPackage(selectedPackage);
    }
                
    @PostConstruct
    public void load() {
        if(id != null) {
            this.entityClass = entityClassBean.find(id);
            this.packageFilterMBean.setSelectedApplication(this.entityClass.getApplication().getId());
            if(this.entityClass.getModule() != null) {
                this.packageFilterMBean.setSelectedModule(this.entityClass.getModule().getId());
            }
            if(this.entityClass.getPackage() != null) {
                this.packageFilterMBean.setSelectedPackage(this.entityClass.getPackage().getId());
            }
        }
        else {
            this.entityClass = new EntityClass();
            if(appId != null) {
                packageFilterMBean.setSelectedApplication(appId);
                this.entityClass.setApplication(packageFilterMBean.getApplication());
            }
            if(modId != null) {
                packageFilterMBean.setSelectedModule(modId);
                this.entityClass.setModule(packageFilterMBean.getModule());
            }
            if(pkgId != null) {
                packageFilterMBean.setSelectedPackage(pkgId);
                this.entityClass.setPackage(packageFilterMBean.getPackage());
            }
        }
    }
    
    public String save() {
        this.entityClass.setApplication(packageFilterMBean.getApplication());
        this.entityClass.setModule(packageFilterMBean.getModule());
        this.entityClass.setPackage(packageFilterMBean.getPackage());
                
        entityClassBean.save(this.entityClass);
        return "artifacts?faces-redirect=true&appId=" + this.entityClass.getApplication().getId() + "&modId=" + this.entityClass.getModule().getId();
    }
}