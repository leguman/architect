package com.hildeberto.architect.system.controller;

import com.hildeberto.architect.system.business.ModuleBean;
import com.hildeberto.architect.system.business.PackageBean;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.system.domain.Module;
import com.hildeberto.architect.system.domain.Package;
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
    private PackageBean packageBean;

    private List<Module> modules;
    private List<Module> macroModules;
    private List<Module> subModules;
    private List<Package> packages;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.appId}")
    private Integer appId;

    @ManagedProperty(value="#{applicationFilterMBean}")
    private ApplicationFilterMBean applicationFilterMBean;

    private Integer selectedSubModuleOf;

    private Module module;

    public List<Application> getApplications() {
        return this.applicationFilterMBean.getApplications();
    }

    public List<Module> getModules() {
        if(modules == null && applicationFilterMBean.getApplication() != null) {
            modules = moduleBean.findByApplication(applicationFilterMBean.getApplication());
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
        if(macroModules == null && applicationFilterMBean.getApplication() != null) {
            if(this.module.getId() != null) {
                macroModules = moduleBean.findSubModulesByApplication(applicationFilterMBean.getApplication(), this.module);
            }
            else {
                macroModules = moduleBean.findSubModulesByApplication(applicationFilterMBean.getApplication(), null);
            }
        }
        return macroModules;
    }

    public List<Package> getPackages() {
        if(this.packages == null && this.module.getId() != null) {
            this.packages = packageBean.findByModule(this.module);
        }
        return this.packages;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Module getModule() {
        return this.module;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getSelectedApplication() {
        return applicationFilterMBean.getSelectedApplication();
    }

    public void setSelectedApplication(Integer selectedApplication) {
        this.applicationFilterMBean.setSelectedApplication(selectedApplication);
    }

    public Integer getSelectedSubModuleOf() {
        return selectedSubModuleOf;
    }

    public void setSelectedSubModuleOf(Integer selectedSubModuleOf) {
        this.selectedSubModuleOf = selectedSubModuleOf;
    }

    public void setApplicationFilterMBean(ApplicationFilterMBean applicationFilterMBean) {
        this.applicationFilterMBean = applicationFilterMBean;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.module = moduleBean.find(id);
            this.applicationFilterMBean.setSelectedApplication(this.module.getApplication().getId());
            if(this.module.getSubModuleOf() != null) {
                this.selectedSubModuleOf = this.module.getSubModuleOf().getId();
            }
        }
        else {
            this.module = new Module();
            if(this.applicationFilterMBean.getApplication() != null) {
                this.module.setApplication(this.applicationFilterMBean.getApplication());
            }
        }
    }

    public String save() {
        this.module.setApplication(this.applicationFilterMBean.getApplication());

        if(selectedSubModuleOf != null) {
            Module macroModule = moduleBean.find(selectedSubModuleOf);
            this.module.setSubModuleOf(macroModule);
        }

        moduleBean.save(this.module);
        return "modules";
    }
}