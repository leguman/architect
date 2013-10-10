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
public class ModuleMBean {
 
    @EJB
    private ModuleBean moduleBean;
    
    @EJB
    private ApplicationBean applicationBean;
    
    private List<Module> modules;
    private List<Module> macroModules;
    private List<Module> subModules;
    private List<Application> applications;
        
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    @ManagedProperty(value="#{param.appId}")
    private Integer selectedApplication;
    
    private Integer selectedSubModuleOf;
    
    private Module module;
    
    public List<Application> getApplications() {
        if(this.applications == null) {
            this.applications = applicationBean.findAll();
        }
        return this.applications;
    }
    
    public List<Module> getModules() {
        if(modules == null && selectedApplication != null) {
            Application application = new Application(selectedApplication);
            modules = moduleBean.findByApplication(application);
        }
        return modules;
    }
    
    public List<Module> getSubModules() {
        if(subModules == null && this.module.getId() != null) {
            subModules = moduleBean.findSubModules(module);
        }
        return subModules;
    }
    
    public List<Module> getMacroModules() {
        if(macroModules == null && selectedApplication != null) {
            Application application = new Application(selectedApplication);
            if(this.module.getId() != null) {
                macroModules = moduleBean.findSubModulesByApplication(application, this.module);
            }
            else {
                macroModules = moduleBean.findSubModulesByApplication(application, null);
            }
        }
        return macroModules;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setAppId(Integer appId) {
        this.selectedApplication = appId;
    }

    public Integer getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Integer selectedApplication) {
        this.selectedApplication = selectedApplication;
    }

    public Integer getSelectedSubModuleOf() {
        return selectedSubModuleOf;
    }

    public void setSelectedSubModuleOf(Integer selectedSubModuleOf) {
        this.selectedSubModuleOf = selectedSubModuleOf;
    }
    
    @PostConstruct
    public void load() {
        if(id != null) {
            this.module = moduleBean.find(id);
            this.selectedApplication = this.module.getApplication().getId();
            if(this.module.getSubModuleOf() != null) {
                this.selectedSubModuleOf = this.module.getSubModuleOf().getId();
            }
        }
        else {
            this.module = new Module();
            if(selectedApplication != null) {
                this.module.setApplication(applicationBean.find(selectedApplication));
            }
        }
    }
    
    public String save() {
        Application application = applicationBean.find(selectedApplication);
        this.module.setApplication(application);
        
        if(selectedSubModuleOf != null) {
            Module macroModule = moduleBean.find(selectedSubModuleOf);
            this.module.setSubModuleOf(macroModule);
        }
        
        moduleBean.save(this.module);
        return "modules";
    }
}