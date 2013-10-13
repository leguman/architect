package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseInstance;
import com.hildeberto.architect.domain.DatabaseSchema;
import com.hildeberto.architect.domain.DatabaseView;
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
public class DatabaseViewBean extends AbstractBean<DatabaseView> {

    @PersistenceContext
    private EntityManager em;
    
    public DatabaseViewBean() {
        super(DatabaseView.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<DatabaseView> findByDatabaseInstance(DatabaseInstance database) {
        return em.createQuery("select dv from DatabaseView dv where dv.databaseInstance = :database order by dv.name asc")
                 .setParameter("database", database)
                 .getResultList();
    }
    
    public List<DatabaseView> findByDatabaseSchema(DatabaseSchema schema) {
        return em.createQuery("select dv from DatabaseView dv where dv.databaseSchema = :schema order by dv.name asc")
                 .setParameter("schema", schema)
                 .getResultList();
    }
}