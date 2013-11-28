package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.datasource.domain.DatabaseInstance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class DatabaseInstanceBean extends AbstractBean<DatabaseInstance> {

    @PersistenceContext
    private EntityManager em;
    
    public DatabaseInstanceBean() {
        super(DatabaseInstance.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<DatabaseInstance> findAll() {
        return em.createQuery("select di from DatabaseInstance di order by di.name asc").getResultList();
    }
}