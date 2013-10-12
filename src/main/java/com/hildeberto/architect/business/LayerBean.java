package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Layer;
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