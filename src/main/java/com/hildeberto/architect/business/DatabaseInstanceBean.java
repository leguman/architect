package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseInstance;
import java.util.List;
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