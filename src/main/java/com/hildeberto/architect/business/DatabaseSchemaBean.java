package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseSchema;
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
    
    public List<DatabaseSchema> findAll() {
        return em.createQuery("select ds from DatabaseSchema ds order by ds.name asc").getResultList();
    }
}