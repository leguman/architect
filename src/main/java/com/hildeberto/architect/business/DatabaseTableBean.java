package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseTable;
import com.hildeberto.architect.domain.DatabaseView;
import com.hildeberto.architect.domain.EntityClass;
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
public class DatabaseTableBean extends AbstractBean<DatabaseTable> {

    @PersistenceContext
    private EntityManager em;

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

    public List<DatabaseTable> findNotMappedTables(DatabaseTable except) {
        if(except != null) {
            return em.createQuery("select dt from DatabaseTable dt where dt not in (select ec.databaseElement from EntityClass ec where ec.databaseElement != :except) order by dt.name asc")
                     .setParameter("except", except)
                     .getResultList();
        }
        else {
            return em.createQuery("select dt from DatabaseTable dt where dt not in (select ec.databaseElement from EntityClass ec) order by dt.name asc")
                     .getResultList();
        }
    }
}