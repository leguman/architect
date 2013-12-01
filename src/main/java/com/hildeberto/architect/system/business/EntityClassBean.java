package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.datasource.domain.DatabaseElement;
import com.hildeberto.architect.system.domain.EntityClass;
import com.hildeberto.architect.system.domain.Module;
import com.hildeberto.architect.system.domain.Package;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class EntityClassBean extends AbstractBean<EntityClass> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PackageBean packageBean;

    public EntityClassBean() {
        super(EntityClass.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<EntityClass> findByApplication(Application application) {
        return em.createQuery("select ec from EntityClass ec where ec.application = :application order by ec.name asc", EntityClass.class)
                 .setParameter("application", application)
                 .getResultList();
    }

    public List<EntityClass> findByModule(Module module) {
        return em.createQuery("select ec from EntityClass ec where ec.module = :module order by ec.name asc", EntityClass.class)
                 .setParameter("module", module)
                 .getResultList();
    }

    public List<EntityClass> findByPackage(Package pack) {
        return em.createQuery("select ec from EntityClass ec where ec.pack = :pack order by ec.name asc", EntityClass.class)
                 .setParameter("pack", pack)
                 .getResultList();
    }

    public EntityClass findByMappedDatabaseElement(DatabaseElement databaseElement) {
        try {
            return em.createQuery("select ec from EntityClass ec where ec.databaseElement = :databaseElement", EntityClass.class)
                     .setParameter("databaseElement", databaseElement)
                     .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    @Override
    public EntityClass save(EntityClass entityClass) {
        // It verifies whether the name of the entity class contains package information.
        Package pack = Package.extractPackage(entityClass.getName(), entityClass.getApplication(), entityClass.getModule(), null);
        if(pack != null) {
            // In this case, the name contains package information and it verifies whether the package already exists.
            Package existingPack = packageBean.findByExample(pack);
            if(existingPack != null) {
                // If it exists then it simply sets the package of the entity class.
                entityClass.setPack(existingPack);
            }
            else {
                // If it doesn't exist then it creates a new package with the informed package name and
                // sets the package of the entity class.
                pack = packageBean.save(pack);
                entityClass.setPack(pack);
            }
        }
        // Since the entity class name contains package information, this method simplifies the name to its common form.
        entityClass.simplifyName();

        return super.save(entityClass);
    }
}