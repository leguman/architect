package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.datasource.domain.DatabaseInstance;
import com.hildeberto.architect.datasource.domain.DatabaseSchema;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class DatabaseSchemaBean extends AbstractBean<DatabaseSchema> {

    @PersistenceContext
    private EntityManager em;
    
    public DatabaseSchemaBean() {
        super(DatabaseSchema.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<DatabaseSchema> findByDatabaseInstance(DatabaseInstance database) {
        return em.createQuery("select ds from DatabaseSchema ds where ds.databaseInstance = :database order by ds.name asc", DatabaseSchema.class)
                 .setParameter("database", database)
                 .getResultList();
    }

    public DatabaseSchema findByName(DatabaseInstance database, String name) {
        DatabaseSchema schema = null;
        if(database != null && name != null) {
            schema = em.createQuery("select ds from DatabaseSchema ds where ds.databaseInstance = :database and ds.name = :name", DatabaseSchema.class)
                       .setParameter("database", database)
                       .setParameter("name", name)
                       .getSingleResult();
        }
        return schema;
    }
}