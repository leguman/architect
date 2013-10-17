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
@DiscriminatorValue("VIEW")
public class LifecycleView extends Lifecycle {
    
    @ManyToOne
    @JoinColumn(name="object")
    private DatabaseView databaseView;

    public DatabaseView getDatabaseView() {
        return databaseView;
    }

    public void setDatabaseView(DatabaseView databaseView) {
        this.databaseView = databaseView;
    }   
}