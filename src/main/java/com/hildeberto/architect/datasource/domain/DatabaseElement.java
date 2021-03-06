package com.hildeberto.architect.datasource.domain;

import com.hildeberto.architect.domain.Identified;
import com.hildeberto.architect.domain.LifecycleState;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Inheritance
@Table(name="database_element")
@DiscriminatorColumn(name="element_type")
public abstract class DatabaseElement implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="database_ins")
    private DatabaseInstance databaseInstance;
        
    @ManyToOne
    @JoinColumn(name="database_schema")
    private DatabaseSchema databaseSchema;
    
    @Lob
    @Size(max = 32700)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_state")
    private LifecycleState state;

    public DatabaseElement() {
    }

    public DatabaseElement(Integer id) {
        this.id = id;
    }

    public DatabaseElement(String name) {
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

    public DatabaseInstance getDatabaseInstance() {
        return databaseInstance;
    }

    public void setDatabaseInstance(DatabaseInstance databaseInstance) {
        this.databaseInstance = databaseInstance;
    }

    public DatabaseSchema getDatabaseSchema() {
        return databaseSchema;
    }

    public void setDatabaseSchema(DatabaseSchema databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LifecycleState getState() {
        return state;
    }

    public void setState(LifecycleState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DatabaseElement)) {
            return false;
        }
        DatabaseElement other = (DatabaseElement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.databaseSchema.getName() + ".<b>" + this.name + "</b>";
    }    
}