package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.system.domain.Component;
import com.hildeberto.architect.system.domain.Module;
import com.hildeberto.architect.system.domain.Package;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class ComponentBean extends AbstractBean<Component> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PackageBean packageBean;

    public ComponentBean() {
        super(Component.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Component> findByApplication(Application application) {
        return em.createQuery("select c from Component c where c.application = :application order by c.name asc", Component.class)
                 .setParameter("application", application)
                 .getResultList();
    }

    public List<Component> findByModule(Module module) {
        return em.createQuery("select c from Component c where c.module = :module order by c.name asc", Component.class)
                 .setParameter("module", module)
                 .getResultList();
    }

    public List<Component> findByPackage(Package pack) {
        return em.createQuery("select c from Component c where c.pack = :pack order by c.name asc", Component.class)
                 .setParameter("pack", pack)
                 .getResultList();
    }

    @Override
    public Component save(Component component) {
        // It verifies whether the name of the component contains package information.
        Package pack = Package.extractPackage(component.getName(), component.getApplication(), component.getModule(), null);
        if(pack != null) {
            // In this case, the name contains package information and it verifies whether the package already exists.
            Package existingPack = packageBean.findByExample(pack);
            if(existingPack != null) {
                // If it exists then it simply sets the package of the component.
                component.setPack(existingPack);
            }
            else {
                // If it doesn't exist then it creates a new package with the informed package name and
                // sets the package of the component.
                pack = packageBean.save(pack);
                component.setPack(pack);
            }
        }
        // Since the component name contains package information, this method simplifies the name to its common form.
        component.simplifyName();

        return super.save(component);
    }
}