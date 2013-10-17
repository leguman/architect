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
    
    @Override
    public String toString() {
        return this.getPackage().getName() + ".<b>" + this.getName() + "</b>";
    }
    
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
