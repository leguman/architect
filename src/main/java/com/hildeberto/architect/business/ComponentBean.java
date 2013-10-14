package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Component;
import com.hildeberto.architect.domain.Module;
import com.hildeberto.architect.domain.Package;
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
public class ComponentBean extends AbstractBean<Component> {

    @PersistenceContext
    private EntityManager em;

    public ComponentBean() {
        super(Component.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Component> findByApplication(Application application) {
        return em.createQuery("select c from Component c where c.application = :application order by c.name asc")
                 .setParameter("application", application)
                 .getResultList();
    }

    public List<Component> findByModule(Module module) {
        return em.createQuery("select c from Component c where c.module = :module order by c.name asc")
                 .setParameter("module", module)
                 .getResultList();
    }

    public List<Component> findByPackage(Package pack) {
        return em.createQuery("select c from Component c where c.pack = :pack order by c.name asc")
                 .setParameter("pack", pack)
                 .getResultList();
    }
}