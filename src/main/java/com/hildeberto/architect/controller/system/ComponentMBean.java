package com.hildeberto.architect.controller.system;

import com.hildeberto.architect.business.system.ComponentBean;
import com.hildeberto.architect.domain.system.Application;
import com.hildeberto.architect.domain.system.Component;
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
public class ComponentMBean {

    @EJB
    private ComponentBean componentBean;

    private List<Component> components;

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

    private Component component;

    public List<Component> getComponents() {
        if(components == null) {
            if(applicationFilterMBean.getSelectedApplication() != null && applicationFilterMBean.getSelectedModule() == null && applicationFilterMBean.getSelectedPackage() == null) {
                Application application = applicationFilterMBean.getApplication();
                components = componentBean.findByApplication(application);
            }
            else if(applicationFilterMBean.getSelectedModule() != null && applicationFilterMBean.getSelectedPackage() == null) {
                Module module = applicationFilterMBean.getModule();
                components = componentBean.findByModule(module);
            }
            else if(applicationFilterMBean.getSelectedPackage() != null) {
                Package pack = applicationFilterMBean.getPackage();
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

    @PostConstruct
    public void load() {
        if(id != null) {
            this.component = componentBean.find(id);
            this.applicationFilterMBean.setSelectedApplication(this.component.getApplication().getId());
            if(this.component.getModule() != null) {
                this.applicationFilterMBean.setSelectedModule(this.component.getModule().getId());
            }
            if(this.component.getPackage() != null) {
                this.applicationFilterMBean.setSelectedPackage(this.component.getPackage().getId());
            }
        }
        else {
            this.component = new Component();
            if(appId != null) {
                applicationFilterMBean.setSelectedApplication(appId);
                this.component.setApplication(applicationFilterMBean.getApplication());
            }
            if(modId != null) {
                applicationFilterMBean.setSelectedModule(modId);
                this.component.setModule(applicationFilterMBean.getModule());
            }
            if(pkgId != null) {
                applicationFilterMBean.setSelectedPackage(pkgId);
                this.component.setPackage(applicationFilterMBean.getPackage());
            }
        }
    }

    public String save() {
        this.component.setApplication(applicationFilterMBean.getApplication());
        this.component.setModule(applicationFilterMBean.getModule());
        this.component.setPackage(applicationFilterMBean.getPackage());

        componentBean.save(this.component);
        return "artifacts?faces-redirect=true&appId=" + this.component.getApplication().getId() +
                                            "&modId=" + this.component.getModule().getId() +
                                            "&pkgId=" + this.component.getPackage().getId() +
                                            "&tab=1";
    }

    public String saveAndCreateNew() {
        this.component.setApplication(applicationFilterMBean.getApplication());
        this.component.setModule(applicationFilterMBean.getModule());
        this.component.setPackage(applicationFilterMBean.getPackage());

        componentBean.save(this.component);
        return "component_form?faces-redirect=true&appId=" + this.component.getApplication().getId() +
                                            "&modId=" + this.component.getModule().getId() +
                                            "&pkgId=" + this.component.getPackage().getId() +
                                            "&tab=1";
    }
}