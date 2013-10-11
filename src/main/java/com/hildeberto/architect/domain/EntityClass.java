package com.hildeberto.architect.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hildeberto Mendonça
 */
@Entity
@Table(name="entity_class")
public class EntityClass implements Serializable, Identified {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="database_element")
    private DatabaseElement databaseElement;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "class_name")
    private String className;
    
    @ManyToOne
    @JoinColumn(name = "package")
    private Package classPackage;
    
    @ManyToOne
    @JoinColumn(name = "application")
    private Application application;
    
    @ManyToOne
    @JoinColumn(name = "module")
    private Module module;
    
    @Lob
    @Size(max = 32700)
    private String description;

    public EntityClass() {
    }

    public EntityClass(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public DatabaseElement getDatabaseElement() {
        return databaseElement;
    }

    public void setDatabaseElement(DatabaseElement databaseElement) {
        this.databaseElement = databaseElement;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Package getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(Package classPackage) {
        this.classPackage = classPackage;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EntityClass)) {
            return false;
        }
        EntityClass other = (EntityClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.className;
    }    
}