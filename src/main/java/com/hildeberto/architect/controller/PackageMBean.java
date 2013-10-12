package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.PackageBean;
import com.hildeberto.architect.domain.Application;
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
public class PackageMBean {
 
    @EJB
    private PackageBean packageBean;
        
    private List<Package> packages;
    private List<Module> relatedModules;
    
    @ManagedProperty(value="#{packageFilterMBean}")
    private PackageFilterMBean packageFilterMBean;
        
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    @ManagedProperty(value="#{param.appId}")
    private Integer appId;
    
    @ManagedProperty(value="#{param.modId}")
    private Integer modId;
        
    private Package pack;
          
    public List<Package> getPackages() {
        if(packages == null) {
            if(packageFilterMBean.getSelectedApplication() != null && packageFilterMBean.getSelectedModule() == null) {
                Application application = packageFilterMBean.getApplication();
                packages = packageBean.findByApplication(application);
            }
            else if(packageFilterMBean.getSelectedModule() != null) {
                Module module = packageFilterMBean.getModule();
                packages = packageBean.findByModule(module);
            }
        }
        return packages;
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
    
    public Package getPackage() {
        return this.pack;
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
    
    public List<Module> getRelatedModules() {
        if(this.relatedModules == null && this.pack != null) {
            this.relatedModules = packageBean.findModulesByPackage(pack);
        }
        return this.relatedModules;
    }
            
    @PostConstruct
    public void load() {
        if(id != null) {
            this.pack = packageBean.find(id);
            this.packageFilterMBean.setSelectedApplication(this.pack.getApplication().getId());
            if(this.pack.getModule() != null) {
                this.packageFilterMBean.setSelectedModule(this.pack.getModule().getId());
            }
        }
        else {
            this.pack = new Package();
            if(appId != null) {
                packageFilterMBean.setSelectedApplication(appId);
                this.pack.setApplication(packageFilterMBean.getApplication());
            }
            if(modId != null) {
                packageFilterMBean.setSelectedModule(modId);
                this.pack.setModule(packageFilterMBean.getModule());
            }
        }
    }
    
    public String save() {
        this.pack.setApplication(packageFilterMBean.getApplication());
        this.pack.setModule(packageFilterMBean.getModule());
        
        packageBean.save(this.pack);
        return "packages?faces-redirect=true&appId=" + this.pack.getApplication().getId() + "&modId=" + this.pack.getModule().getId();
    }
}