package com.hildeberto.architect.system.domain;

import com.hildeberto.architect.domain.Lifecycle;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hildeberto Mendonca
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