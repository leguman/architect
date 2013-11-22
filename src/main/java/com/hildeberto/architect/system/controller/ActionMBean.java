package com.hildeberto.architect.system.controller;

import com.hildeberto.architect.system.domain.Action;
import com.hildeberto.architect.system.domain.Functionality;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

    public List<Action> getActions() {
        return this.actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setFunctionality(Functionality functionality) {
        this.action.setFunctionality(functionality);
    }

    public void save() {
        if (action.getId() != null) {
            boolean found = false;
            for (Action a : actions) {
                if (a.getId() != null && a.getId().equals(action.getId())) {
                    a.setName(action.getName());
                    a.setDescription(action.getDescription());
                    found = true;
                    break;
                }
            }
            if(!found) {
                actions.add(this.action.getId(), action);
            }
        } else {
            action.setId(actions.size());
            actions.add(action);
        }
        action = new Action();
    }

    public void edit(int id) {
        for (Action a : actions) {
            if (a.getId() != null && a.getId().intValue() == id) {
                this.action = a;
                break;
            }
        }
    }

    public void remove(int id) {
        for (Action a : actions) {
            if (a.getId() != null && a.getId().intValue() == id) {
                actions.remove(a);
                break;
            }
        }
        this.action = new Action();
    }
}
