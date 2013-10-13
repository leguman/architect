package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Identified;
import javax.persistence.EntityManager;

/**
 *
 * @author Hildeberto Mendonca
 */
public abstract class AbstractBean<T extends Identified> {

    private Class<T> entityClass;

    public AbstractBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void save(T entity) {
        if(entity.getId() == null) {
            getEntityManager().persist(entity);
        }
        else {
            getEntityManager().merge(entity);
        }
    }

    public void remove(Integer id) {
        getEntityManager().remove(getEntityManager().find(this.entityClass, id));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}