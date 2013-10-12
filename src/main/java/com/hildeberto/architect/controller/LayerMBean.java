package com.hildeberto.architect.controller;

import com.hildeberto.architect.business.LayerBean;
import com.hildeberto.architect.business.PackageBean;
import com.hildeberto.architect.domain.Layer;
import com.hildeberto.architect.domain.Package;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Hildeberto Mendonca
 */
@ManagedBean
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