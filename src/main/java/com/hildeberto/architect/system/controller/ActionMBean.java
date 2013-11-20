package com.hildeberto.architect.system.controller;

import com.hildeberto.architect.system.domain.Action;
import com.hildeberto.architect.system.domain.Functionality;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Hildeberto Mendonca
 */
@ManagedBean
@ViewScoped
public class ActionMBean implements Serializable {

    private Action action;

    private List<Action> actions;

    public ActionMBean() {
        this.action = new Action();
        this.actions = new ArrayList<>();
    }

    public Action getAction() {
        return this.action;
    }

    public List<Action> getActions(Functionality functionality) {
        return this.actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setFunctionality(Functionality functionality) {
        this.action.setFunctionality(functionality);
    }

    public void save(ActionEvent event) {
        actions.add(this.action);
        this.action = new Action();
    }
}