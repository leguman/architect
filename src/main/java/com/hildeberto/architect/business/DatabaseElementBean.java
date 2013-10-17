package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.DatabaseElement;
import com.hildeberto.architect.domain.DatabaseTable;

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

    public List<DatabaseElement> findNotMappedElements(DatabaseElement except) {
        List<DatabaseElement> unmappedElements;
        if(except != null) {
            unmappedElements = em.createQuery("select de from DatabaseElement de where de.id not in (select ec.databaseElement.id from EntityClass ec where ec.databaseElement != :except) order by de.name asc")
                    .setParameter("except", except)
                    .getResultList();
        }
        else {
            unmappedElements = em.createQuery("select de from DatabaseElement de where de.id not in (select ec.databaseElement.id from EntityClass ec) order by de.name asc")
                    .getResultList();
        }
        return unmappedElements;
    }
}