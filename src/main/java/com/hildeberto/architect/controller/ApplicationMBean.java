package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.ApplicationBean;
import com.hildeberto.architect.business.ModuleBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Module;
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
public class ApplicationMBean {
 
    @EJB
    private ApplicationBean applicationBean;
    
    @EJB
    private ModuleBean moduleBean;
    
    private List<Application> applications;
    private List<Module> modules;
    
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