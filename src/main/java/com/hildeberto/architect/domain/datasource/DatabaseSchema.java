package com.hildeberto.architect.domain.datasource;

import com.hildeberto.architect.domain.Identified;
import java.io.Serializable;
import javax.persistence.Basic;
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
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name="database_schema")
public class DatabaseSchema implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="database_ins")
    private DatabaseInstance databaseInstance;
    
    @Lob
    @Size(max = 32700)
    private String description;

    public DatabaseSchema() {
    }

    public DatabaseSchema(Integer id) {
        this.id = id;
    }

    public DatabaseSchema(Integer id, String name) {
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

    public DatabaseInstance getDatabaseInstance() {
        return databaseInstance;
    }

    public void setDatabaseInstance(DatabaseInstance databaseInstance) {
        this.databaseInstance = databaseInstance;
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
        if (!(object instanceof DatabaseSchema)) {
            return false;
        }
        DatabaseSchema other = (DatabaseSchema) object;
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