package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Action;
import com.hildeberto.architect.system.domain.Functionality;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hildeberto Mendonca
 */
@Stateless
public class ActionBean extends AbstractBean<Action> {

    @PersistenceContext
    private EntityManager em;

    public ActionBean() {
        super(Action.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Action> findByFunctionality(Functionality functionality) {
        return em.createQuery("select a from Action a where a.functionality = :functionality order by a.name asc")
                 .setParameter("functionality", functionality)
                 .getResultList();
    }
}