package com.hildeberto.architect.system.controller;

import com.hildeberto.architect.system.business.ActionBean;
import com.hildeberto.architect.system.business.FunctionalityBean;
import com.hildeberto.architect.system.domain.Action;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.system.domain.Functionality;
import com.hildeberto.architect.system.domain.Module;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Hildeberto Mendonca
 */
@ManagedBean
@RequestScoped
public class FunctionalityMBean {

    @EJB
    private FunctionalityBean functionalityBean;

    @EJB
    private ActionBean actionBean;

    private List<Functionality> functionalities;
    private List<Action> relatedActions;

    @ManagedProperty(value="#{applicationFilterMBean}")
    private ApplicationFilterMBean applicationFilterMBean;

    @ManagedProperty(value="#{actionMBean}")
    private ActionMBean actionMBean;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    @ManagedProperty(value="#{param.appId}")
    private Integer appId;

    @ManagedProperty(value="#{param.modId}")
    private Integer modId;

    private Functionality functionality;

    public List<Functionality> getFunctionalities() {
        if(functionalities == null) {
            if(applicationFilterMBean.getSelectedApplication() != null && applicationFilterMBean.getSelectedModule() == null) {
                Application application = applicationFilterMBean.getApplication();
                functionalities = functionalityBean.findByApplication(application);
            }
            else if(applicationFilterMBean.getSelectedModule() != null) {
                Module module = applicationFilterMBean.getModule();
                functionalities = functionalityBean.findByModule(module);
            }
        }
        return functionalities;
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

    public Functionality getFunctionality() {
        return this.functionality;
    }

    public void setApplicationFilterMBean(ApplicationFilterMBean applicationFilterMBean) {
        this.applicationFilterMBean = applicationFilterMBean;
    }

    public void setActionMBean(ActionMBean actionMBean) {
        this.actionMBean = actionMBean;
    }

    public List<Application> getApplications() {
        return this.applicationFilterMBean.getApplications();
    }

    public List<Module> getModules() {
        return this.applicationFilterMBean.getModules();
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

    public Action getAction() {
        return this.actionMBean.getAction();
    }

    public List<Action> getRelatedActions() {
        if(this.relatedActions == null) {
            this.relatedActions = actionBean.findByFunctionality(functionality);
        }
        return this.relatedActions;
    }

    public List<Action> getActions() {
        return this.actionMBean.getActions(functionality);
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.functionality = functionalityBean.find(id);
            this.applicationFilterMBean.setSelectedApplication(this.functionality.getApplication().getId());
            if(this.functionality.getModule() != null) {
                this.applicationFilterMBean.setSelectedModule(this.functionality.getModule().getId());
            }
            actionMBean.setActions(actionBean.findByFunctionality(functionality));
        }
        else {
            this.functionality = new Functionality();
            if(appId != null) {
                applicationFilterMBean.setSelectedApplication(appId);
                this.functionality.setApplication(applicationFilterMBean.getApplication());
            }
            if(modId != null) {
                applicationFilterMBean.setSelectedModule(modId);
                this.functionality.setModule(applicationFilterMBean.getModule());
            }
        }
    }

    public void saveAction(ActionEvent event) {
        actionMBean.setFunctionality(functionality);
        actionMBean.save(event);
    }

    public String save() {
        this.functionality.setApplication(applicationFilterMBean.getApplication());
        this.functionality.setModule(applicationFilterMBean.getModule());

        functionalityBean.save(this.functionality);
        return "functionalities?faces-redirect=true&appId=" + this.functionality.getApplication().getId() + "&modId=" + this.functionality.getModule().getId();
    }
}