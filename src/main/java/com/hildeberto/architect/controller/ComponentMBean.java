package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.ComponentBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Component;
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
public class ComponentMBean {

    @EJB
    private ComponentBean componentBean;

    private List<Component> components;

    @ManagedProperty(value="#{packageFilterMBean}")
    private ApplicationFilterMBean packageFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.appId}")
    private Integer appId;

    @ManagedProperty(value="#{param.modId}")
    private Integer modId;

    @ManagedProperty(value="#{param.pkgId}")
    private Integer pkgId;

    private Component component;

    public List<Component> getComponents() {
        if(components == null) {
            if(packageFilterMBean.getSelectedApplication() != null && packageFilterMBean.getSelectedModule() == null && packageFilterMBean.getSelectedPackage() == null) {
                Application application = packageFilterMBean.getApplication();
                components = componentBean.findByApplication(application);
            }
            else if(packageFilterMBean.getSelectedModule() != null && packageFilterMBean.getSelectedPackage() == null) {
                Module module = packageFilterMBean.getModule();
                components = componentBean.findByModule(module);
            }
            else if(packageFilterMBean.getSelectedPackage() != null) {
                Package pack = packageFilterMBean.getPackage();
                components = componentBean.findByPackage(pack);
            }
        }
        return components;
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

    public Component getComponent() {
        return this.component;
    }

    public void setPackageFilterMBean(ApplicationFilterMBean packageFilterMBean) {
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
            this.component = componentBean.find(id);
            this.packageFilterMBean.setSelectedApplication(this.component.getApplication().getId());
            if(this.component.getModule() != null) {
                this.packageFilterMBean.setSelectedModule(this.component.getModule().getId());
            }
            if(this.component.getPackage() != null) {
                this.packageFilterMBean.setSelectedPackage(this.component.getPackage().getId());
            }
        }
        else {
            this.component = new Component();
            if(appId != null) {
                packageFilterMBean.setSelectedApplication(appId);
                this.component.setApplication(packageFilterMBean.getApplication());
            }
            if(modId != null) {
                packageFilterMBean.setSelectedModule(modId);
                this.component.setModule(packageFilterMBean.getModule());
            }
            if(pkgId != null) {
                packageFilterMBean.setSelectedPackage(pkgId);
                this.component.setPackage(packageFilterMBean.getPackage());
            }
        }
    }

    public String save() {
        this.component.setApplication(packageFilterMBean.getApplication());
        this.component.setModule(packageFilterMBean.getModule());
        this.component.setPackage(packageFilterMBean.getPackage());

        componentBean.save(this.component);
        return "artifacts?faces-redirect=true&appId=" + this.component.getApplication().getId() +
                                            "&modId=" + this.component.getModule().getId() +
                                            "&pkgId=" + this.component.getPackage().getId() +
                                            "&tab=1";
    }
}