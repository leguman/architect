package com.hildeberto.architect.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Inheritance
@Table(name="lifecycle")
@DiscriminatorColumn(name="lifecycle_type")
public abstract class Lifecycle implements Serializable, Identified<Integer> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    protected LifecycleState state;
    
    @Column(name = "state_date")
    @Temporal(TemporalType.DATE)
    private Date stateDate = Calendar.getInstance().getTime();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LifecycleState getState() {
        return state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Lifecycle)) {
            return false;
        }
        Lifecycle other = (Lifecycle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.state.name();
    }
}