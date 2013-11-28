package com.hildeberto.architect.system.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.system.domain.Layer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author htmfilho
 */
@Stateless
public class LayerBean extends AbstractBean<Layer> {

    @PersistenceContext
    private EntityManager em;
    
    public LayerBean() {
        super(Layer.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Layer> findAll() {
        return em.createQuery("select l from Layer l order by l.name asc").getResultList();
    }
}