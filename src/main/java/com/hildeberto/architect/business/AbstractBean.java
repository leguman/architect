package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Identified;
import javax.persistence.EntityManager;

/**
 *
 * @author Hildeberto Mendonca
 * @param <T>
 */
public abstract class AbstractBean<T extends Identified> {

    private final Class<T> entityClass;

    public AbstractBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T save(T entity) {
        if(entity.getId() == null) {
            getEntityManager().persist(entity);
        }
        else {
            entity = getEntityManager().merge(entity);
        }
        return entity;
    }

    public void remove(Integer id) {
        getEntityManager().remove(getEntityManager().find(this.entityClass, id));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}