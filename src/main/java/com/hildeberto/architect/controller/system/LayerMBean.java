package com.hildeberto.architect.controller.system;

import com.hildeberto.architect.business.system.LayerBean;
import com.hildeberto.architect.business.system.PackageBean;
import com.hildeberto.architect.domain.system.Layer;
import com.hildeberto.architect.domain.system.Package;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author Hildeberto Mendonca
 */
@Named
@RequestScoped
public class LayerMBean {
 
    @EJB
    private LayerBean layerBean;
        
    @EJB
    private PackageBean packageBean;
    
    private List<Layer> layers;
    private List<Package> packages;
    
    @ManagedProperty(value="#{param.id}")
    private Integer id;
    
    private Layer layer;
    
    public List<Layer> getLayers() {
        if(this.layers == null) {
            this.layers = layerBean.findAll();
        }
        return this.layers;
    }
       
    public List<Package> getPackages() {
        if(this.packages == null && this.layer.getId() != null) {
            this.packages = packageBean.findByLayer(this.layer);
        }
        return this.packages;
    }
        
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Layer getLayer() {
        return this.layer;
    }
    
    @PostConstruct
    public void load() {
        if(id != null) {
            this.layer = layerBean.find(id);
        }
        else {
            this.layer = new Layer();
        }
    }
    
    public String save() {
        layerBean.save(this.layer);
        return "layers";
    }
}