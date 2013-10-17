package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.LifecycleTable;
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
    
    public List<LifecycleBean> findLifecycleTable(DatabaseTable databaseTable) {
        return em.createQuery("select lt from LifecycleTable lt where lt.databaseTable = :databaseTable order by lt.stateDate")
                 .setParameter("databaseTable", databaseTable)
                 .getResultList();
    }
}