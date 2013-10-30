package com.hildeberto.architect.business.datasource;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.domain.datasource.DatabaseElement;
import com.hildeberto.architect.domain.datasource.DatabaseInstance;
import com.hildeberto.architect.domain.datasource.DatabaseSchema;
import com.hildeberto.architect.domain.datasource.DatabaseTable;
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
    private DatabaseElementBean databaseElementBean;

    public DatabaseTableBean() {
        super(DatabaseTable.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<DatabaseTable> findByDatabaseInstance(DatabaseInstance database, LifecycleState state) {
        if(state == null) {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database order by dt.name asc")
                     .setParameter("database", database)
                     .getResultList();
        }
        else {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database and dt.state = :state order by dt.name asc")
                     .setParameter("database", database)
                     .setParameter("state", state)
                     .getResultList();
        }
    }

    public List<DatabaseTable> findByDatabaseSchema(DatabaseSchema schema, LifecycleState state) {
        if(state == null) {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseSchema = :schema order by dt.name asc")
                     .setParameter("schema", schema)
                     .getResultList();
        }
        else {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseSchema = :schema and dt.state = :state order by dt.name asc")
                     .setParameter("schema", schema)
                     .setParameter("state", state)
                     .getResultList();
        }
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
        return (DatabaseTable) databaseElementBean.save(databaseTable);
    }
}