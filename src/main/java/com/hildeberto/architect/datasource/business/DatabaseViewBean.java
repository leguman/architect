package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.datasource.domain.DatabaseElement;
import com.hildeberto.architect.datasource.domain.DatabaseInstance;
import com.hildeberto.architect.datasource.domain.DatabaseSchema;
import com.hildeberto.architect.datasource.domain.DatabaseView;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class DatabaseViewBean extends AbstractBean<DatabaseView> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private DatabaseElementBean databaseElementBean;

    public DatabaseViewBean() {
        super(DatabaseView.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<DatabaseView> findByDatabaseInstance(DatabaseInstance database) {
        return em.createQuery("select dv from DatabaseView dv where dv.databaseInstance = :database order by dv.name asc", DatabaseView.class)
                 .setParameter("database", database)
                 .getResultList();
    }

    public List<DatabaseView> findByDatabaseSchema(DatabaseSchema schema) {
        return em.createQuery("select dv from DatabaseView dv where dv.databaseSchema = :schema order by dv.name asc", DatabaseView.class)
                 .setParameter("schema", schema)
                 .getResultList();
    }

    @Override
    public DatabaseView save(DatabaseView databaseView) {
        return (DatabaseView) databaseElementBean.save(databaseView);
    }
}