package com.hildeberto.architect.business.system;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.domain.system.Application;
import com.hildeberto.architect.domain.datasource.DatabaseElement;
import com.hildeberto.architect.domain.system.EntityClass;
import com.hildeberto.architect.domain.system.Module;
import com.hildeberto.architect.domain.system.Package;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public EntityClass findByMappedDatabaseElement(DatabaseElement databaseElement) {
        try {
            return em.createQuery("select ec from EntityClass ec where ec.databaseElement = :databaseElement", EntityClass.class)
                     .setParameter("databaseElement", databaseElement)
                     .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
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

    @Override
    public EntityClass save(EntityClass entityClass) {
        // It verifies whether the name of the entity class contains package information.
        Package pack = Package.extractPackage(entityClass.getName(), entityClass.getApplication(), entityClass.getModule(), null);
        if(pack != null) {
            // In this case, the name contains package information and it verifies whether the package already exists.
            Package existingPack = packageBean.findByExample(pack);
            if(existingPack != null) {
                // If it exists then it simply sets the package of the entity class.
                entityClass.setPackage(existingPack);
            }
            else {
                // If it doesn't exist then it creates a new package with the informed package name and
                // sets the package of the entity class.
                pack = packageBean.save(pack);
                entityClass.setPackage(pack);
            }
        }
        // Since the entity class name contains package information, this method simplifies the name to its common form.
        entityClass.simplifyName();

        return super.save(entityClass);
    }
}