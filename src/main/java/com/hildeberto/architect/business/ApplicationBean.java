package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Application;
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
public class ApplicationBean extends AbstractBean<Application> {

    @PersistenceContext
    private EntityManager em;
    
    public ApplicationBean() {
        super(Application.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Application> findAll() {
        return em.createQuery("select a from Application a order by a.name asc").getResultList();
    }
}