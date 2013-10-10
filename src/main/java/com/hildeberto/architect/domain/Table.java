package com.hildeberto.architect.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("TABLE")
public class Table extends DatabaseElement {
    
    @Column(name="primary_key")
    private String primaryKey;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}