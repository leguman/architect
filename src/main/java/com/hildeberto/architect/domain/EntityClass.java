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
    
    private Boolean cacheable;

    public DatabaseElement getDatabaseElement() {
        return databaseElement;
    }
    
    public Boolean getDatabaseTable() {
        if(this.databaseElement != null) {
            return (databaseElement instanceof DatabaseTable);
        }
        else {
            return null;
        }
    }

    public void setDatabaseElement(DatabaseElement databaseElement) {
        this.databaseElement = databaseElement;
    }

    public Boolean getCacheable() {
        return cacheable;
    }

    public void setCacheable(Boolean cacheable) {
        this.cacheable = cacheable;
    }

    /**
     * Based on the name of the entity class, this method suggests the name of
     * the database element.
     */
    public String suggestedElementName() {
        if(getName() == null || getName().isEmpty()) {
            return "";
        }

        String className = getName();
        StringBuilder elementName = new StringBuilder();
        for(int i = 0;i < className.length();i++) {
            char c = className.charAt(i);
            if(i > 0 && Character.isUpperCase(c)) {
                elementName.append("_");
                elementName.append(c);
            }
            else {
                elementName.append(Character.toUpperCase(c));
            }
        }
        return elementName.toString();
    }
}
