package com.hildeberto.architect.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name = "properties")
public class Properties implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

    public Properties() {
    }

    public Properties(String key) {
        this.propertyKey = key;
    }

    public Properties(String key, String value) {
        this.propertyKey = key;
        this.propertyValue = value;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyKey != null ? propertyKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Properties)) {
            return false;
        }
        Properties other = (Properties) object;
        if ((this.propertyKey == null && other.propertyKey != null) || (this.propertyKey != null && !this.propertyKey.equals(other.propertyKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return propertyKey + " = " + propertyValue;
    }
}