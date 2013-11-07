package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Application;
import com.hildeberto.architect.system.domain.Module;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author htmfilho
 */
@Stateless
@LocalBean
public class ModuleBean extends AbstractBean<Module> {

    @PersistenceContext
    private EntityManager em;
    
    public ModuleBean() {
        super(Module.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Module> findByApplication(Application application) {
        return em.createQuery("select m from Module m where m.application = :application and m.subModuleOf is null order by m.name asc")
                 .setParameter("application", application)
                 .getResultList();
    }
    
    public List<Module> findSubModules(Module module) {
        return em.createQuery("select m from Module m where m.subModuleOf = :module order by m.name asc")
                 .setParameter("module", module)
                 .getResultList();
    }
    
    public List<Module> findSubModulesByApplication(Application application, Module except) {
        Query query = em.createQuery("select m from Module m where m.application = :application "+ ((except != null)?"and m != :except ":"") +"order by m.name asc");
        
        query.setParameter("application", application);
        if(except != null) {
            query.setParameter("except", except);
        }
                 
        return query.getResultList();
    }
}