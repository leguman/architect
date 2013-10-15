package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.EntityClass;
import com.hildeberto.architect.domain.Module;
import com.hildeberto.architect.domain.Package;
import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.DatabaseView;
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
public class EntityClassBean extends AbstractBean<EntityClass> {

    @PersistenceContext
    private EntityManager em;

    public EntityClassBean() {
        super(EntityClass.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<EntityClass> findByApplication(Application application) {
        return em.createQuery("select ec from EntityClass ec where ec.application = :application order by ec.name asc")
                 .setParameter("application", application)
                 .getResultList();
    }

    public List<EntityClass> findByModule(Module module) {
        return em.createQuery("select ec from EntityClass ec where ec.module = :module order by ec.name asc")
                 .setParameter("module", module)
                 .getResultList();
    }

    public List<EntityClass> findByPackage(Package pack) {
        return em.createQuery("select ec from EntityClass ec where ec.pack = :pack order by ec.name asc")
                 .setParameter("pack", pack)
                 .getResultList();
    }

    public List<EntityClass> findNotMappedClasses(EntityClass except) {
        if(except != null) {
            return em.createQuery("select ec from EntityClass ec where ec.databaseElement is null && ec != :except order by ec.name asc")
                     .setParameter("except", except)
                     .getResultList();
        }
        else {
            return em.createQuery("select ec from EntityClass ec where ec.databaseElement is null order by ec.name asc")
                     .getResultList();
        }
    }
}