package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseElement;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
@LocalBean
public class DatabaseElementBean extends AbstractBean<DatabaseElement> {

    @PersistenceContext
    private EntityManager em;

    public DatabaseElementBean() {
        super(DatabaseElement.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}