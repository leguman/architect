package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.system.domain.Functionality;
import com.hildeberto.architect.system.domain.Module;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hildeberto Mendonca
 */
@Stateless
@LocalBean
public class FunctionalityBean extends AbstractBean<Functionality> {

    @PersistenceContext
    private EntityManager em;

    public FunctionalityBean() {
        super(Functionality.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Functionality> findByApplication(Application application) {
        return em.createQuery("select f from Functionality f where f.application = :application order by f.name asc")
                 .setParameter("application", application)
                 .getResultList();
    }

    public List<Functionality> findByModule(Module module) {
        return em.createQuery("select f from Functionality f where f.module = :module order by f.name asc")
                 .setParameter("module", module)
                 .getResultList();
    }
}