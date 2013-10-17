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
@DiscriminatorValue("ENTITY_CLASS")
public class LifecycleEntityClass extends Lifecycle {
    
    @ManyToOne
    @JoinColumn(name="object")
    private EntityClass entityClass;

    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }   
}