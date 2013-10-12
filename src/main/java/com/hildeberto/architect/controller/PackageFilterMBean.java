package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.ApplicationBean;
import com.hildeberto.architect.business.ModuleBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Module;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mendoncafilh
 */
@ManagedBean
@ViewScoped
public class PackageFilterMBean {
    
    @EJB
    private ApplicationBean applicationBean;
    
    @EJB
    private ModuleBean moduleBean;
    
    private List<Application> applications;
    private List<Module> modules;
    
    private Integer selectedApplication;
    private Integer selectedModule;
            
    public List<Application> getApplications() {
        if(this.applications == null) {
            this.applications = applicationBean.findAll();
        }
        return this.applications;
    }

    public List<Module> getModules() {
        if(this.selectedApplication != null) {
            Application application = new Application(this.selectedApplication);
            this.modules = moduleBean.findByApplication(application);
        }
        return this.modules;
    }

    public Integer getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Integer selectedApplication) {
        this.selectedApplication = selectedApplication;
    }
    
    public Application getApplication() {
        if(this.selectedApplication != null) {
            return applicationBean.find(this.selectedApplication);
        }
        else {
            return null;
        }
    }

    public Integer getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(Integer selectedModule) {
        this.selectedModule = selectedModule;
    }
    
    public Module getModule() {
        if(this.selectedModule != null) {
            return moduleBean.find(this.selectedModule);
        }
        else {
            return null;
        }
    }
}