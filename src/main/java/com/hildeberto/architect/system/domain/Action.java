package com.hildeberto.architect.system.domain;

import com.hildeberto.architect.domain.Identified;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name="action")
public class Action implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "functionality")
    private Functionality functionality;

    @Lob
    @Size(max = 32700)
    private String description;

    private String acronym;

    public Action() {
    }

    public Action(Integer id) {
        this.id = id;
    }

    public Action(Integer id, String name) {
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

    public Functionality getFunctionality() {
        return functionality;
    }

    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPermission() {
        String permission = null;

        if(this.functionality != null && this.functionality.getModule() != null && this.getAcronym() != null) {
            permission = this.functionality.getModule().getAcronym() + "_" + this.functionality.getAcronym() + "_" + this.getAcronym();
        }

        return permission;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Action)) {
            return false;
        }

        Action other = (Action) object;
        // By adding this.id != other.id it makes sure the object is not exactly the same.
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)) || (this.id != other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}