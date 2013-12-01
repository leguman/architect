package com.hildeberto.architect.business;

import com.hildeberto.architect.datasource.domain.DatabaseElement;
import com.hildeberto.architect.datasource.domain.DatabaseTable;
import com.hildeberto.architect.datasource.domain.DatabaseView;
import com.hildeberto.architect.system.domain.EntityClass;
import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.system.domain.LifecycleEntityClass;
import com.hildeberto.architect.datasource.domain.LifecycleTable;
import com.hildeberto.architect.datasource.domain.LifecycleView;
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

    public List<LifecycleTable> findLifecycleTable(DatabaseTable databaseTable) {
        return em.createQuery("select lt from LifecycleTable lt where lt.databaseTable = :databaseTable order by lt.stateDate", LifecycleTable.class)
                 .setParameter("databaseTable", databaseTable)
                 .getResultList();
    }

    public List<LifecycleView> findLifecycleView(DatabaseView databaseView) {
        return em.createQuery("select lv from LifecycleView lv where lv.databaseView = :databaseView order by lv.stateDate", LifecycleView.class)
                 .setParameter("databaseView", databaseView)
                 .getResultList();
    }

    public List<LifecycleEntityClass> findLifecycleEntityClass(EntityClass entityClass) {
        return em.createQuery("select lec from LifecycleEntityClass lec where lec.entityClass = :entityClass order by lec.stateDate", LifecycleEntityClass.class)
                 .setParameter("entityClass", entityClass)
                 .getResultList();
    }

    public Lifecycle createAndSave(DatabaseElement databaseElement) {
        if(databaseElement instanceof DatabaseTable) {
            LifecycleTable lifecycleTable = new LifecycleTable();
            lifecycleTable.setDatabaseTable((DatabaseTable) databaseElement);
            return save(lifecycleTable);
        }
        else if(databaseElement instanceof DatabaseView) {
            LifecycleView lifecycleView = new LifecycleView();
            lifecycleView.setDatabaseView((DatabaseView) databaseElement);
            return save(lifecycleView);
        }
        else {
            return null;
        }
    }
}