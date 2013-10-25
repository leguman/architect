package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.EntityClass;
import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.LifecycleEntityClass;
import com.hildeberto.architect.domain.LifecycleTable;
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
public class LifecycleBean extends AbstractBean<Lifecycle> {

    @PersistenceContext
    private EntityManager em;

    public LifecycleBean() {
        super(Lifecycle.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<LifecycleTable> findLifecyclesTable(DatabaseTable databaseTable) {
        return em.createQuery("select lt from LifecycleTable lt where lt.databaseTable = :databaseTable order by lt.stateDate")
                 .setParameter("databaseTable", databaseTable)
                 .getResultList();
    }
    
    public LifecycleTable findLifecycleTable(DatabaseTable databaseTable) {
        List<LifecycleTable> lifecyclesTable = em.createQuery("select lt from LifecycleTable lt where lt.databaseTable = :databaseTable and lt.state = :state order by lt.stateDate desc")
                                                 .setParameter("databaseTable", databaseTable)
                                                 .setParameter("state", databaseTable.getState())
                                                 .getResultList();
        if(!lifecyclesTable.isEmpty()) {
            return lifecyclesTable.get(0);
        }
        
        return null;
    }
    
    public List<LifecycleEntityClass> findLifecyclesEntityClass(EntityClass entityClass) {
        return em.createQuery("select lec from LifecycleEntityClass lec where lec.entityClass = :entityClass order by lec.stateDate")
                 .setParameter("entityClass", entityClass)
                 .getResultList();
    }
    
    public LifecycleEntityClass findLifecycleEntityClass(EntityClass entityClass) {
        List<LifecycleEntityClass> lifecyclesEntityClass = em.createQuery("select lec from LifecycleentityClass lec where lec.entityClass = :entityClass and lec.state = :state order by lec.stateDate desc")
                                                 .setParameter("entityClass", entityClass)
                                                 .setParameter("state", entityClass.getState())
                                                 .getResultList();
        if(!lifecyclesEntityClass.isEmpty()) {
            return lifecyclesEntityClass.get(0);
        }
        
        return null;
    }
    
    public Lifecycle createAndSave(DatabaseTable databaseTable) {
        LifecycleTable lifecycleTable = new LifecycleTable();
        lifecycleTable.setDatabaseTable(databaseTable);
        return save(lifecycleTable);
    }
}