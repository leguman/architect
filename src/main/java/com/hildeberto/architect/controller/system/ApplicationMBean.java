package com.hildeberto.architect.controller.system;

import com.hildeberto.architect.business.system.ApplicationBean;
import com.hildeberto.architect.business.system.ModuleBean;
import com.hildeberto.architect.business.system.PackageBean;
import com.hildeberto.architect.domain.system.Application;
import com.hildeberto.architect.domain.system.Module;
import com.hildeberto.architect.domain.system.Package;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Hildeberto Mendonca
 */
@Named
@RequestScoped
public class ApplicationMBean {
 
    @EJB
    private ApplicationBean applicationBean;
    
    @EJB
    private ModuleBean moduleBean;
    
    @EJB
    private PackageBean packageBean;
    
    private List<Application> applications;
    private List<Module> modules;
    private List<Package> packages;
    
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    private Application application;
    
    public List<Application> getApplications() {
        if(applications == null) {
            applications = applicationBean.findAll();
        }
        return applications;
    }
    
    public List<Module> getModules() {
        if(modules == null && application != null) {
            modules = moduleBean.findByApplication(application);
        }
        return modules;
    }
    
    public List<Package> getPackages() {
        if(this.packages == null && this.application.getId() != null) {
            this.packages = packageBean.findByApplication(this.application);
        }
        return this.packages;
    }
        
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Application getApplication() {
        return this.application;
    }
    
    @PostConstruct
    public void load() {
        if(id != null) {
            this.application = applicationBean.find(id);
        }
        else {
            this.application = new Application();
        }
    }
    
    public String save() {
        applicationBean.save(this.application);
        return "applications";
    }
}