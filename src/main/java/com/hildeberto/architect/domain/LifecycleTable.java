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
@DiscriminatorValue("TABLE")
public class LifecycleTable extends Lifecycle {
    
    @ManyToOne
    @JoinColumn(name="object")
    private DatabaseTable databaseTable;

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(DatabaseTable databaseTable) {
        this.databaseTable = databaseTable;
        if(this.databaseTable.getState() != null) {
            this.state = this.databaseTable.getState();
        }
        else {
            this.state = LifecycleState.getDefaultState();
        }
    }
}