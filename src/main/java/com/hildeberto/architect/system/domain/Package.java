package com.hildeberto.architect.system.domain;

import com.hildeberto.architect.domain.Identified;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name="package")
public class Package implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "application")
    private Application application;

    @ManyToOne
    @JoinColumn(name="module")
    private Module module;

    @Lob
    @Size(max = 32700)
    private String description;

    @ManyToOne
    @JoinColumn(name = "layer")
    private Layer layer;

    public Package() {
    }

    public Package(Integer id) {
        this.id = id;
    }

    public Package(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public static Package extractPackage(String className, Application application, Module module, Layer layer) {
        Package pack = null;
        if(className.contains(".")) {
            String packName = className.substring(0, className.lastIndexOf("."));
            pack = new Package();
            pack.setApplication(application);
            pack.setModule(module);
            pack.setLayer(layer);
            pack.setName(packName);
        }
        return pack;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Package)) {
            return false;
        }
        Package other = (Package) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
