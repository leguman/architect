package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.ApplicationBean;
import com.hildeberto.architect.business.ModuleBean;
import com.hildeberto.architect.business.PackageBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Module;
import com.hildeberto.architect.domain.Package;
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
public class ApplicationFilterMBean {
    
    @EJB
    private ApplicationBean applicationBean;
    
    @EJB
    private ModuleBean moduleBean;
    
    @EJB
    private PackageBean packageBean;
    
    private List<Application> applications;
    private List<Module> modules;
    private List<Package> packages;
    
    private Integer selectedApplication;
    private Integer selectedModule;
    private Integer selectedPackage;
            
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
    
    public List<Package> getPackages() {
        if(this.selectedApplication != null && this.selectedModule == null) {
            Application application = new Application(this.selectedApplication);
            this.packages = packageBean.findByApplication(application);
        }
        else if(this.selectedModule != null) {
            Module module = new Module(this.selectedModule);
            this.packages = packageBean.findByModule(module);
        }
        return this.packages;
    }

    public Integer getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Integer selectedApplication) {
        this.selectedApplication = selectedApplication;
    }    

    public Integer getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(Integer selectedModule) {
        this.selectedModule = selectedModule;
    }

    public Integer getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(Integer selectedPackage) {
        this.selectedPackage = selectedPackage;
    }
    
    public Application getApplication() {
        if(this.selectedApplication != null) {
            return applicationBean.find(this.selectedApplication);
        }
        else {
            return null;
        }
    }
    
    public Module getModule() {
        if(this.selectedModule != null) {
            return moduleBean.find(this.selectedModule);
        }
        else {
            return null;
        }
    }
    
    public Package getPackage() {
        if(this.selectedPackage != null) {
            return packageBean.find(this.selectedPackage);
        }
        else {
            return null;
        }
    }
}