package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Application;
import com.hildeberto.architect.domain.Layer;
import com.hildeberto.architect.domain.Module;
import com.hildeberto.architect.domain.Package;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class PackageBean extends AbstractBean<Package> {

    @PersistenceContext
    private EntityManager em;

    public PackageBean() {
        super(Package.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Package> findByApplication(Application application) {
        List<Package> packages = em.createQuery("select p from Package p where p.application = :application order by p.name asc")
                                   .setParameter("application", application)
                                   .getResultList();
        Set<String> packNames = new HashSet<>();
        List<Package> distinctPackages = new ArrayList<>();
        for(Package pack: packages) {
            if(packNames.add(pack.getName())) {
                distinctPackages.add(pack);
            }
        }
        return distinctPackages;
    }

    public List<Package> findByModule(Module module) {
        return em.createQuery("select p from Package p where p.module = :module order by p.name asc")
                 .setParameter("module", module)
                 .getResultList();
    }

    public List<Package> findByLayer(Layer layer) {
        List<Package> packages = em.createQuery("select p from Package p where p.layer = :layer order by p.name asc")
                                   .setParameter("layer", layer)
                                   .getResultList();
        Set<String> packNames = new HashSet<>();
        List<Package> distinctPackages = new ArrayList<>();
        for(Package pack: packages) {
            if(packNames.add(pack.getName())) {
                distinctPackages.add(pack);
            }
        }
        return distinctPackages;
    }

    public List<Module> findModulesByPackage(Package pack) {
        if(pack.getName() == null) {
            return null;
        }

        return em.createQuery("select distinct p.module from Package p where p.name = :packName order by p.module.name asc")
                 .setParameter("packName", pack.getName())
                 .getResultList();
    }
}