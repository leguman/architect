package com.hildeberto.architect.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author mendoncafilh
 */
@Entity
@DiscriminatorValue("ENTITY_CLASS")
public class EntityClass extends CodeArtifact {
        
    @ManyToOne
    @JoinColumn(name="database_element")
    private DatabaseElement databaseElement;

    public DatabaseElement getDatabaseElement() {
        return databaseElement;
    }

    public void setDatabaseElement(DatabaseElement databaseElement) {
        this.databaseElement = databaseElement;
    }
}
