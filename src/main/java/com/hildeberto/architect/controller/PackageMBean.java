package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.ComponentBean;
import com.hildeberto.architect.business.EntityClassBean;
import com.hildeberto.architect.business.LayerBean;
import com.hildeberto.architect.business.PackageBean;
import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Component;
import com.hildeberto.architect.domain.EntityClass;
import com.hildeberto.architect.domain.Layer;
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

    @EJB
    private LayerBean layerBean;

    @EJB
    private EntityClassBean entityClassBean;

    @EJB
    private ComponentBean componentBean;

    private List<Package> packages;
    private List<Module> relatedModules;
    private List<EntityClass> relatedEntityClasses;
    private List<Component> relatedComponents;
    private List<Layer> layers;

    @ManagedProperty(value="#{applicationFilterMBean}")
    private ApplicationFilterMBean applicationFilterMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.appId}")
    private Integer appId;

    @ManagedProperty(value="#{param.modId}")
    private Integer modId;

    private Integer selectedLayer;

    private Package pack;

    public List<Package> getPackages() {
        if(packages == null) {
            if(applicationFilterMBean.getSelectedApplication() != null && applicationFilterMBean.getSelectedModule() == null) {
                Application application = applicationFilterMBean.getApplication();
                packages = packageBean.findByApplication(application);
            }
            else if(applicationFilterMBean.getSelectedModule() != null) {
                Module module = applicationFilterMBean.getModule();
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

    public void setApplicationFilterMBean(ApplicationFilterMBean applicationFilterMBean) {
        this.applicationFilterMBean = applicationFilterMBean;
    }

    public List<Application> getApplications() {
        return this.applicationFilterMBean.getApplications();
    }

    public List<Module> getModules() {
        return this.applicationFilterMBean.getModules();
    }

    public List<Layer> getLayers() {
        if(this.layers == null) {
            this.layers = layerBean.findAll();
        }
        return this.layers;
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

    public Integer getSelectedLayer() {
        return this.selectedLayer;
    }

    public void setSelectedLayer(Integer selectedLayer) {
        this.selectedLayer = selectedLayer;
    }

    public List<Module> getRelatedModules() {
        if(this.relatedModules == null && this.pack != null) {
            this.relatedModules = packageBean.findModulesByPackage(pack);
        }
        return this.relatedModules;
    }

    public List<EntityClass> getRelatedEntityClasses() {
        if(this.relatedEntityClasses == null && this.pack != null) {
            this.relatedEntityClasses = entityClassBean.findByPackage(pack);
        }
        return this.relatedEntityClasses;
    }

    public List<Component> getRelatedComponents() {
        if(this.relatedComponents == null && this.pack != null) {
            this.relatedComponents = componentBean.findByPackage(pack);
        }
        return this.relatedComponents;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.pack = packageBean.find(id);
            this.applicationFilterMBean.setSelectedApplication(this.pack.getApplication().getId());
            if(this.pack.getModule() != null) {
                this.applicationFilterMBean.setSelectedModule(this.pack.getModule().getId());
            }
            if(this.pack.getLayer() != null) {
                this.selectedLayer = this.pack.getLayer().getId();
            }
        }
        else {
            this.pack = new Package();
            if(appId != null) {
                applicationFilterMBean.setSelectedApplication(appId);
                this.pack.setApplication(applicationFilterMBean.getApplication());
            }
            if(modId != null) {
                applicationFilterMBean.setSelectedModule(modId);
                this.pack.setModule(applicationFilterMBean.getModule());
            }
        }
    }

    public String save() {
        this.pack.setApplication(applicationFilterMBean.getApplication());
        this.pack.setModule(applicationFilterMBean.getModule());

        if(this.selectedLayer != null) {
            Layer layer = layerBean.find(this.selectedLayer);
            this.pack.setLayer(layer);
        }

        packageBean.save(this.pack);
        return "packages?faces-redirect=true&appId=" + this.pack.getApplication().getId() + "&modId=" + this.pack.getModule().getId();
    }
}