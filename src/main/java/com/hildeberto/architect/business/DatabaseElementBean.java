package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseElement;
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
public class DatabaseElementBean extends AbstractBean<DatabaseElement> {

    @PersistenceContext
    private EntityManager em;

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
}