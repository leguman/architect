package com.hildeberto.architect.domain.system;

import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.system.Component;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hildeberto Mendonca
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