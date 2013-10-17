package com.hildeberto.architect.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author mendoncafilh
 */
@Entity
@DiscriminatorValue("COMPONENT")
public class LifecycleComponent extends Lifecycle {
    
    @ManyToOne
    @JoinColumn(name="object")
    private Component component;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}