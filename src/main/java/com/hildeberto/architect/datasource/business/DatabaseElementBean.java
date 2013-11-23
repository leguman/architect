package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.business.LifecycleBean;
import com.hildeberto.architect.datasource.domain.DatabaseElement;
import com.hildeberto.architect.domain.LifecycleState;
import java.util.List;
import javax.ejb.EJB;
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
public class DatabaseElementBean extends AbstractBean<DatabaseElement> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private LifecycleBean lifecycleBean;

    public DatabaseElementBean() {
        super(DatabaseElement.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatabaseElement findByName(String name) {
        DatabaseElement databaseElement = null;
        List<DatabaseElement> elements = em.createQuery("select de from DatabaseElement de where de.name = :name")
                                           .setParameter("name", name)
                                           .getResultList();
        if(elements.size() > 0) {
            databaseElement = elements.get(0);
        }
        return databaseElement;
    }

    public List<DatabaseElement> findNotMappedElements(DatabaseElement except) {
        List<DatabaseElement> unmappedElements;
        if(except != null) {
            unmappedElements = em.createQuery("select de from DatabaseElement de where de.id not in (select ec.databaseElement.id from EntityClass ec where ec.databaseElement <> :except) order by de.name asc")
                    .setParameter("except", except)
                    .getResultList();
        }
        else {
            unmappedElements = em.createQuery("select de from DatabaseElement de where de.id not in (select ec.databaseElement.id from EntityClass ec) order by de.name asc")
                    .getResultList();
        }
        return unmappedElements;
    }

    @Override
    public DatabaseElement save(DatabaseElement databaseElement) {
        if(databaseElement.getId() == null) {
            if(databaseElement.getState() != null) {
                databaseElement = super.save(databaseElement);
                lifecycleBean.createAndSave(databaseElement);
            }
            else {
                databaseElement = super.save(databaseElement);
                databaseElement.setState(LifecycleState.getDefaultState());
                lifecycleBean.createAndSave(databaseElement);
            }
        }
        else {
            // The database element already exist, so let's check if the state is the same.
            DatabaseElement existingDatabaseElement = find(databaseElement.getId());
            if(databaseElement.getState() != null) {
                if(!databaseElement.getState().equals(existingDatabaseElement.getState())) {
                    databaseElement = super.save(databaseElement);
                    lifecycleBean.createAndSave(databaseElement);
                }
                else {
                    databaseElement = super.save(databaseElement);
                    // Nothing todo in the lifecycle if the state keeps the same.
                }
            }
            else {
                databaseElement = super.save(databaseElement);
                databaseElement.setState(LifecycleState.getDefaultState());
                lifecycleBean.createAndSave(databaseElement);
            }
        }

        return databaseElement;
    }
}