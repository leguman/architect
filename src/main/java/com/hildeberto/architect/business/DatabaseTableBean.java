package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseElement;
import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.LifecycleState;
import java.util.List;
import javax.ejb.EJB;
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
public class DatabaseTableBean extends AbstractBean<DatabaseTable> {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private LifecycleBean lifecycleBean;

    public DatabaseTableBean() {
        super(DatabaseTable.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<DatabaseTable> findByDatabaseInstance(DatabaseInstance database) {
        return em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database order by dt.name asc")
                 .setParameter("database", database)
                 .getResultList();
    }

    public List<DatabaseTable> findByDatabaseSchema(DatabaseSchema schema) {
        return em.createQuery("select dt from DatabaseTable dt where dt.databaseSchema = :schema order by dt.name asc")
                 .setParameter("schema", schema)
                 .getResultList();
    }

    public List<DatabaseTable> findNotMappedTables(DatabaseElement except) {
        List<DatabaseTable> unmappedTables;
        if(except != null) {
            unmappedTables = em.createQuery("select dt from DatabaseTable dt where dt.id not in (select ec.databaseElement.id from EntityClass ec where ec.databaseElement != :except) order by dt.name asc")
                               .setParameter("except", except)
                               .getResultList();
        }
        else {
            unmappedTables = em.createQuery("select dt from DatabaseTable dt where dt.id not in (select ec.databaseElement.id from EntityClass ec) order by dt.name asc")
                               .getResultList();
        }
        return unmappedTables;
    }
    
    @Override
    public DatabaseTable save(DatabaseTable databaseTable) {
        if(databaseTable.getId() == null) {
            if(databaseTable.getState() != null) {
                databaseTable = super.save(databaseTable);
                lifecycleBean.createAndSave(databaseTable);
            }
            else {
                databaseTable = super.save(databaseTable);
                databaseTable.setState(LifecycleState.getDefaultState());
                lifecycleBean.createAndSave(databaseTable);
            }
        }
        else {
            // The database table already exist, so let's check if the state is the same.
            DatabaseTable existingDatabaseTable = find(databaseTable.getId());
            if(databaseTable.getState() != null) {
                if(!databaseTable.getState().equals(existingDatabaseTable.getState())) {
                    databaseTable = super.save(databaseTable);
                    lifecycleBean.createAndSave(databaseTable);
                }
                else {
                    databaseTable = super.save(databaseTable);
                    // Nothing todo in the lifecycle if the state keeps the same.
                }                
            }
            else {
                databaseTable = super.save(databaseTable);
                databaseTable.setState(LifecycleState.getDefaultState());
                lifecycleBean.createAndSave(databaseTable);
            }
        }
        
        return databaseTable;
    }
}